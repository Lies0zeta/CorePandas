package data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Data<E extends Comparable<?>> {
	private final List<List<E>> data;

	/**
	 * Creates an empty instance of Data
	 */
	public Data() {
		this(Collections.<List<E>>emptyList());
	}

	/**
	 * Creates an instance of Data with provided data
	 * @param data
	 */
	public Data(final Collection<? extends Collection<? extends E>> data) {
		this.data = new LinkedList<>();
		for (final Collection<? extends E> dataColumn : data) {
			add(new ArrayList<>(dataColumn));
		}
	}

	/**
	 * Reshapes shorter lists to resemble an array 
	 * @param columnsNumber
	 * @param rowsNumber
	 */
	public void reshape(final int columnsNumber, final int rowsNumber) {
		for (int c = data.size(); c < columnsNumber; c++) {
			add(new ArrayList<E>(rowsNumber));
		}

		for (final List<E> dataColumn : data) {
			for (int r = dataColumn.size(); r < rowsNumber; r++) {
				dataColumn.add(null);
			}
		}
	}

	public E get(final int columnNumber, final int rowNumber) {
		return data.get(columnNumber).get(rowNumber);
	}

	public void set(final E value, final int colNumber, final int rowNumber) {
		data.get(colNumber).set(rowNumber, value);
	}

	public void add(final List<E> dataColumn) {
		final int len = length();
		for (int r = dataColumn.size(); r < len; r++) {
			dataColumn.add(null);
		}
		data.add(dataColumn);
	}

	public int size() {
		return data.size();
	}  
    
    List<E> getCol(final Integer colNumber) {
    	return data.get(colNumber);
    }
    
    /**
     * 
     */
    @Override
    public String toString() {
    	String toString = "";
    	
    	for(int i=0;i < length(); i++) {
    		for(int j=0; j < size(); j++)
    			toString += get(j, i)+" ";
    		toString += "\n";
    	}
        return toString;
    }

	public int length() {
		return data.isEmpty() ? 0 : data.get(0).size();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> T getMin(int columnIndex) {
		return Collections.min((Collection<T>) data.get(columnIndex));
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> T getMax(int columnIndex) {
		return Collections.max((Collection<T>) data.get(columnIndex));
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> Double getMean(int columnIndex) {
		if (data.get(columnIndex).get(0) instanceof Integer) {
			return ((List<Integer>) data.get(columnIndex)).stream().mapToInt(Integer::intValue).summaryStatistics()
					.getAverage();
		} else if (data.get(columnIndex).get(0) instanceof Double) {
			return ((List<Double>) data.get(columnIndex)).stream().mapToInt(Double::intValue).summaryStatistics()
					.getAverage();
		} else if (data.get(columnIndex).get(0) instanceof Long) {
			return ((List<Long>) data.get(columnIndex)).stream().mapToInt(Long::intValue).summaryStatistics()
					.getAverage();
		} else if (data.get(columnIndex).get(0) instanceof Float) {
			return ((List<Float>) data.get(columnIndex)).stream().mapToInt(Float::intValue).summaryStatistics()
					.getAverage();
		} else {
			return null;
		}
	}
}