package metodos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import metodos.UserExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum menu {
    Create, Remove, Show, Calc, Heat, Extra, Exit;
}

enum calc {
    Gauss, Gauss_pivot, Gauss_complete, Sum, Sub, Exit
}

/**
 *
 * @author Zygmut
 */
public class Metodos {

    Scanner sc = new Scanner(System.in);
    ArrayList<Matrix> matr = new ArrayList();

    public void inicio() {
        while (true) {
            switch (mainMenu()) {

                case 1: //Create
                    try {
                        boolean sel = false;
                        System.out.print("SEL (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            sel = true;
                        }
                        System.out.print("Dimension: ");
                        Matrix tempMat = new Matrix(Integer.parseInt(sc.nextLine()), sel);
                        tempMat.populate();
                        matr.add(tempMat);
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: " + e.getLocalizedMessage());
                    }
                    System.out.println("");
                    break;

                case 2: //Remove
                    try {
                        if (matr.isEmpty()) {
                            throw new ArrayEmptyException();
                        } else {
                            int rem = checkMatr();
                            matr.remove(rem);
                            System.out.println("Matrix [" + rem + "] removed");
                        }
                    } catch (NumberFormatException | ArrayEmptyException e) {
                        System.out.println("ERROR: " + e.getLocalizedMessage());
                    }
                    System.out.println("");
                    break;

                case 3: //Show
                    try {
                        System.out.println("");
                        if (matr.isEmpty()) {
                            throw new ArrayEmptyException();
                        } else {
                            System.out.print(matr.get(0).toString());
                            for (int i = 1; i < matr.size(); i++) {
                                System.out.println("---------------------------------");
                                System.out.print(matr.get(i).toString());
                            }

                        }
                    } catch (ArrayEmptyException e) {
                        System.out.println("ERROR: " + e.getLocalizedMessage());
                    }
                    System.out.println("");
                    break;

                case 4: //Calc
                    boolean calcexit = false;
                    int opt2;
                    Matrix[] sol;
                    System.out.println("");

                    while (!calcexit) {
                        try {
                            switch (calcMenu()) {
                                case 1: //Gauss
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            opt2 = 0;
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            break;
                                    }
                                    sol = matr.get(opt2).gauss();
                                    System.out.println("---FINAL---\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));

                                    break;

                                case 2: //GaussP
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            opt2 = 0;
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            break;
                                    }
                                    sol = matr.get(opt2).gaussP();
                                    System.out.println("---FINAL---\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nP\n" + sol[2].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));

                                    break;

                                case 3: //GaussPM
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            opt2 = 0;
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            break;
                                    }
                                    sol = matr.get(opt2).gaussPM();
                                    System.out.println("---FINAL---\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nP\n" + sol[2].toString() + "\nQ\n" + sol[3].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));

                                    break;
                                case 4: //Sum
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            throw new NotEnoughMatrixException();
                                        case 2:
                                            matr.add(Matrix.sum(matr.get(0), matr.get(1)));
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            int opt3 = checkMatr();
                                            matr.add(Matrix.sum(matr.get(opt2), matr.get(opt3)));
                                            break;
                                    }
                                    break;

                                case 5: //Sub
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            throw new NotEnoughMatrixException();
                                        case 2:
                                            matr.add(Matrix.sub(matr.get(0), matr.get(1)));
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            int opt3 = checkMatr();
                                            matr.add(Matrix.sub(matr.get(opt2), matr.get(opt3)));
                                            break;
                                    }
                                    break;

                                case 6: //Exit
                                    calcexit = true;
                                    break;

