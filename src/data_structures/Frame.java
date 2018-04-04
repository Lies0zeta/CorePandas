package data_structures;

import java.util.ArrayList;

public class Frame<E> {
	private String label;
	private ArrayList<E> data;
	
	public Frame(String[] content) {
		if (content.length > 0) {
			this.label = content[0];
			for (String str : content) {
				data.add((E) str);
			}
		}
	}
	
	public void add(String str) {
		data.add((E) str);
	}

}
