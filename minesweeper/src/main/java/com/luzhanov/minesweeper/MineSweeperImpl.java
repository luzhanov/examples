package com.luzhanov.minesweeper;

import java.util.regex.Pattern;

public class MineSweeperImpl implements MineSweeper {

    public static final Character CHAR_MINE = '*';
    public static final Character CHAR_EMPTY = '.';
    public static final String LINE_SEPARATOR = "\n";

    private Pattern REGEX_ALLOWED_CHARS = Pattern.compile("[*|.|\\n]+");

    private int[][] mineField = null;

    public void setMineField(String mineField) throws IllegalArgumentException {
        this.mineField = parseMineFieldFromString(mineField);
    }

    public String getHintField() throws IllegalStateException {
        if (mineField == null) {
            throw new IllegalStateException("Mine field is not set");
        }

        char[][] hintArray = new char[mineField.length][mineField[0].length];
        int[][] hintCntArray = getCountsCalculation(mineField);

        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[i].length; j++) {
                if (mineField[i][j] == 1) {
                    hintArray[i][j] = CHAR_MINE;
                } else {
                    hintArray[i][j] = Integer.toString(hintCntArray[i+1][j+1]).charAt(0);
                }
            }
        }

        return generateHintStringFromHintArray(hintArray);
    }

    /**
     * Returns extended array (+1 line from each side)
     * @param mineField field with 1 as a mine
     * @return extended array (+1 line from each side)
     */
    private int[][] getCountsCalculation(int[][] mineField) {
        int[][] hintCntArray = new int[mineField.length + 2][mineField[0].length + 2];

        for (int i = 1; i < mineField.length + 1; i++) {
            for (int j = 1; j < mineField[i-1].length + 1; j++) {
                if (mineField[i-1][j-1] == 1) {
                    hintCntArray[i-1][j]++;
                    hintCntArray[i][j-1]++;
                    hintCntArray[i-1][j-1]++;
                    hintCntArray[i+1][j]++;
                    hintCntArray[i][j+1]++;
                    hintCntArray[i+1][j+1]++;
                    hintCntArray[i+1][j-1]++;
                    hintCntArray[i-1][j+1]++;
                }
            }
        }

        return hintCntArray;
    }

    private String generateHintStringFromHintArray(char[][] hintArray ) {
        StringBuilder out = new StringBuilder();
        for (char[] chars : hintArray) {
            if (out.length() != 0) {
                out.append(LINE_SEPARATOR);
            }
            out.append(chars);
        }
        return out.toString();
    }

    public int getWidth() {
        if (mineField != null && mineField.length > 0) {
            return mineField[0].length;
        }

        return 0;
    }

    public int getHeight() {
        if (mineField != null) {
            return mineField.length;
        }

        return 0;
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

        int length = rows[0].length();
        int height = rows.length;

        int[][] result = new int[height][length];

        for (int i = 0; i < rows.length; i++) {
            result[i] = parseFieldLine(rows[i]);
        }

        return result;
    }

    private int[] parseFieldLine(String fieldLine) {
        char[] chars = fieldLine.toCharArray();
        int[] result = new int[chars.length];

        for (int i=0; i < chars.length; i++) {
            char c = chars[i];

            if (CHAR_MINE.equals(c)) {
                result[i] = 1;
            } else if (CHAR_EMPTY.equals(c)) {
                result[i] = 0;
            } else {
                throw new IllegalArgumentException("Unexpected char: " + c);
            }
        }

        return result;
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
