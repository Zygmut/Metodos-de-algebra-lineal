package metodos;

import java.io.Serializable;
import java.util.Scanner;
import metodos.UserExceptions.*;

public class Matrix implements Serializable {

    Scanner sc = new Scanner(System.in);

    public int n; //Dimension
    public double[][] A; //Matriz de coeficientes tal que Filas/columnas
    public double[] b; //Vector independiente 
    public int[] solOrder; //Solution order, used by Complete pivoting
    public boolean SEL; //Affects wheter the toString() method takes into account the b vector or not

    public Matrix() {
        System.out.print("Dimension: ");
        this.n = sc.nextInt();
        A = new double[n][n];
        b = new double[n];
        solOrder = new int[n];
        SEL = false;
        for (int i = 0; i < solOrder.length; i++) {
            solOrder[i] = i;
        }
        create();
    }

    public Matrix(int n) {
        this.n = n;
        A = new double[n][n];
        b = new double[n];
        solOrder = new int[n];
        SEL = false;
        for (int i = 0; i < solOrder.length; i++) {
            solOrder[i] = i;
        }
        create();
    }

    public Matrix(int n, boolean sel) {
        this.n = n;
        A = new double[n][n];
        b = new double[n];
        solOrder = new int[n];
        SEL = sel;
        for (int i = 0; i < solOrder.length; i++) {
            solOrder[i] = i;
        }
        create();
    }

