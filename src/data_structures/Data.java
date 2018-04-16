package data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Data<E extends Comparable<?>> {
	private final List<List<E>> data;

	/**
	 * 
	 */
	public Data() {
		this(Collections.<List<E>>emptyList());
	}

	/**
	 * 
	 * @param data
	 */
	public Data(final Collection<? extends Collection<? extends E>> data) {
		this.data = new LinkedList<>();
		for (final Collection<? extends E> dataColumn : data) {
			add(new ArrayList<>(dataColumn));
		}
	}

	/**
	 * 
	 * @param columnNumber
	 * @param rowNumber
	 */
	public void reshape(final int columnNumber, final int rowNumber) {
		for (int c = data.size(); c < columnNumber; c++) {
			add(new ArrayList<E>(rowNumber));
		}

		for (final List<E> dataColumn : data) {
			for (int r = dataColumn.size(); r < rowNumber; r++) {
				dataColumn.add(null);
			}
		}
	}

	/**
	 * 
	 * @param columnNumber
	 * @param rowNumber
	 * @return
	 */
	public E get(final int columnNumber, final int rowNumber) {
		return data.get(columnNumber).get(rowNumber);
	}

	/**
	 * 
	 * @param value
	 * @param columnNumber
	 * @param rowNumber
	 */
	public void set(final E value, final int colNumber, final int rowNumber) {
		data.get(colNumber).set(rowNumber, value);
	}

	/**
	 * 
	 * @param dataColumn
	 */
	public void add(final List<E> dataColumn) {
		final int len = length();
		for (int r = dataColumn.size(); r < len; r++) {
			dataColumn.add(null);
		}
		data.add(dataColumn);
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return data.size();
	}

	/**
	 * 
	 * @return
	 */
	public int length() {
		return data.isEmpty() ? 0 : data.get(0).size();
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return data.toString();
	}

	@SuppressWarnings("unchecked")
	public<T extends Comparable<T>> T getMin(int columnIndex) {
		return Collections.min((Collection<T>) data.get(columnIndex));
	}

}

// public class Frame<E extends Comparable<E> > {
// private String label;
// private ArrayList<E> data;
//
// public Frame(String label) {
// this.label = label;
// data = new ArrayList<E>();
// }
//
// public Frame(String[] content) {
// if (content.length > 0) {
// this.label = content[0];
// for (String str : content) {
// data.add((E) str);
// }
// }
// }
//
// public void add(String str) {
// data.add((E) str);
// }
//
// public E get(int i) {
// return data.get(i);
// }
//
// public String getLabel() {
// return label;
// }
//
// public int getSize() {
// return data.size();
// }
//
//
// public E getMax() {
// if (data.isEmpty())
// return null;
//
// E max = data.get(0);
// int k;
// for (int i = 1; i < data.size(); i++) {
// k = max.compareTo(data.get(i));
// if (k < 0)
// max = data.get(i);
// }
// return max;
//
// }
//
// public E getMin() {
// if (data.isEmpty())
// return null;
//
// E min = data.get(0);
// int k;
// for (int i = 1; i < data.size(); i++) {
// k = min.compareTo(data.get(i));
// if (k > 0)
// min = data.get(i);
// }
// return min;
// }
//
//
//
// public void print() {
// System.out.println(label);
// for (int i = 0; i < label.toCharArray().length; i++) {
// System.out.print('-');
// }
// for (E e : data) {
// System.out.println(e.toString());
// }
// }
//
// public boolean isEmpty() {
// return data.isEmpty();
// }
//
//
//
// }
