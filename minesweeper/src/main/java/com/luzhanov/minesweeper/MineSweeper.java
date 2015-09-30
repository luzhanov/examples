package com.luzhanov.minesweeper;

/**
 * A mine-field of N x M squares is represented by N lines of exactly M characters each.
 * The character '*' represents a mine * and the character '.' represents no-mine.
 * Lines are separated by '\n'
 * <p/>
 *  * Example mine-field string (as input to setMineField()): "*...\n..*.\n...."
 * (a 3 x 4 mine-field of 12 squares, 2 of which are mines)
 * <p/>
 *  * getHintField() produces a hint-field of identical dimensions as the mineFiled() where each
 * square is a * for a mine or the number of adjacent mine-squares if the square does not contain a mine.
 *  *
 * Example hint-field string (as returned by getHintField(): "*211\n12*1\n0111" (for the above input)
 * <p/>
 *  
 */
public interface MineSweeper {

    /**
     * Initialises the mine-field
     *  
     * @param mineField string containing the mines (see interface javadoc for representation)
     * @throws IllegalArgumentException if mineField is not properly formatted
     */
    void setMineField(String mineField) throws IllegalArgumentException;

    /**
     *  Produces a hint-field for the current mine-filed (see interface javadoc for details)
     *  
     *  @return a string representation of the hint-field
     *  @throws IllegalStateException if the mine-field has not been initialised (i.e. setMineField() has not been called)
     */
    String getHintField() throws IllegalStateException;

}
