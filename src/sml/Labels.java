package sml;

import sml.instruction.DivInstruction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		//Add a check that there are no label duplicates.
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
	 */
	public int getAddress(String label) {
		// Where can NullPointerException be thrown here?
		// (Write an explanation.)

		// labels.get(label) can throw NullPointerException.
		// If a label doesn't exist in the program, NPE would be thrown at runtime.
		// If jnz instruction is used but there is no label value passed, NPE would also be thrown.
		//
		//       Add code to deal with non-existent labels.

		if (labels.containsKey(label)) {
			return labels.get(label);
		}
		else {
			throw new NullPointerException("Call to an unknown label " + label);
		}
	}

	/**
	 * representation of this instance,
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
		// implement the method using the Stream API (see also class Registers).
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels other) {
			return Objects.equals(this.labels, other.labels);
		}
		return false;
	}

	@Override
	public int hashCode() { return  labels.hashCode(); }

	// Implement equals and hashCode (needed in class Machine).

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
