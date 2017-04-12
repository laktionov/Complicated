package Jama;

import java.util.function.DoubleUnaryOperator;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Сергей on 06.04.2017.
 */
public class Interpol {
    public static void produce(DoubleUnaryOperator f, double a, double b, ArrayList X, ArrayList Y, int m) {
        Scanner in = new Scanner(System.in);
        double h = (b - a)/m;
        System.out.print("Степень интерполяционного многочлена, меньшая либо равная "+ m  +", n = ");
        int n  = in.nextInt();
        while (n > m) {
            System.out.print("Степень интерполяционного многочлена, меньшая либо равная "+ m  +", n = ");
            n = in.nextInt();
        }
        boolean otvet = true;
        System.out.print("Точка, расположенная в промежутках [" + a +"," + (a+h) + "], [" + (b - h) + "," + b + "] или [" + (a + floor((n+1)/2)*h) + "," + (b - floor((n+1)/2)*h) + "], в которой необходимо вычислить значение функции, x = ");
        double x = in.nextDouble();

        ArrayList<ArrayList<Double>> D = new ArrayList<>();
        D.add(X);
        D.add(Y);
        for (int k = 2; k <= n+1; k++){
            Finitedifferences(D, m, k);
        }
        int k = 0;
        for (int i = 0; i <= m; i++) {
            if (i >= m-n+1) {
                k++;
            }
            System.out.print(i +" ");
            for (int j = 0; j <= n-k+1; j++){
                System.out.printf("%.12f", + (D.get(j)).get(i));
                System.out.print("    ");
            }
            System.out.println();
        }
        if (((x-a) >= 0) && ((x-a-h) <= 0)) {
            Begin(D, x, n, h, f);
        }
        if ((x-b+h >=0) && (b - x >= 0)) {
            End(D, x, n, m, h, f);
        }
        if((x - a - floor((n+1)/2)*h >=0) && (b - floor((n+1)/2)*h - x > 0)) {
            Middle(D, x, n , m , h, f);
        }

        System.out.println("Повторим?");
        otvet = in.nextBoolean();
        if (otvet == true) {
            produce(f, a, b, X, Y, m);
        }
    }

    public static void Finitedifferences(ArrayList<ArrayList<Double>> D, int m, int k) {
        ArrayList<Double> A = new ArrayList<>();
        for (int i = 0; i <= m-k+1; i++) {
            A.add((D.get(k-1)).get(i+1) - (D.get(k-1)).get(i));
        }
        D.add(A);
    }

    public static void Begin(ArrayList<ArrayList<Double>> D, double x, int n, double h, DoubleUnaryOperator f) {
        double t = (x - (D.get(0).get(0)))/h;
        double Pn = (D.get(1)).get(0);
        double s = t;
        for (int k = 0; k <= n-1; k++) {
            Pn += s*(D.get(k+2).get(0));
            s *= (t-k-1)/(k+2);
        }
        System.out.println("P_n (x) = "+Pn +" - значение интерполяционного многочлена в искомой точке х");
        System.out.println("ef_n (x) = "+abs(Pn - f.applyAsDouble(x)) +" - фактическая погрешность");
    }

    public static void End(ArrayList<ArrayList<Double>> D, double x, int n, int m, double h, DoubleUnaryOperator f) {
        double t = (x - (D.get(0).get(m)))/h;
        double Pn = (D.get(1)).get(m);
        double s = t;
        for (int k = 0; k <= n-1; k++) {
            Pn+= s*D.get(k+2).get(m-k-1);
            s *= (t+k+1)/(k+2);
        }
        System.out.println("P_n (x) = "+Pn +" - значение интерполяционного многочлена в искомой точке х");
        System.out.println("ef_n (x) = "+abs(Pn - f.applyAsDouble(x)) +" - фактическая погрешность");
    }

    public static void Middle(ArrayList<ArrayList<Double>> D, double x, int n, int m, double h, DoubleUnaryOperator f) {
        ArrayList<Double> Z = new ArrayList<>();
        int l = 0;
        for(int i = 0; i <= m; i++) {
            if ((x - D.get(0).get(i) > 0) && (D.get(0).get(i+1) - x > 0)) {
                l = i;
                break;
            }
        }
        double Pn = (D.get(1)).get(l);
        double t = (x-D.get(0).get(l))/h;
        double s = t;
        for (int k = 0; k <= n-1; k++) {
            Pn += s*D.get(k+2).get((int) (l - floor((k+1)/2)));
            s *= (t + pow(-1,k+1)*floor((k+2)/2))/(k+2);
        }
        System.out.println("P_n (x) = "+Pn +" - значение интерполяционного многочлена в искомой точке х");
        System.out.println("ef_n (x) = "+abs(Pn - f.applyAsDouble(x)) +" - фактическая погрешность");
    }
}

