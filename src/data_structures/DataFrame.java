package data_structures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataFrame {
	private final Index rawIndex, columnIndex;
	private final Data<? extends Comparable<?>> data;

	public DataFrame() {
		this(Collections.<List<Object>>emptyList());
	}

	public DataFrame(final Object... columns) {
		this(Arrays.asList((Object[]) columns));
	}

	public DataFrame(final Collection<?> columns) {
		this(Collections.emptyList(), columns, Collections.<List<? extends Comparable<?>>>emptyList());
	}

	public DataFrame(final Collection<?> index, final Collection<?> columns) {
		this(index, columns, Collections.<List<?>>emptyList());
	}

	public DataFrame(final List<? extends List<?>> data) {
		this(Collections.emptyList(), Collections.emptyList(), data);
	}
	
	public DataFrame(final Collection<?> rawIndex, final Collection<?> columnIndex,
			final List<? extends List<? extends Comparable<?>>> data) {
		final Data<?> newData = new Data<>(data);
		newData.reshape(Math.max(newData.size(), columnIndex.size()), Math.max(newData.length(), rawIndex.size()));

		this.data = newData;
		this.columnIndex = new Index(columnIndex, newData.size());
		this.rawIndex = new Index(rawIndex, newData.length());
	}

    public Set<Object> getColumnsIndex() {
        return columnIndex.getNames();
    }
    
    public Set<Object> getRawIndex() {
        return rawIndex.getNames();
    }
    
    public Set<Object> getCol(final Integer column) {
    	return new HashSet<Object>(data.getCol(column));
    }
    
    public Set<Object> getCol(final Object column) {
        return getCol(columnIndex.getNameIndice(column));
    }

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
			
			String name = ((ParameterizedType) listObjectList.get(0).getClass().getGenericSuperclass())
					.getActualTypeArguments()[0].toString();

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
		this.rawIndex = new Index(rawIndex, newData.length());
	}
	
	public int size() {
		return data.size();
	}
	
	public Data<? extends Comparable<?>> getData() {
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

	public <T extends Comparable<T>> T getMin(String columnName) {
		return this.getData().getMin(this.columnIndex.getNameIndice(columnName));
	}

	public <T extends Comparable<T>> T getMax(String columnName) {
		return this.getData().getMax(this.columnIndex.getNameIndice(columnName));
	}

	public <T extends Comparable<T>> Double getMean(String columnName) {
		return this.getData().getMean(this.columnIndex.getNameIndice(columnName));
	}
	
	public static DataFrame readCSV(String filePath) {
		return readCSV(filePath, true, false);
	}
	public static DataFrame readCSV(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex) {
		return new DataFrame(filePath, hasColumnIndex, hasRawIndex);
	}
}
