package Jama;

import java.util.ArrayList;
public class Quick {
        public static void sort(ArrayList<Double> array1, int l, int r, ArrayList<Double> array2, ArrayList<Double> array3 ) {
            int i = l;
            int j = r;
            double M = array1.get(l - (l - r) / 2);
            while (i <= j) {
                while (array1.get(i) < M) {
                    i++;
                }
                while (array1.get(j) > M) {
                    j--;
                }
                if (i <= j) {
                    double tmp1 = array1.get(i);
                    array1.set(i, array1.get(j));
                    array1.set(j ,tmp1);
                    double tmp2 = array2.get(i);
                    array2.set(i, array2.get(j));
                    array2.set(j ,tmp2);
                    double tmp3 = array3.get(i);
                    array3.set(i, array3.get(j));
                    array3.set(j ,tmp3);
                    i++;
                    j--;
                }
            }
            if (l < j) {
                sort(array1, l, j, array2, array3);
            }
            if (i < r) {
                sort(array1, i, r, array2, array3);
            }
        }
    }

