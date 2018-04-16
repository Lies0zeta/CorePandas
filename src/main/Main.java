package main;
import data_structures.*;


public class Main {

	public static void main(String[] args) {
		DataFrame df = new DataFrame("files/test.csv", true, false);
		System.out.println(df.getData().getMean(0).toString());
		System.out.println(df.getMin("City").toString());
		System.out.println(df.getMax("LonD").toString());
		System.out.println(df.getMean("LatM").toString());
		
	}

}
