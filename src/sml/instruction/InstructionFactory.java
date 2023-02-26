package sml.instruction;
import sml.Instruction;
import sml.RegisterName;
import sml.Registers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InstructionFactory {

    private Object[] params;

    private Class<?> getInstructionClass(String opcode) throws ClassNotFoundException {
        String instructionClassName = "sml.instruction." + opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
        return  Class.forName(instructionClassName);
    }

    private Constructor<?> getInstructionConstructor(Class<?> instruction, String label, String ... str) throws NoSuchMethodException {
        int countParams = str.length;
        Class<?>[] paramType = new Class[countParams];
        this.params = new Object[countParams];

        paramType[0] = String.class;
        params[0] = label;

        // TODO: find a better way to check type
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

    public Instruction newInstanceOf(String opcode, String label, String ... str) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        return (Instruction) getInstructionConstructor(getInstructionClass(opcode),label, str).newInstance(params);

    }


}
