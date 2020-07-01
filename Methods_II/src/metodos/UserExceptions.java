package metodos;

/**
 *
 * @author Zygmut
 */
public class UserExceptions extends Exception {

    static class ArrayEmptyException extends Exception {

        public ArrayEmptyException() {
            super("There are no Matrix");
        }
    }

    static class DifferentSizeException extends Exception {

        public DifferentSizeException() {
            super("Matrix don't have the same size");
        }
    }

    static class NotMultiplicableException extends Exception {

        public NotMultiplicableException() {
            super("Matrix are not multiplicable");
        }
    }
    
    static class NotEnoughMatrixException extends Exception {

        public NotEnoughMatrixException() {
            super("There aren't enough matrix");
        }
    }


}
