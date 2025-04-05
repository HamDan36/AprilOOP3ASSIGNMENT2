package appDomain;

import implementations.MyStack;
import implementations.MyQueue;
import utilities.StackADT;
import utilities.QueueADT;
import utilities.Iterator;
import exceptions.EmptyQueueException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * XML Parser implementation using custom Stack and Queue ADTs. It identifies the opening and closing tags of a XML document and checks for mismatches to identify errors.
 * The errors are identified and labeled with the line number they are on in a report.
 */
public class XMLParser {
    private StackADT<String> stack;
    private QueueADT<String> errorQ;
    private QueueADT<String> extrasQ;

    /**
     * No argument constructor for XMLParser class
     * Preconditions: none
     * Postconditions: a new XMLParser object is created
     */
    public XMLParser() {
        stack = new MyStack<>();
        errorQ = new MyQueue<>();
        extrasQ = new MyQueue<>();
    }

    /**
     * Parses through the XML file given in the parameter
     * Preconditions: a valid XMLParser object must exist
     * Postconditions: the file from the filePath is parsed for errors and a report is generated
     * 
     * @param filePath Path to the XML file
     */
    public void parseFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                processLine(line, lineNumber);
            }
            finalizeParsing();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Handles a single line in the XML file and processes the opening and closing tags
     * Preconditions: a valid XMLParser object must exist with a valid BufferedReader file object
     * Postconditions: a line in the XML file is processed and starting and ending tags are placed in the correct Queues, errors are created by matching the two queues and placed in the error queue.
     *  
     * @param line Current line from the XML file
     * @param lineNumber Current line number being processed
     */
    private void processLine(String line, int lineNumber) {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith("<?") || trimmedLine.isEmpty()) return; // Ignore processing instructions

        if (trimmedLine.endsWith("/>")) {
            // Self-closing tag, ignore
            return;
        } else if (trimmedLine.startsWith("<") && !trimmedLine.startsWith("</")) {
            // Start tag
            String tag = extractTag(trimmedLine);
            stack.push(tag + "@" + lineNumber);
        } else if (trimmedLine.startsWith("</")) {
            // End tag
            String tag = extractTag(trimmedLine.substring(1)); // Skip "</"
            String top = stack.isEmpty() ? null : stack.peek();
            if (top != null && top.startsWith(tag + "@")) {
                stack.pop();  // Matches top of stack, so pop it
            } else if (!errorQ.isEmpty()) {
                try {
                    String peekValue = errorQ.peek();
                    if (peekValue.startsWith(tag + "@")) {
                        errorQ.dequeue(); // Matches head of errorQ, dequeue and ignore
                    } else {
                        errorQ.enqueue(tag + "@" + lineNumber); // Else, add to errorQ
                    }
                } catch (EmptyQueueException e) {
                    // If peek fails, treat as if queue is empty and enqueue the tag
                    errorQ.enqueue(tag + "@" + lineNumber);
                }
            } else if (stack.isEmpty()) {
                errorQ.enqueue(tag + "@" + lineNumber); // Stack is empty, add to errorQ
            } else {
                int matchIndex = searchStackForMatch(tag); // Search stack for match
                if (matchIndex != -1) {
                    while (stack.size() > matchIndex) {
                        errorQ.enqueue(stack.pop()); // Pop and enqueue mismatched tags to errorQ
                    }
                } else {
                    extrasQ.enqueue(tag + "@" + lineNumber); // Add to extrasQ if no match
                }
            }
        }
    }
    
    /**
     * Extracts the tags in the line from the XML file
     * Preconditions: A line from XML file starting with a XML tag must be the parameter
     * Postconditions: Returns the tag name from the line
     * 
     * @param line Line from the XML file, must start with a XML tag
     * @return Returns the tag name from the line processed
     */
    private String extractTag(String line) {
        // Remove angle brackets and slashes for both start and end tags
        line = line.replaceAll("[<>]", "");
        // If it's an end tag, remove the "/" at the beginning
        if (line.startsWith("/")) {
            line = line.substring(1);
        }
        // Extract only the tag name without any attributes
        int endIndex = line.indexOf(" ");
        if (endIndex == -1) endIndex = line.length(); // Handle case where no space is present
        return line.substring(0, endIndex);
    }

    /** 
     * Iterates through the Stack object to find a matching tag to the parameter, the index of first matching tag is returned
     * Preconditions: A valid Stack object must exist
     * Postconditions: The index of the first matching tag is returned, or -1 if no match was found
     * 
     * @param tag Tag to be searched for in the Stack object
     * @return Returns the index of the first matching tag is returned, or -1 otherwise
     */
    private int searchStackForMatch(String tag) {
        Iterator<String> iter = stack.iterator();
        int index = 1;
        while (iter.hasNext()) {
            String item = iter.next();
            if (item.startsWith(tag + "@")) return stack.size() - index; // Match found, return index
            index++;
        }
        return -1; // No match found
    }

    /**
     * Finalizes parsing after the entire XML file is processed
     * Preconditions: All the lines in the XML file has been processed
     * Postconditions: Any tags remaining in the queue are flagged as errors 
     */
    private void finalizeParsing() {
        // At the end of the file, pop everything from the stack and add to errorQ
        while (!stack.isEmpty()) {
            errorQ.enqueue(stack.pop());
        }
        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
            System.out.println("XML document is constructed correctly.");
        } else {
            processQueues();
        }
    }

    /**
     * Processes Queues to generate an error report
     * Preconditions: Non-empty valid error and extra Queues must exist
     * Postconditions: The extra and error queues are emptied and an error report is generated based on contents of the Queues
     */
    private void processQueues() {
        // Process and report errors from both queues
        while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
            if (errorQ.isEmpty() != extrasQ.isEmpty()) {
                reportErrors();
                break;
            }
            try {
                String errorTop = errorQ.peek();
                String extrasTop = extrasQ.peek();
                if (!errorTop.equals(extrasTop)) {
                    System.out.println("Error at line: " + errorTop.split("@")[1] + " " + errorTop.split("@")[0] + " is not constructed correctly.");
                    errorQ.dequeue();
                } else {
                    errorQ.dequeue();
                    extrasQ.dequeue();
                }
            } catch (EmptyQueueException e) {
                reportErrors();
                break;
            }
        }
    }

    /**
     * Reports errors from both extra and error Queue objects and prints error statements
     * Preconditions: Valid, non-empty error and extra queues must exist
     * Postconditions: Both error and extra Queues are emptied. Errors are processed and error statements are generated
     */
    private void reportErrors() {
        // Report errors from both queues
        Iterator<String> errorIter = errorQ.iterator();
        Iterator<String> extrasIter = extrasQ.iterator();
        while (errorIter.hasNext()) {
            String error = errorIter.next();
            System.out.println("Error at line: " + error.split("@")[1] + " " + error.split("@")[0] + " is not constructed correctly.");
        }
        while (extrasIter.hasNext()) {
            String extra = extrasIter.next();
            System.out.println("Error at line: " + extra.split("@")[1] + " " + extra.split("@")[0] + " is not constructed correctly.");
        }
    }

    /**
     * Main method for the XMLParser class
     * Preconditions: None
     * Postconditions: The XMLParser parses through the XML file and if errors are found, an error report is printed
     * 
     * @param args cmd line arguments containing the path to the XML file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar Parser.jar <xmlFile>");
            return;
        }
        XMLParser parser = new XMLParser();
        parser.parseFile(args[0]);
    }
}
