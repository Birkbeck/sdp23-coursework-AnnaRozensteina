package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class OutInstruction extends Instruction {
    private final RegisterName result;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName result) {
        super(label, OP_CODE);
        this.result = result;
    }

    @Override
    public int execute(Machine m) {
        System.out.println(m.getRegisters().get(result));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OutInstruction other) {
            return Objects.equals(this.result, other.result)
                    && this.OP_CODE == other.OP_CODE;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, OP_CODE);
    }
}