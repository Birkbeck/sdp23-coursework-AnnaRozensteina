package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * The MovInstruction class represents the storing instruction of the SML. <p>
 *
 * @author arozen01
 * @see Instruction
 */
public class MovInstruction extends Instruction {

    private final RegisterName result;
    private final int value;

    public static final String OP_CODE = "mov";

    /**
     * Sets label, result and value values of the instance.
     *
     * @param label optional label
     * @param result register in which the integer will be stored
     * @param value integer value that will be stored in the register
     */
    public MovInstruction(String label, RegisterName result, int value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    /**
     * Stores the passed in integer value in the register result.
     *
     * @param m machine executing program
     * @return NORMAL_PROGRAM_COUNTER_UPDATE
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Representation of the mov instruction instance
     * in the form "label: mov r x", where r is a register,
     * x is an integer value, and label is an optional label.
     * If there is no label, the form is "mov r x".
     *
     * @return string representation of the mov instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }

    /**
     * Compares two instances.
     *
     * @param o object to be compared with
     * @return true if o is equal to an instance of MovInstruction, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MovInstruction other) {
            return Objects.equals(this.result, other.result)
                    && Objects.equals(this.value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, value, OP_CODE);
    }
}
