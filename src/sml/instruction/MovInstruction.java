package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class MovInstruction extends Instruction {
    private final RegisterName result;
    private final int value;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName result, int value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = value;
        m.getRegisters().set(result, value1 + value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MovInstruction other) {
            return Objects.equals(this.result, other.result)
                    && Objects.equals(this.value, other.value)
                    && this.OP_CODE == other.OP_CODE;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, value, OP_CODE);
    }
}
