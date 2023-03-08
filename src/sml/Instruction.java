package sml;

/**
 * Abstract class Instruction represents an abstract instruction.
 *
 * @author BBK, arozen01
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language).
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	/**
	 * Method returning the optional label of the instruction.
	 * @return label (can be null)
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Method returning the opcode of the instruction.
	 * @return opcode
	 */
	public String getOpcode() {
		return opcode;
	}

	// indicates that the next instruction to be executed will be the next sequential instruction
	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed
	 */
	public abstract int execute(Machine machine);

	/**
	 * Method returning string representation of the label.
	 *
	 * @return null if label is null, otherwise label in form "label: "
	 */
	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	// What does abstract in the declaration below mean?

	// It means that all subclasses (that are not themselves abstract) will need to provide an
	// implementation for these superclass methods or alternatively be declared abstract themselves,
	// otherwise the subclass won't compile. The abstract method
	// It also means that the subclass won't have access to the method implementation that Instruction class
	// superclass may have, it is essentially overriden here but not implemented.

	/**
	 * Method returning representation of the instruction instance.
	 *
	 * @return string representation of the instruction
	 */
	@Override
	public abstract String toString();

	/**
	 * Compares two instances.
	 *
	 * @param o object to be compared with
	 * @return true if o is equal to an instance of AddInstruction, otherwise false
	 */
	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

}
