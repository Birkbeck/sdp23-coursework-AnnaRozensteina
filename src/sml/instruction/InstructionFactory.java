package sml.instruction;
import sml.Instruction;
import sml.RegisterName;
import sml.Registers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Instruction Factory class handles the creation of instructions.
 *
 */
public class InstructionFactory {

    private Object[] params;

    /**
     * Method finds the corresponding instruction class name for the passed in opcode.
     *
     * @param opcode instruction opcode
     * @return class based on the passed opcode
     * @throws ClassNotFoundException if there isn't a class that would match the opcode
     */
    private Class<?> getInstructionClass(String opcode) throws ClassNotFoundException {
        String instructionClassName = "sml.instruction." + opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
        return  Class.forName(instructionClassName);
    }

    /**
     * Finds the matching constructor based on the arguments passed in.
     *
     * @param instruction class of the instruction
     * @param label optional label of the instruction
     * @param str parameters of the instruction
     * @return relevant constructor based on the arguments passed in
     * @throws NoSuchMethodException if the class doesn't have a matching constructor
     */
    private Constructor<?> getInstructionConstructor(Class<?> instruction, String label, String ... str) throws NoSuchMethodException {

        int countParams = str.length;
        Class<?>[] paramType = new Class[countParams];
        this.params = new Object[countParams];

        paramType[0] = String.class;
        params[0] = label;

        // finding parameter types
        for (int i = 1; i < countParams; i++) {
            String word = str[i];
            try{
                params[i] = Registers.Register.valueOf(word);
                paramType[i] = RegisterName.class;

            } catch (IllegalArgumentException e) {
                try{
                    params[i] = Integer.parseInt(word);
                    paramType[i] = int.class;
                } catch (NumberFormatException ex) {
                    params[i] = word;
                    paramType[i] = String.class;
                }
            }
        }

        // find the correct constructor based on argument types
        return instruction.getDeclaredConstructor(paramType);
    }

    /**
     * Creates a new instance of the corresponding Instruction class and returns it.
     *
     * @param opcode opcode of the instruction
     * @param label optional label of the instruction
     * @param str parameters of the instruction
     * @return new instance of the corresponding instruction class
     *
     * @throws ClassNotFoundException if there isn't a class that would match the opcode
     * @throws NoSuchMethodException if the class doesn't have a matching constructor
     * @throws InvocationTargetException when new instance cannot be created
     * @throws InstantiationException when new instance cannot be created
     * @throws IllegalAccessException when new instance cannot be created
     */
    public Instruction newInstanceOf(String opcode, String label, String ... str) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        return (Instruction) getInstructionConstructor(getInstructionClass(opcode),label, str).newInstance(params);

    }


}
