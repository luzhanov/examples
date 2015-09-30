package com.luzhanov.minesweeper;

import java.util.regex.Pattern;

public class MineSweeperImpl implements MineSweeper {

    public static final Character CHAR_MINE = '*';
    public static final Character CHAR_EMPTY = '.';
    public static final String LINE_SEPARATOR = "\n";

    private Pattern REGEX_ALLOWED_CHARS = Pattern.compile("[*|.|\\n]+");

    private int[][] mineField = null;

    public void setMineField(String mineField) throws IllegalArgumentException {
        //todo: validate for squareness

        this.mineField = parseMineFieldFromString(mineField);
    }

    public String getHintField() throws IllegalStateException {
        if (mineField == null) {
            throw new IllegalStateException("Mine field is not set");
        }

        //todo: implement
        return null;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -1;
    }

    private int[][] parseMineFieldFromString(String mineField) {
        if (mineField == null || mineField.isEmpty()) {
            throw new IllegalArgumentException("Mine field is null or empty");
        }
        if (!REGEX_ALLOWED_CHARS.matcher(mineField).matches()) {
            throw new IllegalArgumentException("Mine field should contains only [*] [.] or new line");
        }
        String[] rows = mineField.split(LINE_SEPARATOR);
        if (!isRowsTheSameLength(rows)) {
            throw new IllegalArgumentException("Mine field lines have different length! Should be the same length");
        }



        //todo: implement
        return null;
    }

    private boolean isRowsTheSameLength(String[] rows) {
        int firstRowLength = rows[0].length();
        for (String row : rows) {
            if (row.length() != firstRowLength) {
                return false;
            }
        }

        return true;
    }

}
