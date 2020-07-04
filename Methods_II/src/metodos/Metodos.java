package metodos;

import metodos.UserExceptions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum Main_menu {
    Create, Remove, Show, Calc, Extra, Exit;
}

enum Calcul {
    Gauss, Gauss_pivot, Gauss_complete, Sum, Sub, Det, Linear_transformation_matrix_with_other_bases, Exit
}

enum Extra {
    Heat, Exit
}

/**
 *
 * @author Zygmut
 */
public class Metodos {

    ArrayList menus = new ArrayList();
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
                                    sol = matr.get(opt2).gauss(true);
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
                                    sol = matr.get(opt2).gaussP(true);
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
                                    sol = matr.get(opt2).gaussPM(true);
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

                                case 6: //Det
                                    switch (matr.size()) {
                                        case 0:
                                            throw new ArrayEmptyException();
                                        case 1:
                                            System.out.println("Determinant: " + matr.get(0).det());
                                            break;
                                        default:
                                            opt2 = checkMatr();
                                            int opt3 = checkMatr();
                                            System.out.println("Determinant: " + matr.get(opt3).det());
                                            break;
                                    }
                                    break;
                                case 7: //Linear transformation matrix with other bases
                                    Matrix T,
                                     B,
                                     t_B;
                                    System.out.print("Dimension: ");
                                    int dim = Integer.parseInt(sc.nextLine());
                                    T = new Matrix(dim);
                                    B = new Matrix(dim);
                                    t_B = new Matrix(dim);

                                    System.out.println("T:");
                                    T.populate();
                                    System.out.println("\nB:");
                                    B.populate();

                                    for (int i = 0; i < dim; i++) {
                                        Matrix temp = new Matrix(dim);
                                        for (int j = 0; j < dim; j++) {
                                            for (int k = 0; k < dim; k++) {
                                                temp.A[j][k] = B.A[j][k];
                                            }
                                            temp.b[j] = T.A[j][i];
                                        }
                                        double[] solution = temp.gaussPM(false)[1].resolve();
                                        for (int j = 0; j < dim; j++) {
                                            t_B.A[j][i] = solution[j];
                                        }
                                    }

                                    System.out.println("\nT:\n" + T.Astring() + "\nB:\n" + B.Astring() + "\nT_B:\n" + t_B.Astring());
                                    break;

                                case 8: //Exit
                                    calcexit = true;
                                    break;

