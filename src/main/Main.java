package main;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data_structures.*;


public class Main {
	
	public static List<? extends List<?>> randData(int maxRaws, int maxColumns) {
		Random rand = new Random(System.currentTimeMillis());
		List<List<Integer>> data = new ArrayList<>();
		data.add(new ArrayList<>());
		
		int columns = (rand.nextInt() % maxColumns) + 1;
		for(int i=0; i<columns; i++)
			data.add(new ArrayList<>());
		
		
		for(int i=0; i<columns; i++) {
			int rawlenght = (rand.nextInt() % maxRaws) + 1;
			for(int j=0; j<rawlenght; j++) {
				data.get(i+1).add(rand.nextInt());
				if(!data.get(0).contains(j))
					data.get(0).add(j);
			}
		}
		
		return data;
	}
	
	public static void main(String[] args) {
//		//String fileName = "taxables.csv"; //cities.csv //addresses.csv //taxables.csv
//		//DataFrame df = new DataFrame("/home/n/nahalh/DEVOPS/CorePandas/files/" + fileName, true);		
//		//df.print();
//		/*df.top();
//		df.tail();
//		System.out.println("-------------");
//		df.select("City", "LatS");
//		df.min("City");*/
//		List<? extends List<?>> la = randData(20,7);
//		ArrayList<String> l = new ArrayList<String>();
//		ArrayList<Integer> j = new ArrayList<Integer>();
//		l.add("a");
//		l.add("b");
//		j.add(4);
//		j.add(7);
//		j.add(23);
//		ArrayList<Integer> i = new ArrayList<Integer>();
//		for(int k=0;k<la.size();k++)
//			i.add(k);
//
//		final DataFrame frame = new DataFrame(la.remove(0),i,la);
//		System.out.println(frame);
		//System.out.println(i.toString());
		System.out.println(DataFrame.readCSV("files/taxables.csv"));
		DataFrame df = new DataFrame("files/test.csv", true, false);
		System.out.println(df.getMean("LonD").toString());
		System.out.println(df.getMin("City").toString());
		System.out.println(df.getMax("LonD").toString());
		System.out.println(df.getMean("LatM").toString());
		DataFrame.print(df);
		DataFrame.printFirst(df);
		DataFrame.printLast(df);
		df.printLine(7);
		DataFrame.printLines(df,0,4,5);
		df.createFromColumns("LonD", "City").print();
		df.createFromRows(0,2).print();
	}

}
