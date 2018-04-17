package CorePandas.CorePandas.data_structures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataFrame {
	private final Index rowIndex, columnIndex;
	private final Data<? extends Comparable<?>> data;

	/**
	 * Builds empty data frame
	 */
	public DataFrame() {
		this(Collections.<List<Object>>emptyList());
	}

	/**
	 * Builds empty data frame with specified column index
	 * @param columnsIndex are objects to define column index
	 */
	public DataFrame(final Object... columnsIndex) {
		this(Arrays.asList((Object[]) columnsIndex));
	}

	/**
	 * Builds empty data frame with specified column index
	 * @param columnsIndex is collection of objects to define column index
	 */
	public DataFrame(final Collection<?> columnsIndex) {
		this(Collections.emptyList(), columnsIndex, Collections.<List<? extends Comparable<?>>>emptyList());
	}

	/**
	 * Builds empty data frame with specified column and row index
	 * @param rowsIndex is collection of objects to define row index
	 * @param columnsIndex is collection of objects to define column index
	 */
	public DataFrame(final Collection<?> rowsIndex, final Collection<?> columnsIndex) {
		this(rowsIndex, columnsIndex, Collections.<List<?>>emptyList());
	}

	/**
	 * Builds data frame with specified data and empty column and row index
	 * @param rawData is data to create data frame from
	 */
	public DataFrame(final List<? extends List<? extends Comparable<?>>> rawData) {
		this(Collections.emptyList(), Collections.emptyList(), rawData);
	}

	/**
	 * Builds data frame with specified data, column and row index
	 * @param rowsIndex is collection of objects to define row index
	 * @param columnsIndex is collection of objects to define column index
	 * @param rawData is data to create data frame from
	 */
	public DataFrame(final Collection<?> rowsIndex, final Collection<?> columnsIndex,
			final List<? extends List<? extends Comparable<?>>> rawData) {
		final Data<?> newData = new Data<>(rawData);
		newData.reshape(Math.max(newData.size(), columnsIndex.size()), Math.max(newData.length(), rowsIndex.size()));

		this.data = newData;
		this.columnIndex = new Index(columnsIndex, newData.size());
		this.rowIndex = new Index(rowsIndex, newData.length());
	}

	/**
	 * Gets column index
	 * @return set of column index items
	 */
	public Set<Object> getColumnIndex() {
		return columnIndex.getNames();
	}

	/**
	 * Gets row index
	 * @return set of row index items
	 */
	public Set<Object> getRowIndex() {
		return rowIndex.getNames();
	}

	@SuppressWarnings("unchecked")
	private <E> Set<E> getCol(final Integer column) {
		return new HashSet<E>((Collection<? extends E>) data.getCol(column));
	}

	/**
	 * Gets column with specified column index item
	 * @param columnId is column index item
	 * @return set of items in the specified column
	 */
	public <E> Set<E> getCol(final Object columnId) {
		return getCol(columnIndex.getNameIndice(columnId));
	}

	/**
	 * Builds data frame from CSV file
	 * @param filePath name of CSV file with its path
	 * @param hasColumnIndex is true if the first raw defines the column index in
	 * CSV file
	 * @param hasRawIndex is true if the first column defines the raw index in CSV
	 * file
	 */
	public DataFrame(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex) {
		List<Object> rawIndex = null, columnIndex = null;
		List<List<? extends Comparable<?>>> listObjectList = null;

		try {
			Reader in = new FileReader(filePath);
			CSVParser parser;

			if (hasColumnIndex) {
				parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			} else {
				parser = CSVFormat.RFC4180.parse(in);
			}
			List<CSVRecord> records = parser.getRecords();

			int columnsNumber = records.get(0).size();
			int rowsNumber = records.size();

			// Filling column index
			if (hasColumnIndex) {
				columnIndex = new ArrayList<>();
				columnIndex.addAll(parser.getHeaderMap().keySet());
			}

			// Initializing structures
			List<List<String>> listStringList = new ArrayList<>();
			for (int i = 0; i < columnsNumber; i++) {
				listStringList.add(new ArrayList<String>());
			}

			// Filling in the records
			for (int i = 0; i < rowsNumber; i++) {
				CSVRecord record = records.get(i);
				for (int j = 0; j < columnsNumber; j++) {
					listStringList.get(j).add(record.get(j));
				}
			}

			listObjectList = new ArrayList<>();
			for (int i = 0; i < columnsNumber; i++) {
				try {
					Integer.valueOf(listStringList.get(i).get(0));
					ArrayList<Integer> newList = new ArrayList<>();
					for (String str : listStringList.get(i)) {
						newList.add(Integer.valueOf(str));
					}
					listObjectList.add(newList);
				} catch (NumberFormatException iE) {
					try {
						Long.valueOf(listStringList.get(i).get(0));
						ArrayList<Long> newList = new ArrayList<>();
						for (String str : listStringList.get(i)) {
							newList.add(Long.valueOf(str));
						}
						listObjectList.add(newList);
					} catch (NumberFormatException lE) {
						try {
							Double.valueOf(listStringList.get(i).get(0));
							ArrayList<Double> newList = new ArrayList<>();
							for (String str : listStringList.get(i)) {
								newList.add(Double.valueOf(str));
							}
							listObjectList.add(newList);
						} catch (NumberFormatException dE) {
							try {
								new SimpleDateFormat("dd/MM/yyyy").parse(listStringList.get(i).get(0));
								ArrayList<Date> newList = new ArrayList<>();
								for (String str : listStringList.get(i)) {
									newList.add(new SimpleDateFormat("dd/MM/yyyy").parse(str));
								}
								listObjectList.add(newList);
							} catch (ParseException e1) {
								try {
									new SimpleDateFormat("dd-MMM-yyyy").parse(listStringList.get(i).get(0));
									ArrayList<Date> newList = new ArrayList<>();
									for (String str : listStringList.get(i)) {
										newList.add(new SimpleDateFormat("dd-MMM-yyyy").parse(str));
									}
									listObjectList.add(newList);
								} catch (ParseException e2) {
									try {
										new SimpleDateFormat("MM dd, yyyy").parse(listStringList.get(i).get(0));
										ArrayList<Date> newList = new ArrayList<>();
										for (String str : listStringList.get(i)) {
											newList.add(new SimpleDateFormat("MM dd, yyyy").parse(str));
										}
										listObjectList.add(newList);
									} catch (ParseException e3) {
										try {
											new SimpleDateFormat("E, MMM dd yyyy").parse(listStringList.get(i).get(0));
											ArrayList<Date> newList = new ArrayList<>();
											for (String str : listStringList.get(i)) {
												newList.add(new SimpleDateFormat("E, MMM dd yyyy").parse(str));
											}
											listObjectList.add(newList);
										} catch (ParseException e4) {
											try {
												new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss")
														.parse(listStringList.get(i).get(0));
												ArrayList<Date> newList = new ArrayList<>();
												for (String str : listStringList.get(i)) {
													newList.add(
															new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(str));
												}
												listObjectList.add(newList);
											} catch (ParseException e5) {
												try {
													new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
															.parse(listStringList.get(i).get(0));
													ArrayList<Date> newList = new ArrayList<>();
													for (String str : listStringList.get(i)) {
														newList.add(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
																.parse(str));
													}
													listObjectList.add(newList);
												} catch (ParseException e6) {
													ArrayList<String> newList = new ArrayList<>();
													for (String str : listStringList.get(i)) {
														newList.add(new String(str));
													}
													listObjectList.add(newList);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			if (hasRawIndex) {
				rawIndex = new ArrayList<>();
				rawIndex.addAll(listObjectList.get(0));
				listObjectList.remove(listObjectList.get(0));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rawIndex == null) {
			rawIndex = Collections.emptyList();
		}
		if (columnIndex == null) {
			columnIndex = Collections.emptyList();
		}
		final Data<?> newData = new Data<>(listObjectList);
		newData.reshape(Math.max(newData.size(), columnIndex.size()), Math.max(newData.length(), rawIndex.size()));

		this.data = newData;
		this.columnIndex = new Index(columnIndex, newData.size());
		this.rowIndex = new Index(rawIndex, newData.length());
	}

	/**
	 * Gets size of data frame
	 * @return number of columns in data frame
	 */
	public int size() {
		return data.size();
	}

	private Data<? extends Comparable<?>> getData() {
		return data;
	}

	// @Override
	// public Iterator<List<E>> iterator() {
	// // TODO Do we need this method??
	// return null;
	// }

	@Override
	public String toString() {
		// Iterator<Data<?>> it;
		return data.toString();
	}

	/**
	 * Computes the minimum of the given column with numerical/text values
	 * @param columnName is the name of the column
	 * @return minimum of columnName
	 */
	public <T extends Comparable<T>> T getMin(String columnName) {
		return this.getData().getMin(this.columnIndex.getNameIndice(columnName));
	}

	/**
	 * Computes the maximum of the given column with numerical/text values
	 * @param columnName is the name of the column
	 * @return maximum of columnName
	 */
	public <T extends Comparable<T>> T getMax(String columnName) {
		return this.getData().getMax(this.columnIndex.getNameIndice(columnName));
	}

	/**
	 * Computes the average of the given column with numerical values
	 * @param columnName is the name of the column
	 * @return average of columnName
	 */
	public <T extends Comparable<T>> Double getMean(String columnName) {
		return this.getData().getMean(this.columnIndex.getNameIndice(columnName));
	}

	/**
	 * Builds data frame from CSV file with column index in the file
	 * @param filePath name of CSV file with its path
	 * @return new data frame instance with CSV data
	 */
	public static DataFrame readCSV(String filePath) {
		return readCSV(filePath, true, false);
	}

	/**
	 * Builds data frame from CSV file
	 * @param filePath name of CSV file with its path
	 * @param hasColumnIndex is true if the first raw defines the column index in
	 * CSV file
	 * @param hasRawIndex is true if the first column defines the raw index in CSV
	 * file
	 * @return new data frame instance with CSV data
	 */
	public static DataFrame readCSV(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex) {
		return new DataFrame(filePath, hasColumnIndex, hasRawIndex);
	}

	/**
	 * Creates deep copy of list of lists
	 * @param srcList list of lists to copy from
	 * @return new instance of list of lists with contents from src
	 */
	public static List<? extends List<? extends Comparable<?>>> clone(
			final List<? extends List<? extends Comparable<?>>> srcList) {
		List<List<? extends Comparable<?>>> destList = new ArrayList<>();
		for (List<? extends Comparable<?>> sublist : srcList) {
			destList.add(new ArrayList<>(sublist));
		}
		return destList;
	}

	/**
	 * Prints the complete data frame
	 */
	public void print() {
		System.out.println(this.getColumnIndex());
		Object[] ar = this.getRowIndex().toArray();
		for (int i = 0; i < this.getRowIndex().size(); i++) {
			System.out.print(ar[i]);
			System.out.println(this.getData().getRow(i));
		}
	}

	/**
	 * Prints the first four lines of the data frame
	 */
	public void printFirst() {
		System.out.println(this.getColumnIndex());
		Object[] ar = this.getRowIndex().toArray();
		for (int i = 0; i < this.getRowIndex().size() && i < 4; i++) {
			System.out.print(ar[i]);
			System.out.println(this.getData().getRow(i));
		}
	}

	/**
	 * Prints the last four lines of the data frame
	 */
	public void printLast() {
		System.out.println(this.getColumnIndex());
		Object[] ar = this.getRowIndex().toArray();
		for (int i = 0; i < this.getRowIndex().size(); i++) {
			if ((this.getRowIndex().size() - i) < 5) {
				System.out.print(ar[i]);
				System.out.println(this.getData().getRow(i));
			}
		}
	}

	/**
	 * Prints the specified line as an index
	 * @param lineNumber is a row index
	 */
	public void printLine(final Object lineNumber) {
		this.printLines(lineNumber);
	}

	/**
	 * Prints the specified lines as indexes
	 * @param lines are raw indexes
	 */
	public void printLines(final Object... lines) {
		System.out.println(this.getColumnIndex());
		Object[] ar = this.getRowIndex().toArray();
		for (Object lineName : lines) {
			Integer lineNumber = rowIndex.getNameIndice(lineName);

			if (lineNumber != null && lineNumber < this.getRowIndex().size()) {
				System.out.print(ar[lineNumber]);
				System.out.println(this.getData().getRow(lineNumber));
			} else {
				System.out.println("Out of bounds! Select the number between 0 and " + (this.getRowIndex().size() - 1));
			}
		}
	}

	/**
	 * Prints the complete data frame from the given dataframe
	 * @param dataFrame
	 */
	public static void print(DataFrame dataFrame) {
		dataFrame.print();
	}

	/**
	 * Prints the first four lines of the data frame from the given dataframe
	 * @param dataFrame
	 */
	public static void printFirst(DataFrame dataFrame) {
		dataFrame.printFirst();
	}

	/**
	 * Prints the last four lines of the data frame from the given dataframe
	 * @param dataFrame
	 */
	public static void printLast(DataFrame dataFrame) {
		dataFrame.printLast();
	}

	/**
	 * Prints the specified line as an index from the given dataframe
	 * @param dataFrame
	 * @param lineNumber is a row index
	 */
	public static void printLine(DataFrame dataFrame, Object lineNumber) {
		dataFrame.printLines(lineNumber);
	}

	/**
	 * Prints the specified lines as indexes from the given dataframe
	 * @param dataFrame
	 * @param lineNumbers are raw indexes
	 */
	public static void printLines(DataFrame dataFrame, final Object... lineNumbers) {
		dataFrame.printLines((Object[]) lineNumbers);
	}

	/**
	 * Selects columns from specified column index
	 * @param columnIds is array of column index elements
	 * @return list of selected columns
	 */
	public List<? extends List<? extends Comparable<?>>> selectColumns(final Object... columnIds) {
		List<List<? extends Comparable<?>>> selectedColumnsList = new ArrayList<>();
		for (Object columnId : columnIds) {
			if (columnIndex.getNameIndice(columnId) != null) {
				selectedColumnsList.add(data.getCol(columnIndex.getNameIndice(columnId)));
			} else {
				System.out.println("This index does not exist!");
			}
		}
		return selectedColumnsList;
	}

	/**
	 * Selects rows from specified row index
	 * @param rowIds is array of row index elements
	 * @return list of selected rows
	 */
	public List<? extends List<? extends Comparable<?>>> selectRows(final Object... rowIds) {
		ArrayList<Integer> rowIn = new ArrayList<>();
		for (Object rowId : rowIds) {
			if (rowIndex.getNameIndice(rowId) != null) {
				rowIn.add(rowIndex.getNameIndice(rowId));
			} else {
				System.out.println("This index does not exist!");
			}
		}
		return data.getRows((Integer[]) rowIn.toArray(new Integer[rowIn.size()]));
	}

	/**
	 * Creates new data frame from selected columns
	 * @param columnIds is array of column index elements to select from
	 * @return new instance of data frame with selected column index
	 */
	public DataFrame createFromColumns(final Object... columnIds) {
		return new DataFrame(Collections.emptyList(), Arrays.asList((Object[]) columnIds),
				DataFrame.clone(selectColumns((Object[]) columnIds)));
	}

	/**
	 * Creates new data frame from selected rows
	 * @param rowIds is array of row index elements to select from
	 * @return new instance of data frame with selected row index
	 */
	public DataFrame createFromRows(final Object... rowIds) {
		return new DataFrame(Arrays.asList((Object[]) rowIds), Collections.emptyList(),
				DataFrame.clone(selectRows((Object[]) rowIds)));
	}

	/**
	 * Creates new data frame from selected columns
	 * @param dSource is data frame to get columns from
	 * @param columnIds is array of column index elements to select from
	 * @return new instance of data frame with selected column index
	 */
	public static DataFrame createFromColumns(DataFrame dSource, final Object... columnIds) {
		return new DataFrame(Collections.emptyList(), Arrays.asList((Object[]) columnIds),
				DataFrame.clone(dSource.selectColumns((Object[]) columnIds)));
	}

	/**
	 * Creates new data frame from selected rows
	 * @param dSource is data frame to get columns from
	 * @param rowIds is array of row index elements to select from
	 * @return new instance of data frame with selected row index
	 */
	public static DataFrame createFromRows(DataFrame dSource, final Object... rowIds) {
		return new DataFrame(Arrays.asList((Object[]) rowIds), Collections.emptyList(),
				DataFrame.clone(dSource.selectRows((Object[]) rowIds)));
	}

	public <E> void groupBy(final Object... columnIds) {

		for (Object columnId : columnIds) {
			Map<?, Long> result = getCol(columnId).stream()
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

//			Map<Object, Long> finalMap = new LinkedHashMap<>();
//
//			// Sort a map and add to finalMap
//			result.entrySet().stream().sorted(Map.Entry.<Object, Long>comparingByValue().reversed())
//					.forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

			System.out.println(result);
		}

//		for (Object columnId : columnIds) {
//			List<List<? extends Comparable<?>>> selectedColumnsList = new ArrayList<>();
//
//		}

	}

}
