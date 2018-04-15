package data_structures;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataFrame
		// TODO Do we need this implements???
//		implements Iterable<List<E>> 
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

//	@Override
//	public Iterator<List<E>> iterator() {
//		// TODO Do we need this method??
//		return null;
//	}

	@Override
	public String toString() {
		//Iterator<Data<?>> it;
		return data.toString();
	}



}

//public class DataFrame {
//	private ArrayList<String> header;
//	private ArrayList<Frame> frames;
//	private int nbLines;
//	private String separator = ",";
//	private static final int TOP = 10;
//	
//	
//	public DataFrame(String[]... tabs) {
//		frames = new ArrayList<Frame>();
//		header = new ArrayList<String>();
//		for (String[] arg : tabs) {
//			frames.add(new Frame(arg));			
//		}
//		nbLines = tabs.length;
//	}
//	
//	public DataFrame(String fileName, boolean hasHeader) {
//		header = new ArrayList<String>();
//		frames = new ArrayList<Frame>();
//		File f = new File(fileName);
//		FileReader fr;
//		BufferedReader br;
//		nbLines = 0;
//		
//		if (f.exists()) {
//			//parse file and import data
//			try {
//				fr = new FileReader(f);
//				br = new BufferedReader(fr);
//				String s;
//				String[] tab;
//				int i = 0;
//				
//				//If the file has a header in its first line
//				
//				//Parsing line by line
//				int k = 0;
//				boolean h = hasHeader;
//				int j;				
//				while ((s = br.readLine()) != null) {
//					tab = s.split(separator);
//					j = 0;
//					
//					for (String str : tab) {
//						if (h) {
//							if (k==0) {
//								frames.add(new Frame(str));
//							}
//							header.add(str);
//						}
//						else {
//							if (k==0) {
//								frames.add(new Frame(String.valueOf(j)));
//							}
//							frames.get(j).add(str);
//						}
//						j++;
//					}
//					k++;
//					h = false;
//				}
//				nbLines = k;
//				if (hasHeader)
//					nbLines--;
//			}
//			catch (Exception e) {
//				System.err.println(e.getMessage());
//			}						
//		}
//		
//		else {
//			System.out.println("File does not exist.");
//		}
//	}
//	
//	private void printHeader(String... labels) {
//		int i = 0;
//		
//		String[] strs = new String[header.size()];
//		strs = header.toArray(strs);
//		
//		/*if (labels.length > 0) {
//			for (String str : labels) {
//				System.out.print(str + " | ");
//				i += str.length() + 3;
//			}
//			System.out.println();
//		}*/
//		
//		if (labels.length > 0) {
//			strs = labels;
//		}
//		for (String str : strs) {
//			System.out.print(str + " | ");	
//			i += str.length() + 3;
//		}
//		System.out.println();
//		
//		for (int j = 0; j < i; j++) {
//			System.out.print("-");
//		}
//		System.out.println();
//	}
//	
//	private void printRow(int i) {
//		for (Frame f : frames) {
//			System.out.print(f.get(i) + " | ");
//		}
//		System.out.println();
//	}
//	
//	/*	
//	private void printColumn(String label) {
//		int i = 0;
//		boolean found = false;
//		while (i < frames.size() && !found) {
//			found = (frames.get(i).getLabel().equals(label));
//		}
//		if (i < frames.size())
//			frames.get(i).print();
//	}*/
//	
//	private void printRows(int start, int end) {
//		if (start < 0)
//			start = 0;
//		if (end > nbLines)
//			end = nbLines;		
//
//		for (int i = start; i < end; i++) {
//			printRow(i);
//		}
//	}
//
//	public void print() {
//		printHeader();
//		printRows(0, nbLines);
//	}
//	
//	public void top() {
//		printHeader();
//		printRows(0, TOP);
//	}
//	
//	public void tail() {
//		printHeader();
//		printRows(nbLines-TOP, nbLines);
//	}
//	
//	public void select(int... indexes) {
//		printHeader();
//		for (int i = 0; i < indexes.length; i++) {
//			if (indexes[i] < nbLines)
//				printRow(indexes[i]);
//		}		
//	}
//	
//	private Frame getFrameFromLabel(String s) {
//		for (Frame f : frames) {
//			if (f.getLabel().equals(s))
//				return f;
//		}
//		return null;
//	}
//	
//	public void select(String... labels) {
//		ArrayList<Frame> dataframe = new ArrayList<Frame>();
//		Frame f;
//		for (String s : labels) {
//			f = getFrameFromLabel(s);
//			if (f != null)
//				dataframe.add(f);
//		}
//	
//		printHeader(labels);
//		for (int i = 0; i < nbLines; i++) {
//			for (Frame frame : dataframe) {
//				System.out.print(frame.get(i) + " | " );
//			}
//			System.out.println();
//		}
//	}
//	
//	public void min(String label) {
//		System.out.println("min");
//		System.out.println("------");
//		Frame f = getFrameFromLabel(label);
//		if (f != null) {
//			System.out.println(f.getMin());
//		}
//	}
//	
//}
