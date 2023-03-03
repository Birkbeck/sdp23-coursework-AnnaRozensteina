package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * The MulInstruction class represents the multiplication instruction of the SML. <p>
 *
 * @author arozen01
 * @see Instruction
 */
public class MulInstruction extends Instruction {

    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "mul";

    /**
     * Sets label, result and source values of the instance.
     *
     * @param label optional label
     * @param result register in which the result will be stored after addition
     * @param source register which is added to the result register
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Multiplies the contents of register result by the
     * contents of register source and stores the result in register result.
     *
     * @param m machine executing program
     * @return NORMAL_PROGRAM_COUNTER_UPDATE
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 * value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Representation of the mul instruction instance
     * in the form "label: mul r s", where r and s
     * are register names and label is an optional label.
     * If there is no label, the form is "mul r s".
     *
     * @return string representation of the mul instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    /**
     * Compares two instances.
     *
     * @param o object to be compared with
     * @return true if o is equal to an instance of MulInstruction, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MulInstruction other) {
            return Objects.equals(this.result, other.result)
                    && Objects.equals(this.source, other.source);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, source, OP_CODE);
    }
}
