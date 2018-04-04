package data_structures;


import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DataFrame {
	private ArrayList<String> header;
	private ArrayList<Frame> frames;
	private int nbLines;
	private String separator = ",";
	private static final int TOP = 10;
	
	
	public DataFrame(String[]... tabs) {
		frames = new ArrayList<Frame>();
		for (String[] arg : tabs) {
			frames.add(new Frame(arg));			
		}
		nbLines = tabs.length;
	}
	
	public DataFrame(String fileName, boolean hasHeader) {
		header = new ArrayList<String>();
		frames = new ArrayList<Frame>();
		File f = new File(fileName);
		FileReader fr;
		BufferedReader br;
		nbLines = 0;
		
		if (f.exists()) {
			//parse file and import data
			try {
				fr = new FileReader(f);
				br = new BufferedReader(fr);
				String s;
				String[] tab;
				int i = 0;
				
				//If the file has a header as its first line
				if (hasHeader) {
					s = br.readLine();
					tab = s.split(separator);
					for (String str : tab) {
						header.add(str);	
						frames.add(new Frame(str));
					}
				}
				
				//Parsing line by line
				int k = 0;
				while ((s = br.readLine()) != null) {
					tab = s.split(separator);
					int j = 0;
					for (String str : tab) {
						frames.get(j).add(str);
						j++;
					} 
					k++;
				}
				nbLines = k;
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}						
		}
		
		else {
			System.out.println("File does not exist.");
		}
	}
	
	private void printHeader() {
		for (String str : header)
			System.out.print(str + " | ");	
		System.out.println();
	}
	
	private void printLines(int start, int end) {
		if (start < 0)
			start = 0;
		if (end > nbLines)
			end = nbLines;
		

		for (int i = start; i < end; i++) {
			for (Frame f : frames)
				System.out.print(f.get(i) + " | ");
			System.out.println();
		}
	}

	public void print() {
		printHeader();
		printLines(0, nbLines);
	}
	
	public void top() {
		printHeader();
		printLines(0, TOP);
	}
	
	public void tail() {
		printHeader();
		printLines(nbLines-TOP, nbLines);
	}
	
}
