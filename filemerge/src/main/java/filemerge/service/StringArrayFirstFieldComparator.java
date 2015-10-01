package filemerge.service;

import java.util.Comparator;

class StringArrayFirstFieldComparator implements Comparator<String[]> {

    @Override
    public int compare(String[] array1, String[] array2) {
        if (isEmptyArray(array1) && isEmptyArray(array2)) {
            return 0;
        } else if (isEmptyArray(array1)) {
            return -1;
        } else if (isEmptyArray(array2)) {
            return 1;
        } else  {
            String field1 = array1[0];
            String field2 = array2[0];

            if (field1 == null && field2 == null) {
                return 0;
            } else if (field1 == null) {
                return -1;
            } else if (field2 == null) {
                return 1;
            } else {
                return field1.compareTo(field2);
            }
        }
    }

    private boolean isEmptyArray(String[] array) {
        return array == null || array.length == 0;
    }
}
