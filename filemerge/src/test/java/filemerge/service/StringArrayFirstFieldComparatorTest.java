package filemerge.service;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringArrayFirstFieldComparatorTest {

    StringArrayFirstFieldComparator comparator = new StringArrayFirstFieldComparator();

    @Test
    public void nonNullIsBiggerThanNullOrEmpty() {
        String[] array = new String[]{"aa", "bb"};
        String[] emptyArray = new String[]{};

        assertThat(comparator.compare(array, null)).isEqualTo(1);
        assertThat(comparator.compare(array, emptyArray)).isEqualTo(1);
    }

    @Test
    public void nullOrEmptyIsSmallerThanNotNull() {
        String[] array = new String[]{"aa", "bb"};
        String[] emptyArray = new String[]{};

        assertThat(comparator.compare(null, array)).isEqualTo(-1);
        assertThat(comparator.compare(emptyArray, array)).isEqualTo(-1);
    }

    @Test
    public void nonNullFirstFieldIsBiggerThanNull() {
        String[] array1 = new String[]{"aa", "bb"};
        String[] array2 = new String[]{null, "cc"};

        assertThat(comparator.compare(array1, array2)).isEqualTo(1);
    }

    @Test
    public void nullFirstFieldIsSmallerThanNotNull() {
        String[] array1 = new String[]{"aa", "bb"};
        String[] array2 = new String[]{null, "cc"};

        assertThat(comparator.compare(array2, array1)).isEqualTo(-1);
    }

    @Test
    public void nullsAndEmptieasAreEqual() {
        String[] emptyArray = new String[]{};

        assertThat(comparator.compare(null, null)).isEqualTo(0);
        assertThat(comparator.compare(emptyArray, emptyArray)).isEqualTo(0);
    }

    @Test
    public void comparesArraysByFirstFields() {
        String[] array1 = new String[]{"bb", "bb"};
        String[] array2 = new String[]{"dd", "aa"};

        assertThat(comparator.compare(array1, array2)).isLessThanOrEqualTo(-1);
        assertThat(comparator.compare(array2, array1)).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void equalIfFirstFieldsAreEquals() {
        String[] array1 = new String[]{"dd", "bb"};
        String[] array2 = new String[]{"dd", "aa"};

        assertThat(comparator.compare(array1, array2)).isLessThanOrEqualTo(0);
        assertThat(comparator.compare(array2, array1)).isGreaterThanOrEqualTo(0);
    }

}