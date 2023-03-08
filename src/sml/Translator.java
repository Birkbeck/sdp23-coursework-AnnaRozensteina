package sml;

import sml.instruction.InstructionFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads and translates an SML program.
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author BBK, arozen01
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    /**
     * Reads and translates the SML program in the file into labels and program.
     *
     * @param labels list of all labels and their addresses
     * @param program list of instructions in the program
     * @throws IOException when program cannot be read
     * @throws Exception when next Instruction can't be created
     */

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
            System.out.println("No errors were detected when reading the program.");
    }

    /**
     * Translates the current line into an instruction with the given label.
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     *
     * @param label the instruction label
     * @return the new instruction
     * @throws NoSuchMethodException if unknown opcode is used
     * @throws ClassNotFoundException if instruction use is incorrect
     */
    private Instruction getInstruction(String label) throws Exception {
        if (line.isEmpty())
            return null;

        String instructionStr = line;
        String opcode = scan();
        String[] values = line.split("\\s+");

        try {
            InstructionFactory factory = new InstructionFactory();
            return factory.newInstanceOf(opcode,label, values);
        }
        catch (ClassNotFoundException e){
            throw new ClassNotFoundException("An unknown opcode " + opcode + " was used.");
        }
        catch (NoSuchMethodException e){
            throw new NoSuchMethodException("Incorrect use of " + opcode + " instruction: " + instructionStr);
        }
        catch (Exception e){
            throw new Exception("Couldn't execute instruction" + instructionStr);
        }

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs)
    }

    /**
     * Scans the line and return the label. If there is no label, the scanning is
     * undone and null is returned.
     *
     * @return label if it exists, null otherwise
     */
    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     * @return next word in line or empty string
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