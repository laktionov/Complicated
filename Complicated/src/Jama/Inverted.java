package Jama;

import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;
import static java.lang.Math.*;
import java.util.Scanner;
import Jama.*;
/**
 * Created by Сергей on 06.04.2017.
 */
public class Inverted extends Jama {
    public static void search(DoubleUnaryOperator f, double a, double b, ArrayList X, ArrayList Y, int m) {
        Scanner in = new Scanner(System.in);
        System.out.print("Значение функции, для которого ищем значение аргумента, F = ");
        double F = in.nextDouble();
        System.out.print("Степень интерполяционного многочлена, меньшая либо равная "+ m  +", n = ");
        int n = in.nextInt();
        while (n > m) {
            System.out.print("Степень интерполяционного многочлена, меньшая либо равная "+ m  +", n = ");
            n = in.nextInt();
        }
        boolean otvet = true;
        System.out.println("Функция монотонна?");
        otvet = in.nextBoolean();
        if (otvet == true) {
            Odin(X,Y, m, n, F, f);
        } else
        {
            Dva(X, Y, m, n, F, f);
        }
    }

    public static void Odin(ArrayList X, ArrayList Y, int m, int n, double F, DoubleUnaryOperator f) {
        ArrayList<Double> Z = new ArrayList<Double>();
        for (int i = 0; i <= m; i++) {
            Z.add(abs((double) Y.get(i) - F));
        }
        Quick.sort(Z, 0, m, Y, X);
        System.out.println();
        double x = Lagrange.poly(Y, X, f, F, n);
        System.out.println("Искомое значение аргумента x = " + x);
        double r = abs(f.applyAsDouble(x) - F);
        System.out.println("Модуль невязки r=" + r);
    }

    public static void Dva(ArrayList X, ArrayList Y, int m, int n, double F, DoubleUnaryOperator f) {
        int l = 0;
        for(int i = 0; i <= m; i++) {
            if ((F - (double) Y.get(i) > 0) && ((double) Y.get(i + 1) - F > 0)) {
                l = i;
                break;
            }
        }
        double c = ((double)X.get(l) + (double)X.get(l+1))/2;
        ArrayList<Double> Z = new ArrayList<Double>();
        for (int i = 0; i <= m; i++) {
            Z.add(abs((double) X.get(i) - c));
        }
        Quick.sort(Z, 0, m, X, Y);
        Matrix A = new Matrix(n+1, n+1);
        Matrix B = new Matrix(n+1,1);
        for (int i = 0; i <= n; i++) {
            int k = 0;
            for (int j = 0 ; j <= n; j++) {
                A.set(i,j, pow((double)X.get(i), k));
                k++;
            }
            B.set(i,0, (double)Y.get(i));
        }
        Matrix C = A.inverse();
        Matrix K = new Matrix(n+1, 1);
        K = C.times(B);
        for (int i = 0; i <= n; i++) {
            System.out.println(K.get(i,0));
        }

        for (int i = 0; i <=n; i++) {
            double y =0;
            for (int j = 0; j <= n; j++) {
                y += K.get(j,0)*pow((double)X.get(i), j);
            }
            System.out.println(y);
        }

        double x = bisect((double)X.get(0), (double)X.get(1), K, n, F);
        System.out.println("Искомое значение аргумента x = " + x);
        double r = abs(f.applyAsDouble(x) - F);
        System.out.println("Модуль невязки r=" + r);    }

    public static double bisect(double a, double b, Matrix K, int n, double F) {
        while (Math.abs(b - a) > 2 * 0.00000001) {
            double c = (a + b) / 2;
            double y = function(K, a, n, F);
            double z = function(K, c, n, F);
            if (y * z <= 0) {
                b = c;
            } else {
                a = c;
            }
        }
        double x = (a + b) / 2;
        return x;
    }

    public static double function(Matrix K, double x, int n, double F) {
        int k = 0;
        double function = 0;
        while (k <= n) {
            function += K.get(k, 0)* pow(x, k);
            k++;
        }
        return (function - F);
    }
}