                                default: //Def
                                    break;
                            }
                            System.out.println("");
                        } catch (ArrayEmptyException | DifferentSizeException | NotEnoughMatrixException e) {
                            System.out.println("ERROR: " + e.getLocalizedMessage());
                            System.out.println("");
                        }
                    }
                    System.out.println("");
                    break;

                case 5:
                    int t = 0,
                     s = 0;

                    System.out.print("Rod samples: "); //I
                    try {
                        s = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                    }

                    System.out.print("\nTime stamps: ");
                    try {
                        t = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                    }

                    double[][] u = new double[s][t];
                    double[] w = new double[s];

                    //f(x)
                    for (int i = 0; i < u[0].length && i == 0; i++) {
                        for (int j = 0; j < u.length; j++) {
                            u[j][i] = 0;
                        }
                    }

                    //Variables
                    ArrayList<Double> l = new ArrayList();
                    ArrayList<Double> r = new ArrayList();
                    Matrix bigHeat = new Matrix(u.length);
                    Double L = 1.00,
                     T = 10.00,
                     I = (double) s,
                     J = (double) t,
                     k = T / J,
                     h = L / I,
                     a = k / Math.pow(h, 2);

                    //Generate big heat matrix 
                    for (int i = 0; i < bigHeat.n; i++) {
                        for (int j = 0; j < bigHeat.n; j++) {
                            if (i == j) {
                                bigHeat.A[i][j] = 251;
                                try {
                                    bigHeat.A[i][j - 1] = -125;
                                } catch (Exception e) {
                                }
                                try {
                                    bigHeat.A[i][j + 1] = -125;
                                } catch (Exception e) {
                                }
                            }
                        }
                    }

                    for (int i = 1; i <= u[0].length; i++) {
                        Double aux1 = (6 / Math.PI) * Math.atan(10 * i);
                        Double aux2 = -(2 / Math.PI) * Math.atan(10 * i);
                        l.add(aux1);
                        r.add(aux1);
                    }
                    System.out.println("---DATA---\nL: " + L + "\nT: " + T + "\nI: " + I + "\nJ: " + J + "\n");
                    System.out.println("k: " + k);
                    System.out.println("h: " + h);
                    System.out.println("h^2: " + Math.pow(h, 2));
                    System.out.println("\u03B1: " + a + "\n");
                    System.out.println("---Matrix---");
                    System.out.println(bigHeat.octexport() + "\n");
                    System.out.println("---Frontiers---");
                    System.out.println("Left frontier: " + Arrays.toString(l.toArray()));
                    System.out.println("Right frontier: " + Arrays.toString(r.toArray()));

                    System.out.println("\n---CALCULOS---");

                    //Inicializar matriz u
                    for (int i = 0; i < u[0].length; i++) { //Condicion representa la cantidad  de pasos que va a hacer el calculo

                        for (int j = 0; j < u.length; j++) { //Recorrido del hilo
                            if (j == 0) { //Fronteras
                                u[j][i] += a * l.get(i);
                            } else if (j == u.length - 1) {
                                u[j][i] += a * r.get(i);
                            }
                        }

                        Matrix mat = new Matrix(u.length, true);//Generar el SEL para aplicar GaussPM
                        mat.A = bigHeat.A;

                        for (int j = 0; j < u.length; j++) {
                            mat.b[j] = u[j][i];
                        }

                        w = mat.gaussPM()[1].resolve();

                        for (int j = 0; j < u.length && i != u[0].length - 1; j++) { //Copy 
                            u[j][i + 1] = w[j];
                            System.out.print(u[j][i + 1] + " ");
                        }
                        System.out.println("");

                        //System.out.println(Arrays.toString(w));
                    }
                    break;

                case 6: //Extra
                    Matrix tempMatrix = new Matrix(15);
                    for (int i = 0; i < tempMatrix.n; i++) {
                        for (int j = 0; j < tempMatrix.n; j++) {
                            if (i == j) {
                                tempMatrix.A[i][j] = (1 + Math.pow(3, 2)) / 2;
                                try {
                                    tempMatrix.A[i][j - 1] = -(Math.pow(3, 2)/4);
                                } catch (Exception e) {
                                }
                                try {
                                    tempMatrix.A[i][j + 1] = -(Math.pow(3, 2)/4);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                    System.out.println(tempMatrix.octexport());

                    break;
                case 7: //Exit 
                    System.exit(0);
                    break;
                default: //Def
                    System.out.println("");
                    break;
            }
        }
    }

    /*
     * Checks matr Array, convenience
     */
    public int checkMatr() {
        int rem;
        if (matr.size() == 1) {
            rem = 0;
        } else {
            System.out.print("Wich matrix: ");
            rem = Integer.parseInt(sc.nextLine());
        }
        return rem;
    }

    public int mainMenu() {
        System.out.println("*-------------------------------*");
        System.out.println("|           MAIN MENU           |");
        System.out.println("*-------------------------------*");
        for (int i = 0; i < menu.values().length; i++) {
            System.out.println((i + 1) + ") " + menu.values()[i].name().replaceAll("_", " "));
        }
        System.out.println("---------------------------------");
        System.out.print("Option: ");
        int opt;
        try {
            opt = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opt = -1;
        }
        return opt;
    }

    public int calcMenu() {
        System.out.println("*-------------------------------*");
        System.out.println("|           CALC MENU           |");
        System.out.println("*-------------------------------*");
        for (int i = 0; i < calc.values().length; i++) {
            System.out.println((i + 1) + ") " + calc.values()[i].name().replaceAll("_", " "));
        }
        System.out.println("---------------------------------");
        System.out.print("Option: ");
        int opt1;
        try {
            opt1 = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opt1 = -1;
        }
        return opt1;
    }

    public static void main(String[] args) {
        new Metodos().inicio();
    }

}
