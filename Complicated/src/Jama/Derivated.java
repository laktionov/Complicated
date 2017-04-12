package Jama;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.*;
import java.util.function.DoubleUnaryOperator;
/**
 * Created by Сергей on 06.04.2017.
 */
public class Derivated {
    public static void compute(ArrayList<Double> X, ArrayList<Double> Y, double h, int m) {
        Scanner in = new Scanner(System.in);
        DoubleUnaryOperator ff = x -> (4*cos(x) - 1)/2;
        DoubleUnaryOperator fff = x -> -2*sin(x);
        System.out.print(" x_i");
        System.out.print("         ");
        System.out.print("f(x_i)");
        System.out.print("        ");
        System.out.print("f'(x_i)_ЧД");
        System.out.print("      ");
        System.out.print("|f'(x_i)_Т - f'(x_i)_ЧД|");
        System.out.print("    ");
        System.out.print("f''(x_i)_ЧД");
        System.out.print("      ");
        System.out.print("|f''(x_i)_Т - f''(x_i)_ЧД|");
        System.out.print("     ");
        System.out.println();
        for (int k = 0; k <= m; k++) {
            if (k == 0) {
                double df = deriv2(Y.get(k), Y.get(k+1), Y.get(k+2), h);
                System.out.printf("%.5f", + X.get(k));
                System.out.print("        ");
                System.out.printf("%.5f", + Y.get(k));
                System.out.print("      ");
                System.out.printf("%.5f", + df);
                System.out.print("        ");
                double s = ff.applyAsDouble(X.get(k));
                System.out.print(abs(ff.applyAsDouble(X.get(k)) - df));
                System.out.print("      ");
                System.out.println();
            }
            if((k>0) && (k<m)) {
                double df = deriv1(Y.get(k+1), Y.get(k-1), h);
                double ddf = deriv4(Y.get(k+1), Y.get(k), Y.get(k-1), h);
                System.out.printf("%.5f", + X.get(k));
                System.out.print("        ");
                System.out.printf("%.5f", + Y.get(k));
                System.out.print("      ");
                System.out.printf("%.5f", + df);
                System.out.print("        ");
                System.out.print(abs(ff.applyAsDouble(X.get(k)) - df));
                System.out.print("        ");
                System.out.printf("%.5f", + ddf);
                System.out.print("        ");
                System.out.print(abs(fff.applyAsDouble(X.get(k)) - ddf));
                System.out.print("");
                System.out.println();

            }

            if (k == m) {
                double df = deriv3(Y.get(k), Y.get(k-1), Y.get(k-2), h);
                System.out.printf("%.5f", + X.get(k));
                System.out.print("        ");
                System.out.printf("%.5f", + Y.get(k));
                System.out.print("      ");
                System.out.printf("%.5f", + df);
                System.out.print("        ");
                System.out.print(abs(ff.applyAsDouble(X.get(k)) - df));
                System.out.print("      ");
                System.out.println();
            }
        }
    }
    public static double deriv1(double f1, double f2, double h) {
        double deriv1 = (f1 - f2)/(2*h);
        return deriv1;
    }
    public static double deriv2(double f1, double f2, double f3, double h) {
        double deriv2 = (-3*f1 + 4*f2 - f3) / (2 * h);
        return deriv2;
    }

    public static double deriv3(double f1, double f2, double f3, double h) {
        double deriv3 = (3*f1 - 4*f2 + f3) / (2 * h);
        return deriv3;
    }

    public static double deriv4(double f1, double f2, double f3, double h) {
        double deriv4 = (f1 - 2*f2 + f3) / (pow(h,2));
        return deriv4;
    }
}
