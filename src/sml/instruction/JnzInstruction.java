package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * The JnzInstruction class represents the jump or branch instruction of the SML. <p>
 *
 * @author arozen01
 * @see Instruction
 */

public class JnzInstruction extends Instruction {

    private final RegisterName condition;
    private final String jumpLabel;

    public static final String OP_CODE = "jnz";

    /**
     * Sets label, condition and jumpLabel values of the instance.
     *
     * @param label optional label
     * @param condition register to be checked
     * @param jumpLabel label name for the next instruction
     */
    public JnzInstruction(String label, RegisterName condition, String jumpLabel) {
        super(label, OP_CODE);
        this.condition = condition;
        this.jumpLabel = jumpLabel;
    }

    /**
     * If the contents of register condition is not zero,
     * then make the statement labeled jumpLabel the next statement to execute.
     * Otherwise, return normal program counter update which to carry on with the next
     * instruction in order.
     *
     * @param m machine executing program
     * @return NORMAL_PROGRAM_COUNTER_UPDATE or jumpLabel address
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(condition);
        if (value1 != 0 ) {
            return m.getLabels().getAddress(jumpLabel);
        }
        else {
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        }

    }

    /**
     * Representation of the jnz instruction instance
     * in the form "label: jnz r jLabel", where r is a register name,
     * jLabel is the label of the next instruction and label is an optional label.
     * If there is no label, the form is "jnz r jLabel".
     *
     * @return string representation of the jnz instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + condition + " " + jumpLabel;
    }

    /**
     * Compares two instances.
     *
     * @param o object to be compared with
     * @return true if o is equal to an instance of JnzInstruction, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof JnzInstruction other) {
            return Objects.equals(this.condition, other.condition)
                    && Objects.equals(this.jumpLabel, other.jumpLabel);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, jumpLabel, OP_CODE);
    }
}