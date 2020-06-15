package metodos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import metodos.UserExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum menu {
    Create, Remove, Show, Calc, Heat, Exit;
}

enum calc {
    Gauss, Gauss_pivot, Gauss_complete, Sum, Sub, Exit
}

/**
 *
 * @author Zygmut
 */
public class Metodos {

    Scanner sc = new Scanner(System.in); //Scanner
    ArrayList<Matrix> matr = new ArrayList(); //ArrayList With all the Matrix

    ObjectOutputStream oos;
    ObjectInputStream ois;

    public void inicio() {
        //Program here

        while (true) {
            mainMenu();
            int opt;
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opt = -1;
            }

            switch (opt) {

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
                        calcMenu();
                        int opt1;
                        try {
                            opt1 = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            opt1 = -1;
                        }
                        try {
                            switch (opt1) {
                                case 1: //Gauss
                                    if (matr.isEmpty()) {
                                        throw new ArrayEmptyException();
                                    } else {
                                        if (matr.size() == 1) {
                                            opt2 = 0;
                                        } else {
                                            opt2 = checkMatr();
                                        }
                                        sol = matr.get(opt2).gauss();
                                        System.out.println("\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));
                                    }
                                    System.out.println("");
                                    break;

                                case 2: //GaussP
                                    if (matr.isEmpty()) {
                                        throw new ArrayEmptyException();
                                    } else {
                                        if (matr.size() == 1) {
                                            opt2 = 0;
                                        } else {
                                            opt2 = checkMatr();
                                        }
                                        sol = matr.get(opt2).gaussP();
                                        System.out.println("\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nP\n" + sol[2].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));
                                    }
                                    System.out.println("");
                                    break;

                                case 3: //GaussPM
                                    if (matr.isEmpty()) {
                                        throw new ArrayEmptyException();
                                    } else {
                                        if (matr.size() == 1) {
                                            opt2 = 0;
                                        } else {
                                            opt2 = checkMatr();
                                        }
                                        sol = matr.get(opt2).gaussPM();
                                        System.out.println("\nL: \n" + sol[0].toString() + "\nU:\n" + sol[1].toString() + "\nP\n" + sol[2].toString() + "\nQ\n" + sol[3].toString() + "\nSol: " + Arrays.toString(sol[1].resolve()));
                                    }
                                    System.out.println("");
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
                                    System.out.println("");
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
                                    System.out.println("");
                                    break;

                                case 6: //Exit
                                    calcexit = true;
                                    System.out.println("");
                                    break;

                                default: //Def
                                    System.out.println("");
                                    break;
                            }
                        } catch (ArrayEmptyException | DifferentSizeException | NotEnoughMatrixException e) {
                            System.out.println("ERROR: " + e.getLocalizedMessage());
                            System.out.println("");
                        }
                    }
                    System.out.println("");
                    break;

                case 5:
                    double[][] u = new double[25][50];
                    double[] w = new double[25];
                    //f(x)
                    for (int i = 0; i < u[0].length; i++) {
                        for (int j = 0; j < u.length; j++) {
                            u[j][i] = 0;
                        }

                    }

                    ArrayList<Double> l = new ArrayList();
                    ArrayList<Double> r = new ArrayList();
                    Matrix x = new Matrix(25);
                    Double L = 1.00,
                     T = 10.00,
                     I = 25.00,
                     J = 50.00,
                     k = T / J,
                     h = L / I,
                     a = k / Math.pow(h, 2);

                    for (int i = 0; i < x.n; i++) {
                        for (int j = 0; j < x.n; j++) {
                            if (i == j) {
                                x.A[i][j] = 251;
                                try {
                                    x.A[i][j - 1] = -125;
                                } catch (Exception e) {
                                }
                                try {
                                    x.A[i][j + 1] = -125;
                                } catch (Exception e) {
                                }
                            }
                        }
                    }

                    for (int i = 1; i <= 50; i++) {
                        Double aux1 = (6 / Math.PI) * Math.atan(10 * i);
                        Double aux2 = -(2 / Math.PI) * Math.atan(10 * i);
                        l.add(aux1);
                        r.add(aux2);
                    }
                    System.out.println("---DATA---\nL: " + L + "\nT: " + T + "\nI: " + I + "\nJ: " + J + "\n");
                    System.out.println("k: " + k);
                    System.out.println("h: " + h);
                    System.out.println("h^2: " + Math.pow(h, 2));
                    System.out.println("\u03B1: " + a + "\n");
                    System.out.println("---Matrix---");
                    System.out.println(x.octexport() + "\n");
                    System.out.println("---Frontiers---");
                    System.out.println("Left frontier: " + Arrays.toString(l.toArray()));
                    System.out.println("Right frontier: " + Arrays.toString(r.toArray()));

                    System.out.println("\n---CALCULOS---");
                    //Inicializar matriz u

                    for (int i = 0; i < 1; i++) { //Condicion representa la cantidad  de pasos que va a hacer el calculo

                        for (int j = 0; j < u.length; j++) { //Recorrido del hilo
                            if (j == 0 ) {
                                u[j][0] += a * l.get(i);
                            }else if(j == u.length - 1){
                                u[j][0] += a * l.get(i);
                            }
                        }
                        Matrix mat = new Matrix(25, true);
                        mat.A = x.A;
                        
                        for (int j = 0; j < u.length; j++) {
                            mat.b[j] = u[j][i];
                        }
                        System.out.println(mat.toString());
                        
                        System.out.println(Arrays.toString(mat.gaussPM()[1].resolve()));
                        System.out.println(Arrays.toString(mat.gaussP()[1].resolve()));
                    }

                    break;

                case 6: //Exit 
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

    public void mainMenu() {
        System.out.println("*-------------------------------*");
        System.out.println("|           MAIN MENU           |");
        System.out.println("*-------------------------------*");
        for (int i = 0; i < menu.values().length; i++) {
            System.out.println((i + 1) + ") " + menu.values()[i].name());
        }
        System.out.println("---------------------------------");
        System.out.print("Option: ");
    }

    public void calcMenu() {
        System.out.println("*-------------------------------*");
        System.out.println("|           CALC MENU           |");
        System.out.println("*-------------------------------*");
        for (int i = 0; i < calc.values().length; i++) {
            System.out.println((i + 1) + ") " + calc.values()[i].name().replaceAll("_", " "));
        }
        System.out.println("---------------------------------");
        System.out.print("Option: ");
    }

    public static void main(String[] args) {
        new Metodos().inicio();
    }

}
