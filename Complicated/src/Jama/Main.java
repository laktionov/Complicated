package Jama;

import Jama.Derivated;
import Jama.Interpol;
import Jama.Inverted;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;
import static java.lang.Math.*;
/**
 * Created by Сергей on 06.04.2017.
 */
public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("Интерполирование по равноотстоящим узлам.\n" +
                "Задача обратного интерполирования.\n" +
                "Нахождение производных таблично - заданной функции по формулам численного дифференцирования");
        DoubleUnaryOperator f = x -> (4*sin(x) - x)/2.0;
        System.out.print("Число значений в таблице m+1 = ");
        int s = in.nextInt();
        int m = s-1;
        System.out.print("Левый конец отрезка интерполирования a = ");
        double a = in.nextDouble();
        System.out.print("Правый конец отрезка интерполирования b = ");
        double b = in.nextDouble();
        double h = (b - a)/m;
        ArrayList<Double> X = new ArrayList<Double>();
        ArrayList<Double> Y = new ArrayList<Double>();
        System.out.println("i " + "         x_i       " + "           f_i");
        for (int i = 0; i<=m; i++) {
            X.add(a + i*h);
            Y.add(f.applyAsDouble(X.get(i)));
            System.out.printf(i + "          ");
            System.out.printf("%.9f", + X.get(i));
            System.out.print("           ");
            System.out.printf("%.9f%n", + Y.get(i));
        }
        System.out.println();
        System.out.print("Номер задания: ");
        int i = in.nextInt();
        switch (i) {
            case 1:
                Interpol.produce(f, a, b, X, Y, m);
                break;
            case 2:
                Inverted.search(f, a, b, X, Y, m);
                break;
            case 3:
                Derivated.compute(X,Y, h, m);
                break;
        }
    }
}
