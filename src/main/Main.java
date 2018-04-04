package main;
import data_structures.*;


public class Main {

	public static void main(String[] args) {
		String fileName = "cities.csv"; //addresses.csv //taxables.csv
		DataFrame df = new DataFrame("/home/n/nahalh/DEVOPS/CorePandas/files/" + fileName, true);
		
		df.print();

	}

}
