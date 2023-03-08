package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class Labels represents the labels of the program in the form of a HashMap. <p>
 * Labels must be unique and each label has an address associated with it.
 *
 * @author BBK, arozen01
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 * Label cannot be null.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 * @throws IllegalArgumentException if a duplicate label is encountered
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (!labels.containsKey(label)){
			labels.put(label, address);
		}
		else{
			throw new IllegalArgumentException("Label: " + label + " is duplicated. Labels must be unique.");
		}
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 * @throws NullPointerException if an unknown label is called
	 */
	public int getAddress(String label) {
		// Where can NullPointerException be thrown here?

		// labels.get(label) can throw NullPointerException.
		// If a label doesn't exist in the program, NPE would be thrown at runtime.
		// If jnz instruction is used but there is no label value passed, NPE would also be thrown.

		if (labels.containsKey(label)) {
			return labels.get(label);
		}
		else {
			throw new NullPointerException("Call to an unknown label " + label);
		}
	}

	/**
	 * String representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> e.getKey() + " -> " + e.getValue())
				.collect(Collectors.joining(", ", "[", "]")) ;
	}

	/**
	 * Compares two instances.
	 *
	 * @param o object to be compared with
	 * @return true if o is equal to an instance of Labels, otherwise false
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels other) {
			return Objects.equals(this.labels, other.labels);
		}
		return false;
	}

	@Override
	public int hashCode() { return  labels.hashCode(); }

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
