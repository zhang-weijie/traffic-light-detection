package org.htw.prog2.ampel;

public class FormatException extends Exception {
    private String line;
    private String error;
    public FormatException(String line, String error) {
        super();
        this.line = line;
        this.error = error;
    }

    public String getLine() {
        return line;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        String end = "";
        if (!getError().endsWith("."))
            end = ".";
        return "Wrong format in line '" + getLine() + "': " + getError() + end;
    }
}
