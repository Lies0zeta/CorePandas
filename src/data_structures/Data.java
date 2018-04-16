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

	/**
	 * 
	 * @return
	 */
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