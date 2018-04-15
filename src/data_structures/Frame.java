package data_structures;

import java.util.ArrayList;

public class Frame<E extends Comparable<E> > {
	private String label;
	private ArrayList<E> data;
	
	public Frame(String label) {
		this.label = label;
		data = new ArrayList<E>();
	}
	
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
	
	public E get(int i) {
		return data.get(i); 
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getSize() {
		return data.size();
	}
	
	
	public E getMax() {
		if (data.isEmpty())
			return null;
		
		E max = data.get(0);
		int k;
		for (int i = 1; i < data.size(); i++) {
			k = max.compareTo(data.get(i));
			if (k < 0)
				max = data.get(i);
		}
		return max;

	}
	
	public E getMin() {
		if (data.isEmpty())
			return null;
		
		E min = data.get(0);
		int k;
		for (int i = 1; i < data.size(); i++) {
			k = min.compareTo(data.get(i));
			if (k > 0)
				min = data.get(i);
		}
		return min;
	}
	

	
	public void print() {
		System.out.println(label);
		for (int i = 0; i < label.toCharArray().length; i++) {
			System.out.print('-');
		}
		for (E e : data) {
			System.out.println(e.toString());
		}
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	

}