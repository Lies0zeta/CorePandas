package CorePandas.CorePandas.data_structures;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class Index {
	/**
	 * In the context of hash map:
	 * KEY is NAME (row/column index key in data frame),
	 * VALUE is INDICE (raw/column index value in data frame)
	 */
	private final LinkedHashMap<Object, Integer> index;

	/**
	 * Creates an index with an empty list
	 */
	public Index() {
		this(Collections.<Object>emptyList());
	}

	/**
	 * Creates an index from the given collection
	 * @param names
	 * is the given collection
	 */
	public Index(final Collection<?> names) {
		this.index = new LinkedHashMap<>(names.size());
		final Iterator<?> it = names.iterator();
		for (int i = 0; i < names.size(); i++) {
			final Object name = it.hasNext() ? it.next() : i;
			setNameIndice(name, i);
		}
	}

	// TODO Remove method maybe???
	/**
	 * Creates an index from the given collection and with given size
	 * @param names
	 * @param size
	 */
	public Index(final Collection<?> names, Integer size) {
		this.index = new LinkedHashMap<>(names.size());
		final Iterator<?> it = names.iterator();
		for (int i = 0; i < size; i++) {
			final Object name = it.hasNext() ? it.next() : i;
			setNameIndice(name, i);
		}
	}

	/**
	 * Getter of names
	 * @return a set of names
	 */
	public Set<Object> getNames() {
		final Set<Object> names = this.index.keySet();
		if (names == null) {
			throw new BadIndexException("I am afraid that the index doesn't exist.\n");
		}
//		if (names.isEmpty()) {
//			throw new BadIndexException("I am afraid that the index is empty.\n");
//		}
		final Collection<Integer> indices = getNameIndices();
		if (indices == null /*|| indices.isEmpty()*/ || names.size() != indices.size()) {
			throw new BadIndexException("I am afraid that the index is broken.\n");
		}
		return names;
	}

	/**
	 * Getter of indice corresponding to the given name
	 * @param name is an object to find indices for
	 * @return indice of name
	 */
	public Integer getNameIndice(final Object name) {
		final Integer indice = this.index.get(name);
//		if (indice == null) {
//			throw new BadIndexException("I am afraid that the absent index name " + name + " was given.\n");
//		}
		return indice;
	}

	/**
	 * Getter of all indices
	 * @return a collection of indices
	 */
	public Collection<Integer> getNameIndices() {
		final Collection<Integer> indices = this.index.values();
		if (indices == null) {
			throw new BadIndexException("I am afraid that the index doesn't exist.\n");
		}
//		if (indices.isEmpty()) {
//			throw new BadIndexException("I am afraid that the index is empty.\n");
//		}
		return indices;
	}

	/**
	 * Getter of indices corresponding to given names
	 * @param names is a table to find indices for
	 * @return a table of indices corresponding to names
	 */
	public Integer[] getNameIndices(final Object[] names) {
		if (names == null || names.length == 0) {
			throw new BadIndexException("I am afraid that the table doesn't exist or is empty.\n");
		}
		return getNameIndices(Arrays.asList(names));
	}

	/**
	 * Getter of indices corresponding to given names
	 * @param names is a list to find indices for
	 * @return a table of indices corresponding to names
	 */
	public Integer[] getNameIndices(final List<Object> names) {
		if (names == null || names.size() == 0) {
			throw new BadIndexException("I am afraid that the list doesn't exist or is empty.\n");
		}
		final int size = names.size();
		final Integer[] indices = new Integer[size];
		for (int i = 0; i < size; i++) {
			indices[i] = getNameIndice(names.get(i));
		}
		return indices;
	}

	/**
	 * Inserting new mapping
	 * @param newName to insert
	 * @param newIndice to insert
	 */
	public void setNameIndice(final Object newName, final Integer newIndice) {
		if (this.index.putIfAbsent(newName, newIndice) != null) {
			throw new BadIndexException("I am afraid that the duplicate index name " + newName + " was found.\n");
		}
	}

	/**
	 * Inserting changed mappings preserving order
	 * @param changedMaps to insert
	 */
	public void changeNameIndice(final LinkedHashMap<Object, Integer> changedMaps) {
		final LinkedHashMap<Object, Integer> newIndex = new LinkedHashMap<>();
		for (final Map.Entry<Object, Integer> mapi : index.entrySet()) {
			final Object namei = mapi.getKey();
			if (changedMaps.keySet().contains(namei)) {
				newIndex.put(changedMaps.get(namei), mapi.getValue());
			} else {
				newIndex.put(namei, mapi.getValue());
			}
		}
		index.clear();
		index.putAll(newIndex);
	}

}
