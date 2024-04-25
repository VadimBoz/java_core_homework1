

import java.util.Random;
import java.util.Scanner;


public class Main {


    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static int WIN_COUNT;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static  int x_HUMAN;
    private static  int y_HUMAN;

    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;
    private static int countTurns = 0;

    public static final String RESET = "\u001B[0m";
    public static final String BLUE_BACKGROUND = "\u001B[30;44m";
    public static final String WHITE_BACKGROUND = "\033[30;107m";



    public static void main(String[] args) {
        while (true){
            initialize(6,9, 4);
            printField();
            while (true){
                humanTurn();
                printField();
                if (checkState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }

    }

    /**
     * Инициализация объектов игры
     */
    static void initialize(int fieldSizeX, int fieldSizeY, int WIN_COUNT){
        Main.fieldSizeX = fieldSizeX;
        Main.fieldSizeY = fieldSizeY;
        Main.WIN_COUNT = WIN_COUNT;
        countTurns = 0;
        field = new char[fieldSizeX][fieldSizeY];
        for(int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField(){
        System.out.print(BLUE_BACKGROUND  + "  ");
        for(int x = 0; x < fieldSizeX; x++){
            System.out.print(" " + (x + 1));
        }
        System.out.println("   " + RESET);

        for(int y = 0; y < fieldSizeY; y++){
            System.out.print(BLUE_BACKGROUND + " ");
            System.out.print( y + 1 + WHITE_BACKGROUND + "|" );

            for (int x = 0; x < fieldSizeX; x++){
                System.out.print(field[x][y] + "|");
            }
            System.out.print(BLUE_BACKGROUND);
            System.out.println(y + 1 +  " " + RESET);
        }

        System.out.print(BLUE_BACKGROUND  + "  ");
        for(int y = 0; y < fieldSizeX; y++){
            System.out.print(" " + (y + 1));
        }
        System.out.println("   " + RESET);

        System.out.println();
        for(int y = 0; y < fieldSizeY * 2 + 2; y++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static void humanTurn(){
        int x;
        int y;

        while (true) {
            System.out.printf("Введите координаты хода X (от 1 до %s) и Y(от 1 до %s) через пробел: \n", fieldSizeX, fieldSizeY);
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            x_HUMAN = x;
            y_HUMAN = y;
            if (isCellValid(x, y) && isCellEmpty(x, y)) {
                field[x][y] = DOT_HUMAN;
                countTurns++;
                break;
            } else {
                System.out.println("Введены неверные значения Х и У");
            }
        }

    }


    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x
     * @param y
     * @return boolean
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn() {
        int x;
        int y;

        int jAi = checkContinuousVertical(DOT_HUMAN, WIN_COUNT - 1);
        if (jAi != -1) {
                field[x_HUMAN][jAi] = DOT_AI;
                countTurns++;
                return;
        }

        int iAi = checkContinuousHorizontal(DOT_HUMAN, WIN_COUNT - 1);
        if (iAi != -1) {
            field[iAi][y_HUMAN] = DOT_AI;
            countTurns++;
            return;
        }


        int[] dAi = checkContinuousDiagonal(DOT_HUMAN, WIN_COUNT - 1);
            if (dAi[0] != -1) {
                field[dAi[0]][dAi[1]] = DOT_AI;
                countTurns++;
                return;
            }

        do{
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
        countTurns++;
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        return countTurns == fieldSizeX * fieldSizeY ;
    }

    /**
     * TODO: Переработать в рамках домашней работы
     * Метод проверки победы
     * @param dot фишка игрока
     * @return
     */
    static boolean checkWin(char dot, int winCount){

        // Проверка победы по вертикалям
        int count;
        int countMax = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            count = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == dot) {
                    count++;
                    if (count > countMax) {
                        countMax = count;
                    }
                } else {
                    count = 0;
                }
            }
        }
        if (countMax >= WIN_COUNT) return true;

        // Проверка победы по горизонтали
        countMax = 0;
        for (int j = 0; j < fieldSizeY; j++) {
            count = 0;
            for (int i = 0; i < fieldSizeX; i++) {
                if (field[i][j] == dot) {
                    count++;
                    if (count > countMax) countMax = count;
                } else {
                    count = 0;
                }
            }
        }
        if (countMax >= WIN_COUNT) return true;


//          Проверка победы по восходящей диагонали
        countMax = 0;
        for (int k = 0 ; k < fieldSizeY + fieldSizeX; k++ ) {
            count = 0;
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < fieldSizeX && j < fieldSizeY ) {
                    if (field[i][j] == dot) {
                        count++;
                        if (count > countMax) countMax = count;
                    } else {
                        count = 0;
                    }
                }
            }
        }
        if (countMax >= WIN_COUNT) return true;


//        Проверка победы по низходящей диагонали
        countMax = 0;
        for (int k = -fieldSizeY - 1; k < fieldSizeY + fieldSizeX; k++) {
            count = 0;
            for (int i = fieldSizeX; i >= 0; i--) {
                int j = i - k;
                if (i < fieldSizeX  && j < fieldSizeY && j >=0) {
//                    System.out.print( field[i][j] + " " );
                    if (field[i][j] == dot) {
                        count++;
                        if (count > countMax) countMax = count;
                    } else {
                        count = 0;
                    }
                }
            }
        }
        if (countMax >= WIN_COUNT) return true;
        return false;
    }


    static int checkContinuousVertical(char dot, int winCount) {
        int count = 0;
        int jEnd = y_HUMAN;
            for (int j = y_HUMAN; j < fieldSizeY; j++) {
                if (field[x_HUMAN][j] == dot) {
                    count++;
                    jEnd = j;
                } else {
                    break;
                }
            }
            if (count >= winCount) {
                if (jEnd + 1 < fieldSizeY && isCellEmpty(x_HUMAN, jEnd + 1)) {
                    return jEnd + 1;
                }
                if (y_HUMAN - 1 >= 0  && isCellEmpty(x_HUMAN, y_HUMAN - 1)) {
                    return y_HUMAN - 1;
                }
            }

            count = 0;
            for (int j = y_HUMAN; j >= 0; j--) {
                if (field[x_HUMAN][j] == dot) {
                    count++;
                    jEnd = j;
                } else {
                    break;
                }
            }
            if (count >= winCount) {
                if (jEnd - 1 >= 0 && isCellEmpty(x_HUMAN, jEnd - 1)) {
                    return jEnd - 1;
                }
                if (y_HUMAN + 1 < fieldSizeY  && isCellEmpty(x_HUMAN, y_HUMAN + 1)) {
                    return y_HUMAN + 1;
                }
            }
            return -1;
    }

    static int checkContinuousHorizontal(char dot, int winCount) {
        int count = 0;
        int iEnd = x_HUMAN;
        for (int i = x_HUMAN; i < fieldSizeX; i++) {
            if (field[i][y_HUMAN] == dot) {
                count++;
                iEnd = i;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if (iEnd + 1 < fieldSizeX && isCellEmpty(iEnd + 1, y_HUMAN)) {
                return iEnd + 1;
            }
            if (x_HUMAN - 1 >= 0 && isCellEmpty(x_HUMAN - 1, y_HUMAN)) {
                return x_HUMAN - 1;
            }
        }

        count = 0;
        for (int i = x_HUMAN; i >= 0; i--) {
            if (field[i][y_HUMAN] == dot) {
                count++;
                iEnd = i;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if ((iEnd - 1 >= 0 && field[iEnd - 1][y_HUMAN] == '*')) {
                return iEnd - 1;
            }
            if (x_HUMAN + 1 < fieldSizeX  && isCellEmpty(x_HUMAN + 1, y_HUMAN)) {
                return x_HUMAN + 1;
            }
        }
        return -1;
    }

    static int[] checkContinuousDiagonal(char dot, int winCount) {
        int count = 0;
        int iEnd = x_HUMAN;
        int jEnd = y_HUMAN;
        for (int i = x_HUMAN, j = y_HUMAN; i < fieldSizeX && j >= 0; i++, j--) {  //вправо вверх
            if (field[i][j] == dot) {
                count++;
                iEnd = i;
                jEnd = j;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if ((iEnd + 1 < fieldSizeX) && (jEnd - 1 >= 0) && field[iEnd + 1][jEnd - 1] == '*') {
                return new int[] {iEnd + 1, jEnd - 1};
            }
            if ((x_HUMAN - 1 >= 0  && y_HUMAN + 1 < fieldSizeY) && isCellEmpty(x_HUMAN - 1, y_HUMAN + 1)) {
                return new int[]{x_HUMAN - 1, y_HUMAN + 1};
            }
        }

        count = 0;
        for (int i = x_HUMAN, j = y_HUMAN; i >= 0 && j >=0; i--, j--) {    // влево вверх
            if (field[i][j] == dot) {
                count++;
                iEnd = i;
                jEnd = j;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if ((iEnd - 1 >= 0) && (jEnd - 1 >=  0) && field[iEnd - 1][jEnd - 1] == '*') {
                return new int[] {iEnd - 1, jEnd - 1};
            }
            if ((x_HUMAN + 1 < fieldSizeX  && y_HUMAN + 1 < fieldSizeY) && isCellEmpty(x_HUMAN + 1, y_HUMAN + 1)) {
                return new int[]{x_HUMAN + 1, y_HUMAN + 1};
            }
        }


        count = 0;
        for (int i = x_HUMAN, j = y_HUMAN; i >=0 && j < fieldSizeY; i--, j++) {    // влево вниз
            if (field[i][j] == dot) {
                count++;
                iEnd = i;
                jEnd = j;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if ((iEnd - 1 >= 0) && (jEnd + 1 < fieldSizeY) && field[iEnd - 1][jEnd + 1] == '*') {
                return new int[] {iEnd - 1, jEnd + 1};
            }
            if ((x_HUMAN + 1 < fieldSizeX  && y_HUMAN - 1 >= 0)  && isCellEmpty(x_HUMAN + 1,y_HUMAN - 1)) {
                return new int[]{x_HUMAN + 1, y_HUMAN - 1};
            }
        }

        count = 0;
        for (int i = x_HUMAN, j = y_HUMAN; i < fieldSizeX && j < fieldSizeY; i++, j++) {  //вправо вниз
            if (field[i][j] == dot) {
                count++;
                iEnd = i;
                jEnd = j;
            } else {
                break;
            }
        }
        if (count >= winCount) {
            if ((iEnd + 1 < fieldSizeX) && (jEnd + 1 <  fieldSizeY) && isCellEmpty(iEnd + 1, jEnd + 1)) {
                return new int[] {iEnd + 1, jEnd + 1};
            }
            if ((x_HUMAN - 1 >= 0) && (y_HUMAN - 1 >=  0) && isCellEmpty(x_HUMAN - 1, y_HUMAN - 1)) {
                return new int[] {x_HUMAN - 1, y_HUMAN - 1};
            }
        }
        return new int[]{-1, -1};
    }




    static boolean check2(int x, int y, char dot, int win){
        return false;
    }

    static boolean check3(int x, int y, char dot, int win){
        return false;
    }

    static boolean check4(int x, int y, char dot, int win){
        return false;
    }

    /**
     * Проверка состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return
     */
    static boolean checkState(char dot, String s){
        if (checkWin(dot, WIN_COUNT)){
            System.out.println(s);
            return true;
        }
        else if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

}