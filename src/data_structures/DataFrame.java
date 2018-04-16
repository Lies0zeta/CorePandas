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
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataFrame
// TODO Do we need this implements???
// implements Iterable<List<E>>
{
	private final Index index, columns;
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
		this.columns = new Index(columnIndex, newData.size());
		this.index = new Index(rawIndex, newData.length());
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

			// TODO : Remove print
			System.out.println(listStringList);

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
			// TODO Remove print
			System.out.println(listObjectList);
			String name = ((ParameterizedType) listObjectList.get(0).getClass().getGenericSuperclass())
					.getActualTypeArguments()[0].toString();
			System.out.println(name);

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
		this.columns = new Index(columnIndex, newData.size());
		this.index = new Index(rawIndex, newData.length());
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
		return this.getData().getMin(this.columns.getNameIndice(columnName));
	}

	public <T extends Comparable<T>> T getMax(String columnName) {
		return this.getData().getMax(this.columns.getNameIndice(columnName));
	}

	public <T extends Comparable<T>> Double getMean(String columnName) {
		return this.getData().getMean(this.columns.getNameIndice(columnName));
	}
	
	public static DataFrame readCSV(String filePath) {
		return readCSV(filePath, true, false);
	}
	public static DataFrame readCSV(String filePath, Boolean hasColumnIndex, Boolean hasRawIndex) {
		return new DataFrame(filePath, hasColumnIndex, hasRawIndex);
	}
}

// public class DataFrame {
// private ArrayList<String> header;
// private ArrayList<Frame> frames;
// private int nbLines;
// private String separator = ",";
// private static final int TOP = 10;
//
//
// public DataFrame(String[]... tabs) {
// frames = new ArrayList<Frame>();
// header = new ArrayList<String>();
// for (String[] arg : tabs) {
// frames.add(new Frame(arg));
// }
// nbLines = tabs.length;
// }
//
// public DataFrame(String fileName, boolean hasHeader) {
// header = new ArrayList<String>();
// frames = new ArrayList<Frame>();
// File f = new File(fileName);
// FileReader fr;
// BufferedReader br;
// nbLines = 0;
//
// if (f.exists()) {
// //parse file and import data
// try {
// fr = new FileReader(f);
// br = new BufferedReader(fr);
// String s;
// String[] tab;
// int i = 0;
//
// //If the file has a header in its first line
//
// //Parsing line by line
// int k = 0;
// boolean h = hasHeader;
// int j;
// while ((s = br.readLine()) != null) {
// tab = s.split(separator);
// j = 0;
//
// for (String str : tab) {
// if (h) {
// if (k==0) {
// frames.add(new Frame(str));
// }
// header.add(str);
// }
// else {
// if (k==0) {
// frames.add(new Frame(String.valueOf(j)));
// }
// frames.get(j).add(str);
// }
// j++;
// }
// k++;
// h = false;
// }
// nbLines = k;
// if (hasHeader)
// nbLines--;
// }
// catch (Exception e) {
// System.err.println(e.getMessage());
// }
// }
//
// else {
// System.out.println("File does not exist.");
// }
// }
//
// private void printHeader(String... labels) {
// int i = 0;
//
// String[] strs = new String[header.size()];
// strs = header.toArray(strs);
//
// /*if (labels.length > 0) {
// for (String str : labels) {
// System.out.print(str + " | ");
// i += str.length() + 3;
// }
// System.out.println();
// }*/
//
// if (labels.length > 0) {
// strs = labels;
// }
// for (String str : strs) {
// System.out.print(str + " | ");
// i += str.length() + 3;
// }
// System.out.println();
//
// for (int j = 0; j < i; j++) {
// System.out.print("-");
// }
// System.out.println();
// }
//
// private void printRow(int i) {
// for (Frame f : frames) {
// System.out.print(f.get(i) + " | ");
// }
// System.out.println();
// }
//
// /*
// private void printColumn(String label) {
// int i = 0;
// boolean found = false;
// while (i < frames.size() && !found) {
// found = (frames.get(i).getLabel().equals(label));
// }
// if (i < frames.size())
// frames.get(i).print();
// }*/
//
// private void printRows(int start, int end) {
// if (start < 0)
// start = 0;
// if (end > nbLines)
// end = nbLines;
//
// for (int i = start; i < end; i++) {
// printRow(i);
// }
// }
//
// public void print() {
// printHeader();
// printRows(0, nbLines);
// }
//
// public void top() {
// printHeader();
// printRows(0, TOP);
// }
//
// public void tail() {
// printHeader();
// printRows(nbLines-TOP, nbLines);
// }
//
// public void select(int... indexes) {
// printHeader();
// for (int i = 0; i < indexes.length; i++) {
// if (indexes[i] < nbLines)
// printRow(indexes[i]);
// }
// }
//
// private Frame getFrameFromLabel(String s) {
// for (Frame f : frames) {
// if (f.getLabel().equals(s))
// return f;
// }
// return null;
// }
//
// public void select(String... labels) {
// ArrayList<Frame> dataframe = new ArrayList<Frame>();
// Frame f;
// for (String s : labels) {
// f = getFrameFromLabel(s);
// if (f != null)
// dataframe.add(f);
// }
//
// printHeader(labels);
// for (int i = 0; i < nbLines; i++) {
// for (Frame frame : dataframe) {
// System.out.print(frame.get(i) + " | " );
// }
// System.out.println();
// }
// }
//
// public void min(String label) {
// System.out.println("min");
// System.out.println("------");
// Frame f = getFrameFromLabel(label);
// if (f != null) {
// System.out.println(f.getMin());
// }
// }
//
// }
