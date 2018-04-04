package data_structures;


import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DataFrame {
	ArrayList<String> header;
	ArrayList<Frame> frames;
	int nbLines;
	String separator = ",";
	
	
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
		System.out.println(fileName);
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
					System.out.println(k + "ième ligne : " + s);
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
	
	public void print() {
		System.out.println("nbLines = " + nbLines);
		for (String str : header) {
			System.out.print(str + " | ");
		}
		System.out.println();
		for (int i = 0; i < nbLines; i++) {
			for (Frame f : frames) {
				System.out.print(f.get(i) + " | ");
			}
			System.out.println();
		}
	}
	
	
}
