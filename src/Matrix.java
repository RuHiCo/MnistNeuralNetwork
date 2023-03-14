public class Matrix {
    public static void print(double[][] array1) {
        //System.out.printf("{");
        for (int i = 0; i < array1.length; i++) {
            //System.out.printf("{");
            for (int j = 0; j < array1[0].length; j++) {
                System.out.printf("%5.3f  ", array1[i][j]);
                if (j < array1[0].length - 1) {
                    System.out.printf("  ");
                }
            }
            //System.out.printf("}");
            if (i < array1[0].length - 1) {
                System.out.printf("\n");
            }
        }
        System.out.printf("\n");
        //System.out.printf("}");
    }

    public static double[][] add(double[][] matrix1,double[][] matrix2) {
        if(matrix1.length==matrix2.length && matrix1[0].length==matrix2[0].length) {
            double[][] erg = matrix1;
            for(int i=0;i<matrix1.length;i++){
                for(int j=0;j<matrix1[0].length;j++) {
                    erg[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            return erg;
        }
        else {
            return null;
            //throw Exception();
        }
    }

    public static double[][] multiplyEachBy(double[][] matrix1,double num) {
        double[][] erg = new double[matrix1.length][matrix1[0].length];
        for(int i=0;i<matrix1.length;i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                erg[i][j] = matrix1[i][j] * num;
            }
        }
        return erg;
    }

    public static double[][] multiply(double[][] matrix1,double[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            return null;
            //throw Exception ('Not the correct dimension');
        }
        double[][] erg = new double[matrix1.length][matrix2[0].length];
        for (int j = 0; j < matrix2[0].length; j++) {
            for (int i = 0; i < matrix1.length; i++) {
                double zwerg = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    zwerg += matrix1[i][k] * matrix2[k][j];
                }
                erg[i][j] = zwerg;
            }
        }
        return erg;
    }

    public static double[] multiplyWithVector(double[][] matrix1,double[] vector) {
        if (matrix1[0].length != vector.length) {
            return null;
            //throw Exception ('Not the correct dimension');
        }
        double[] erg = new double[matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            double zwerg = 0;
            for (int k = 0; k < matrix1[0].length; k++) {
                zwerg += matrix1[i][k] * vector[k];
            }
            erg[i] = zwerg;
        }
        return erg;
    }

    public static double[][] multiplyVectors(double[] matrix1,double[] matrix2) {
        double[][] erg = new double[matrix1.length][matrix2.length];
        for (int j = 0; j < matrix2.length; j++) {
            for (int i = 0; i < matrix1.length; i++) {
                erg[i][j] = matrix1[i] * matrix2[j];
            }
        }
        return erg;
    }

    public static double[][] transpose(double[][] matrix1) {
        double[][] erg = new double[matrix1[0].length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                erg[j][i] = matrix1[i][j];
            }
        }
        return erg;
    }

}
