package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class JnzInstruction extends Instruction {
    private final RegisterName condition;
    private final String jumpLabel;

    public static final String OP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName condition, String jumpLabel) {
        super(label, OP_CODE);
        this.condition = condition;
        this.jumpLabel = jumpLabel;
    }

    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(condition);
        if (value1 != 0 ) {
            m.setProgramCounter(m.getLabels().getAddress(jumpLabel)-1);
        }
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + condition + " " + jumpLabel;
    }
}