package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

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
            return m.getLabels().getAddress(jumpLabel);
        }
        else {
            return NORMAL_PROGRAM_COUNTER_UPDATE;
        }

    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + condition + " " + jumpLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof JnzInstruction other) {
            return Objects.equals(this.condition, other.condition)
                    && Objects.equals(this.jumpLabel, other.jumpLabel)
                    && this.OP_CODE == other.OP_CODE;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, jumpLabel, OP_CODE);
    }
}