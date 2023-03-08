package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Registers class represents registers that can be used in the program.
 * Registers are represented as a HashMap where each unique register has a name
 * and a value.
 *
 * @author BBK, arozen01
 */
public final class Registers {
    private final Map<Register, Integer> registers = new HashMap<>();

    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI
    }

    /**
     * Calls clear method which creates all registers and clears them by setting them to 0.
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Creates all registers and clears them by setting them to 0.
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     * Compares two instances.
     *
     * @param o object to be compared with
     * @return true if o is equal to an instance of Registers, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    /**
     * String representation of the registers.
     * <p>
     * Form: [register = value, register = value, ... , register = value]
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
