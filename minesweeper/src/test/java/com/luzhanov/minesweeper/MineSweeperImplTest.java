package com.luzhanov.minesweeper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MineSweeperImplTest {

    private MineSweeperImpl mineSweeper = new MineSweeperImpl();

    public static final String FIELD1 = "*...\n" +
                                        "..*.\n" +
                                        "....";

    public static final String HINT1 = "*211\n" +
                                       "12*1\n" +
                                       "0111";

    public static final String FIELD_ONE_LINE = "*.*...*.";

    public static final String HINT_ONE_LINE = "*2*101*1";

    public static final String FIELD_ONE_CHAR = ".";

    public static final String HINT_ONE_CHAR = "0";

    public static final String FIELD_INCORRECT_CHARS = "*...\n" +
                                                       "a.*.\n" +
                                                       "....";

    public static final String FIELD_INCORRECT_SIZE = "*...\n" +
                                                   "..*..\n" +
                                                   "....";

    @Test
    public void returnsZeroSizeWithoutData() throws Exception {
        assertThat(mineSweeper.getHeight()).isEqualTo(0);
        assertThat(mineSweeper.getWidth()).isEqualTo(0);
    }

    @Test
    public void parsesFieldWithCorrectSize() throws Exception {
        mineSweeper.setMineField(FIELD1);

        assertThat(mineSweeper.getHeight()).isEqualTo(3);
        assertThat(mineSweeper.getWidth()).isEqualTo(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnEmptyField() throws Exception {
        mineSweeper.setMineField("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnNullField() throws Exception {
        mineSweeper.setMineField(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnIncorrectCharsField() throws Exception {
        mineSweeper.setMineField(FIELD_INCORRECT_CHARS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnIncorrectLengthField() throws Exception {
        mineSweeper.setMineField(FIELD_INCORRECT_SIZE);
    }

    @Test
    public void calculatesCorrectHint() throws Exception {
        mineSweeper.setMineField(FIELD1);
        assertThat(mineSweeper.getHintField()).isEqualTo(HINT1);
    }

    @Test
    public void calculatesCorrectHintOneLine() throws Exception {
        mineSweeper.setMineField(FIELD_ONE_LINE);
        assertThat(mineSweeper.getHintField()).isEqualTo(HINT_ONE_LINE);
    }

    @Test
    public void calculatesCorrectHintOneChar() throws Exception {
        mineSweeper.setMineField(FIELD_ONE_CHAR);
        assertThat(mineSweeper.getHintField()).isEqualTo(HINT_ONE_CHAR);
    }

    @Test(expected = IllegalStateException.class)
    public void failsOnHintCalculationIfFieldIsNotSet() throws Exception {
        mineSweeper.getHintField();
    }
}
