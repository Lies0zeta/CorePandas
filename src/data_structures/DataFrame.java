package data_structures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataFrame
// TODO Do we need this implements???
// implements Iterable<List<E>>
{
	private final Index index, columns;
	private final Data<Object> data;

	public static enum NumberDefault {
		LONG_DEFAULT, DOUBLE_DEFAULT
	}

	public DataFrame() {
		this(Collections.<List<Object>>emptyList());
	}

	public DataFrame(final Object... columns) {
		this(Arrays.asList((Object[]) columns));
	}

	public DataFrame(final Collection<?> columns) {
		this(Collections.emptyList(), columns, Collections.<List<?>>emptyList());
	}

	public DataFrame(final Collection<?> index, final Collection<?> columns) {
		this(index, columns, Collections.<List<?>>emptyList());
	}

	public DataFrame(final List<? extends List<?>> data) {
		this(Collections.emptyList(), Collections.emptyList(), data);
	}

	public DataFrame(final Collection<?> rawIndex, final Collection<?> columnIndex,
			final List<? extends List<?>> data) {
		final Data<Object> newData = new Data<>(data);
		newData.reshape(Math.max(newData.size(), columnIndex.size()), Math.max(newData.length(), rawIndex.size()));

		this.data = newData;
		this.columns = new Index(columnIndex, newData.size());
		this.index = new Index(rawIndex, newData.length());
	}

	public int size() {
		return data.size();
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

	public static void readCSV() {

		try {
			Reader in = new FileReader("files/test.csv");

			CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			List<CSVRecord> records = parser.getRecords();

			int columnsNumber = records.get(0).size();
			int rowsNumber = records.size();
			// Initializing structures
			List<List<String>> l = new ArrayList<>();
			for (int i = 0; i < columnsNumber; i++) {
				// l.add(DataFrame.getType(records.get(0).get(0)));

				List<String> ll = new ArrayList<>();
				l.add(ll);
			}

			// Filling in the records
			for (int i = 0; i < rowsNumber; i++) {
				CSVRecord record = records.get(i);
				for (int j = 0; j < columnsNumber; j++) {
					l.get(j).add(record.get(j));
				}
			}

			// TODO : Remove print
			System.out.println(l);

			List<List<?>> newL = new ArrayList<>();
			for (int i = 0; i < columnsNumber; i++) {
				// ArrayList<?> newList;// = new ArrayList<>();
				try {
					Integer.valueOf(l.get(i).get(0));
					ArrayList<Integer> newList = new ArrayList<>();
					for (String str : l.get(i)) {
						newList.add(Integer.valueOf(str));
					}
					newL.add(newList);
				} catch (NumberFormatException iE) {
					try {
						Long.valueOf(l.get(i).get(0));
						ArrayList<Long> newList = new ArrayList<>();
						for (String str : l.get(i)) {
							newList.add(Long.valueOf(str));
						}
						newL.add(newList);
					} catch (NumberFormatException lE) {
						try {
							Double.valueOf(l.get(i).get(0));
							ArrayList<Double> newList = new ArrayList<>();
							for (String str : l.get(i)) {
								newList.add(Double.valueOf(str));
							}
							newL.add(newList);
						} catch (NumberFormatException dE) {
							try {
								new SimpleDateFormat("dd/MM/yyyy").parse(l.get(i).get(0));
								ArrayList<Date> newList = new ArrayList<>();
								for (String str : l.get(i)) {
									newList.add(new SimpleDateFormat("dd/MM/yyyy").parse(str));
								}
								newL.add(newList);
							} catch (ParseException e1) {
								try {
									new SimpleDateFormat("dd-MMM-yyyy").parse(l.get(i).get(0));
									ArrayList<Date> newList = new ArrayList<>();
									for (String str : l.get(i)) {
										newList.add(new SimpleDateFormat("dd-MMM-yyyy").parse(str));
									}
									newL.add(newList);
								} catch (ParseException e2) {
									try {
										new SimpleDateFormat("MM dd, yyyy").parse(l.get(i).get(0));
										ArrayList<Date> newList = new ArrayList<>();
										for (String str : l.get(i)) {
											newList.add(new SimpleDateFormat("MM dd, yyyy").parse(str));
										}
										newL.add(newList);
									} catch (ParseException e3) {
										try {
											new SimpleDateFormat("E, MMM dd yyyy").parse(l.get(i).get(0));
											ArrayList<Date> newList = new ArrayList<>();
											for (String str : l.get(i)) {
												newList.add(new SimpleDateFormat("E, MMM dd yyyy").parse(str));
											}
											newL.add(newList);
										} catch (ParseException e4) {
											try {
												new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(l.get(i).get(0));
												ArrayList<Date> newList = new ArrayList<>();
												for (String str : l.get(i)) {
													newList.add(
															new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(str));
												}
												newL.add(newList);
											} catch (ParseException e5) {
												try {
													new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(l.get(i).get(0));
													ArrayList<Date> newList = new ArrayList<>();
													for (String str : l.get(i)) {
														newList.add(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
																.parse(str));
													}
													newL.add(newList);
												} catch (ParseException e6) {
													ArrayList<String> newList = new ArrayList<>();
													for (String str : l.get(i)) {
														newList.add(new String(str));
													}
													newL.add(newList);
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
			System.out.println(newL);
			//newL.get(0).values().stream().mapToInt(i -> i.intValue()).sum();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
