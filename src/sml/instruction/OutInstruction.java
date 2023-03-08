package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * The OutInstruction class represents the return or print out instruction of the SML. <p>
 *
 * @author arozen01
 * @see Instruction
 */

public class OutInstruction extends Instruction {

    private final RegisterName result;

    public static final String OP_CODE = "out";

    /**
     * Sets label and result values of the instance.
     *
     * @param label optional label
     * @param result register which is to be returned
     */
    public OutInstruction(String label, RegisterName result) {
        super(label, OP_CODE);
        this.result = result;
    }

    /**
     * Prints the contents of register result on the console.
     *
     * @param m machine executing program
     * @return NORMAL_PROGRAM_COUNTER_UPDATE
     */
    @Override
    public int execute(Machine m) {
        System.out.println(m.getRegisters().get(result));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Representation of the out instruction instance
     * in the form "label: out r", where r is a register name
     * and label is an optional label.
     * If there is no label, the form is "out r".
     *
     * @return string representation of the out instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result;
    }

    /**
     * Compares two instances.
     *
     * @param o object to be compared with
     * @return true if o is equal to an instance of OutInstruction, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof OutInstruction other) {
            return Objects.equals(this.result, other.result);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, OP_CODE);
    }
}