    /*
     * Populates the matrix with 0.00
     */
    private void create() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = 0.00;
            }
            b[i] = 0.00;
        }
    }

    /*
     * Identity method
     */
    private void identity() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    A[i][j] = 1.00;
                } else {
                    A[i][j] = 0.00;
                }
            }
            b[i] = 0.00;
        }
    }

    /*
     * Creates a matrix 
     */
    public void populate() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("coefficient [" + j + "][" + i + "]: ");
                A[i][j] = sc.nextDouble();
            }
            if (SEL) {
                System.out.print("result [" + n + "]: ");
                b[i] = sc.nextDouble();
            }

        }

    }

    /*
     * Sums two matrix
     */
    public Matrix sum(Matrix A) throws DifferentSizeException {
        if (A.n != this.n) {
            throw new DifferentSizeException();
        } else {
            Matrix temp = new Matrix(A.n);
            for (int i = 0; i < A.n; i++) {
                for (int j = 0; j < A.n; j++) {
                    temp.A[i][j] = A.A[i][j] + this.A[i][j];
                }
                temp.b[i] = A.b[i] + this.b[i];
            }
            return temp;
        }
    }

    /*
     * Sums two matrix, STATIC version
     */
    public static Matrix sum(Matrix A, Matrix B) throws DifferentSizeException {
        if (A.n != B.n) {
            throw new DifferentSizeException();
        } else {
            Matrix temp = new Matrix(A.n);
            for (int i = 0; i < A.n; i++) {
                for (int j = 0; j < A.n; j++) {
                    temp.A[i][j] = A.A[i][j] + B.A[i][j];
                }
                temp.b[i] = A.b[i] + B.b[i];
            }
            return temp;
        }
    }

    /*
     * Sub two matrix
     */
    public Matrix sub(Matrix A) throws DifferentSizeException {
        if (A.n != this.n) {
            throw new DifferentSizeException();
        } else {
            Matrix temp = new Matrix(A.n);
            for (int i = 0; i < A.n; i++) {
                for (int j = 0; j < A.n; j++) {
                    temp.A[i][j] = A.A[i][j] - this.A[i][j];
                }
                temp.b[i] = A.b[i] - this.b[i];
            }
            return temp;
        }
    }

    /*
     * Sub two matrix, STATIC version
     */
    public static Matrix sub(Matrix A, Matrix B) throws DifferentSizeException {
        if (A.n != B.n) {
            throw new DifferentSizeException();
        } else {
            Matrix temp = new Matrix(A.n);
            for (int i = 0; i < A.n; i++) {
                for (int j = 0; j < A.n; j++) {
                    temp.A[i][j] = A.A[i][j] - B.A[i][j];
                }
                temp.b[i] = A.b[i] - B.b[i];
            }
            return temp;
        }
    }

    /*
     * Simple Gauss Sol(0 = L, 1 = U)
     */
    public Matrix[] gauss() {
        Matrix[] sol = new Matrix[2];
        Matrix temp = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp.A[i][j] = A[i][j];
            }
            temp.b[i] = b[i];
        }
        sol[0] = temp.gaussD();
        sol[1] = temp;
        return sol;
    }

    /*
     * Simple Gauss 
     */
    private Matrix gaussD() {
        Matrix L = new Matrix(this.n);
        L.identity();
        for (int p = 0; p < n; p++) {
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                L.A[i][p] = alpha;
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }
        return L;
    }

    /*
     * Gauss con pivotaje parcial Sol(0 = L, 1 = U, 2 = P)
     */
    public Matrix[] gaussP() {
        Matrix[] sol = new Matrix[3];
        Matrix temp = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp.A[i][j] = A[i][j];
            }
            temp.b[i] = b[i];
        }
        Matrix[] tempp = temp.gaussPD();
        sol[0] = tempp[0];
        sol[1] = temp;
        sol[2] = tempp[1];
        return sol;
    }

    /*
     * Gauss con pivotaje parcial SOL(0 = L, 1 = P)
     */
    private Matrix[] gaussPD() {
        Matrix[] sol = new Matrix[2];
        sol[0] = new Matrix(this.n);
        sol[1] = new Matrix(this.n);
        sol[0].identity();
        sol[1].identity();

        for (int p = 0; p < n; p++) {
            //busqueda del valor mas grande
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            sol[1].swapRow(p, max);
            //Cambio de filas
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;

            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            //Gauss
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                sol[0].A[i][p] = alpha;
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }
        return sol;
    }

    /*
     * Gauss con pivotaje maximal Sol(0 = L, 1 = U, 2 = P, 3 = Q)
     */
    public Matrix[] gaussPM() {
        Matrix[] sol = new Matrix[4];
        Matrix temp = new Matrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp.A[i][j] = A[i][j];
            }
            temp.b[i] = b[i];
        }
        Matrix[] tempp = temp.gaussPMD();
        sol[0] = tempp[0]; //L
        sol[1] = temp; //U
        for (int i = 2; i < 4; i++) {
            sol[i] = tempp[i - 1]; //P, Q, sol order
        }

        return sol;
    }

    /*
     * Gauss con pivotaje maximal SOL(0 = L, 1 = P, 2 = Q, 3 = Solution order)
     */
    private Matrix[] gaussPMD() {

        Matrix[] sol = new Matrix[3];
        for (int i = 0; i < sol.length; i++) {
            sol[i] = new Matrix(this.n);
            sol[i].identity();
        }

        for (int p = 0; p < n; p++) {
            //busqueda del valor mas grande
            int maxc = p;
            int maxr = p;
            for (int i = p + 1; i < n; i++) {
                for (int j = p + 1; j < n; j++) {
                    if (Math.abs(A[i][j]) > Math.abs(A[maxr][maxc])) {
                        maxr = i;
                        maxc = j;
                    }
                }
            }

            sol[1].swapRow(p, maxr); //P
            sol[2].swapRow(p, maxc); //Q

            int temp = solOrder[maxc];
            solOrder[maxc] = solOrder[p];
            solOrder[p] = temp;

            //Cambio de filas
            //Vertical
            this.swapCol(p, maxc);

            //Horizontal
            this.swapRow(p, maxr);

            double t = b[p];
            b[p] = b[maxr];
            b[maxr] = t;

            //Gauss
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                sol[0].A[i][p] = alpha;
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        return sol;
    }

    /*
     * Back subs algorithm
     */
    public double[] resolve() {
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * solution[j];
            }
            solution[i] = (b[i] - sum) / A[i][i];
        }
        double[] fSolution = sort(solution);
        return fSolution;
    }

    /*
     * Order solutions via solOrder
     */
    private double[] sort(double[] sol) {
        boolean sorted = false;
        int temp;
        double tempp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < solOrder.length - 1; i++) {
                if (solOrder[i] > solOrder[i + 1]) {
                    temp = solOrder[i];
                    solOrder[i] = solOrder[i + 1];
                    solOrder[i + 1] = temp;

                    tempp = sol[i];
                    sol[i] = sol[i + 1];
                    sol[i + 1] = tempp;
                    sorted = false;
                }
            }
        }
    return sol;
    }

    public void val() {
        //Valores que dependen de lo grande que sea tu matriz, abria que hacer un for
        //y que entrasen sus valores
        double[] c = {2.5, 2.333333333333333333333333333333333333, 2.75};

        //vector independiente
        b = new double[]{1, 2, 3};

        System.out.print("Matrix dimension: ");
        n = sc.nextInt();

        A = new double[n][n];
        //Valdennoseque matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == (n - 1)) {
                    A[i][j] = 1;
                } else {
                    A[i][j] = Math.pow(c[i], n - (j + 1));
                }
            }
        }
    }

    /*
     * Interchanges columns i and j of the A matrix 
     */
    private void swapCol(int i, int j) {
        double[] tempA = this.getCol(j);
        for (int k = 0; k < this.n; k++) {
            this.A[k][j] = this.A[k][i];
            this.A[k][i] = tempA[k];
        }
    }

    private double[] getCol(int i) {
        double[] out = new double[this.n];
        for (int j = 0; j < this.n; j++) {
            out[j] = this.A[j][i];
        }
        return out;
    }

    /*
     * Interchanges rows i and j of the A matrix 
     */
    public void swapRow(int i, int j) {
        double[] temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public String octexport() {
        String out = "[";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                out += A[i][j] + ", ";
            }
            if (i == n - 1) {
                out += A[i][n - 1];
            } else {
                out += A[i][n - 1] + "; ";
            }

        }
        out += "]";
        return out;
    }

    /*
     * String A 
     */
    public String Astring() {
        String out = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out += A[i][j] + " ";
            }
            out += "\n";
        }
        return out;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out += A[i][j] + " ";
            }
            if (SEL) {
                out += b[i];
            }
            out += "\n";

        }
        return out;
    }

}
