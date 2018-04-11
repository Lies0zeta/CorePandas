package main;
import data_structures.*;


public class Main {

	public static void main(String[] args) {
		String fileName = "taxables.csv"; //cities.csv //addresses.csv //taxables.csv
		DataFrame df = new DataFrame("/home/n/nahalh/DEVOPS/CorePandas/files/" + fileName, true);
		
		df.print();
		/*df.top();
		df.tail();
		System.out.println("-------------");
		df.select("City", "LatS");
		df.min("City");*/
	}

}
