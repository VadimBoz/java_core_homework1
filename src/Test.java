import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    static int[][] field = new int[][]{
            {1,   2,  3,  4,  5,  6},
            {7,  15,  9,  10, 11, 12}
//            {13, 14, 15, 16, 17, 18}
//            {19, 20, 21, 22, 23, 24}
    };

    static int[][] field5 = new int[][]{
            {5, 7, 0},
            {6, 5, 0},
            {0, 0, 7},
            {0, 7, 3},
            {7, 2, 0},
            {1, 0, 9},

    };


    static int[][] field1 = new int[][]{
            {1, 0, 0, 0, 0, 2},
            {0, 1, 0, 0, 2, 0},
            {0, 0, 1, 2, 0, 0},
            {0, 0, 0, 0, 0, 0}
    };


    public static void main(String[] args) {
        int[][] ar11 = new int[][]{
                {0, 0, 0, 0, 0, 2},
                {1, 0, 0, 0, 2, 0},
                {0, 1, 0, 2, 0, 0},
                {0, 0, 1, 0, 0, 0}
        };


        for (int k = 0 ; k < 9  ; k++ ) {
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
//                System.out.println("k=" + k + ", j= " + j + ", i = " + i);
                if( i < 6 && j < 3 ) {
//                    System.out.println("*k=" + k + ", j= " + j + ", i = " + i);
                    System.out.print( field5[i][j] + " " );
                }
            }
            System.out.println();
        }
//
//
//        System.out.println();
//        for (int k = -3; k <= 11 ; k++ ) {
//            for( int j = 6 ; j >= 0; j-- ) {
//                int i = j - k;
//                if( i < 6 && j < 3  && i >= 0 && j >= 0) {
//                    System.out.print( field5[i][j] + " " );
//                }
//            }
//            System.out.println();
//        }

        int WIN_COUNT = 3;   int dot = 15;
        int countMax = 0;
        int count = 0;
        int fieldSizeY = 6;
        int fieldSizeX = 3;

        for (int k = -fieldSizeY - 1; k < fieldSizeY + fieldSizeX - 4; k++) {
            count = 0;
            for (int i = fieldSizeX; i >= 0; i--) {
                int j = i - k;
                if (i < fieldSizeY  && j < fieldSizeX && j >=0) {
                    System.out.print( field5[i][j] + " " );
                    if (field5[i][j] == dot) {
                        count++;
                        if (count > countMax) countMax = count;
                    } else {
                        count = 0;
                    }
                }
            }
            System.out.println();

        }

        System.out.println("countMax= " + countMax);


    }
}