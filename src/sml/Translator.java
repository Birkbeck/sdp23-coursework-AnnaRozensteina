package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException, Exception {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
        catch (IllegalArgumentException ex){
            System.out.println("Program could not be read.\n" + ex.getMessage());
            throw new IOException();
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) throws Exception{
        // TODO: Deal with specific exceptions for when class or constructor is not found
        // TODO: create a factory class that handles building the instruction instance

        if (line.isEmpty())
            return null;

        // remove opcode from line and find the corresponding class
        String opcode = scan();
        String instructionClassName = "sml.instruction." + opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
        Class<?> InstructionClass = Class.forName(instructionClassName);

        // split the rest of the line as parameters for instruction constructor
        int countParams = line.split("\\s+").length;
        Class[] paramType = new Class[countParams];
        Object[] params = new Object[countParams];

        // add label as all instructions will start with the label
        paramType[0] = String.class;
        params[0] = label;

        // add types and values of all other constructor parameters
        // TODO: find a better way to check type
        for(int i = 1; i < countParams; i++){
            String word = scan();
            try{
                params[i] = Register.valueOf(word);
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
        Constructor<?> InstructionConstructor = InstructionClass.getDeclaredConstructor(paramType);

        // return new instruction with the correct arguments
        return (Instruction) InstructionConstructor.newInstance(params);

            // add code for all other types of instructions

            // Then, replace the switch by using the Reflection API

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs)

    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}