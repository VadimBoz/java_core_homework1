import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {


    public static void main(String[] args) {
        int field_x  = 15;
        int field_y = 4;
        int count0  = 10;
        int[][] array = new int[field_x][field_y];
        for (int y = 0; y < field_y; y++) {
            for (int x = 0; x < field_x; x++) {
                array[x][y] = ++count0;
                System.out.print(array[x][y] + " ");
            }
            System.out.println();
        }


        System.out.println();

        for (int k = 0 ; k < field_x + field_y  ; k++ ) {
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < field_x && j < field_y ) {
                    System.out.print( array[i][j] + " " );
                }
            }
            System.out.println();
        }


//        System.out.println();
//        for (int k = -3; k <= 11 ; k++ ) {
//            for( int j = 6 ; j >= 0; j-- ) {
//                int i = j - k;
//                if( i < field_y && j < field_x  && i >= 0 && j >= 0) {
//                    System.out.print( array[i][j] + " " );
//                }
//            }
//            System.out.println();
//        }

        int WIN_COUNT = 3;   int dot = 15;
        int countMax = 0;
        int count = 0;
        int fieldSizeY = field_y;
        int fieldSizeX = field_x;

        for (int k = -fieldSizeY; k < fieldSizeY + fieldSizeX ; k++) {
            count = 0;
            for (int i = fieldSizeX; i >= 0; i--) {
                int j = i - k;
                if (i < fieldSizeX  && j < fieldSizeY && j >=0) {
                    System.out.print( array[i][j] + " " );
                    if (array[i][j] == dot) {
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