                                default: //Def
                                    break;
                            }
                            System.out.println("");
                        } catch (ArrayEmptyException | DifferentSizeException | NotEnoughMatrixException e) {
                            System.out.println("ERROR: " + e.getLocalizedMessage());
                        }
                    }
                    break;

                case 5: //Extra
                    boolean extraExit = false;
                    System.out.println("");

                    while (!extraExit) {
                        try {
                            switch (extraMenu()) {
                                case 1: //Heat
                                    int t = 0,
                                     s = 0;

                                    System.out.print("Rod samples: ");
                                    try {
                                        s = Integer.parseInt(sc.nextLine());
                                    } catch (NumberFormatException e) {
                                    }

                                    System.out.print("\nTime stamps: ");
                                    try {
                                        t = Integer.parseInt(sc.nextLine());
                                    } catch (NumberFormatException e) {
                                    }

                                    double[][] M = new double[s][t];

                                    //Vectores sin las fronteras, para simplificar la SEL
                                    double[] u = new double[s - 2];
                                    double[] w = new double[s - 2];

                                    ArrayList<Double> l = new ArrayList();
                                    ArrayList<Double> r = new ArrayList();
                                    Double L = 1.00,
                                     T = 10.00,
                                     I = (double) s,
                                     J = (double) t,
                                     k = T / J,
                                     h = L / I,
                                     a = k / Math.pow(h, 2),
                                     aux1,
                                     aux2;
                                    Matrix bigHeat = heatMatrix(a, u.length); //SEL Matrix

                                    //Fronteras con respecto al tiempo
                                    for (int i = 1; i <= M[0].length + 1; i++) {
                                        aux1 = (6 / Math.PI) * Math.atan(10 * i);
                                        aux2 = (6 / Math.PI) * Math.atan(10 * i);
                                        l.add(aux1);
                                        r.add(aux2);
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

                                    //Calculo
                                    for (int i = 0; i < M[0].length; i++) {
                                        u = w;

                                        //Guardar el vector u en M
                                        M[0][i] = l.get(i);
                                        for (int j = 0; j < M.length - 2; j++) {
                                            M[j + 1][i] = u[j];
                                        }
                                        M[M.length - 1][i] = r.get(i);

                                        //Adaptar el vector u a la SEL
                                        u[0] += +a * l.get(i + 1);
                                        u[u.length - 1] += +a * r.get(i + 1);

                                        bigHeat.b = u;
                                        w = bigHeat.gaussPM(false)[1].resolve();
                                    }
                                    //System.out.println(print(M));

                                    String sMatrix = "";
                                    for (int i = 0; i < J; i++) {
                                        sMatrix += "    $fila_{" + i + "}$&: [";
                                        for (int j = 0; j < M.length - 1; j++) {
                                            sMatrix += String.format("%.5g", M[j][i]) + ", ";
                                        }
                                        sMatrix += String.format("%.5g", M[M.length - 1][i]) + "]\\\\\n";
                                    }
                                    System.out.println(sMatrix);
                                    //System.out.println(Rexport(M));
                                    break;
                                case 2:
                                    extraExit = true;
                                    break;
                                default:
                                    break;
                            }
                            System.out.println("");
                        } catch (Exception e) {
                            System.out.println("ERROR: " + e.getLocalizedMessage());
                        }
                    }
                    break;

                case 6: //Exit 
                    System.exit(0);
                    break;
                default: //Def
                    break;
            }
            System.out.println("");
        }
    }

    /*
     *Generates the heat matrix given the alpha value and the dimension
     */
    private Matrix heatMatrix(double a, int n) {
        Matrix temp = new Matrix(n, true);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    temp.A[i][j] = 1 + 2 * a;
                    try {
                        temp.A[i][j - 1] = -a;
                    } catch (Exception e) {
                    }
                    try {
                        temp.A[i][j + 1] = -a;
                    } catch (Exception e) {
                    }
                }
            }
        }
        return temp;
    }

    /*
     * Prints the M Matrix, just for convenience
     */
    private String print(double[][] M) {
        String sMatrix = "";
        for (int i = 0; i < M[0].length; i++) {
            sMatrix += i + " [";
            for (int j = 0; j < M.length - 1; j++) {
                sMatrix += String.format("%.4g", M[j][i]) + " | ";
            }
            sMatrix += String.format("%.4g", M[M.length - 1][i]) + "]\n";
        }
        return sMatrix;
    }

    /*
     * Prints the M Matrix, just for convenience
     */
    private String Rexport(double[][] M) {
        String sMatrix = "";
        String temp = "matrix = rbind(";
        for (int i = 0; i < M[0].length; i++) {
            sMatrix += "c" + i + "= c(";
            for (int j = 0; j < M.length - 1; j++) {
                sMatrix += String.format("%.5g", M[j][i]) + ", ";
            }
            sMatrix += String.format("%.5g", M[M.length - 1][i]) + ")\n";
            temp += "c" + i;
            if (i != M[0].length - 1) {
                temp += ",";
            }

        }
        sMatrix += temp + ")";
        return sMatrix;
    }

    /*
     * Export Matrix to LaTeX
     */
    public String latexExport(double[][] x) {
        String s = "\\begin{pmatrix}\n";
        for (int i = 0; i < x[0].length; i++) {
            for (int j = 0; j < x.length; j++) {
                s += String.format("%.4g", x[j][i]);
                if (j != x.length - 1) {
                    s += " & ";
                }
            }
            if (i != x[0].length - 1) {
                s += "\\\\";
            }
            s += "\n";
        }
        return s += "\\end{pmatrix}";
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
        for (int i = 0; i < Main_menu.values().length; i++) {
            System.out.println((i + 1) + ") " + Main_menu.values()[i].name().replaceAll("_", " "));
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
        for (int i = 0; i < Calcul.values().length; i++) {
            System.out.println((i + 1) + ") " + Calcul.values()[i].name().replaceAll("_", " "));
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

    public int extraMenu() {
        System.out.println("*-------------------------------*");
        System.out.println("|          EXTRA MENU           |");
        System.out.println("*-------------------------------*");
        for (int i = 0; i < Extra.values().length; i++) {
            System.out.println((i + 1) + ") " + Extra.values()[i].name().replaceAll("_", " "));
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

    public static void main(String[] args) {
        new Metodos().inicio();
    }
}
