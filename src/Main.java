

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {


    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int WIN_COUNT = 3;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;
    private static int countTurns = 0;

    public static final String RESET = "\u001B[0m";
    public static final String BLUE_BACKGROUND = "\u001B[30;44m";
    public static final String WHITE_BACKGROUND = "\033[30;107m";



    public static void main(String[] args) {
        while (true){
            initialize(3,6);
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
    static void initialize(int fieldSizeX, int fieldSizeY){
//        fieldSizeX = 3;
//        fieldSizeY = 6;
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
    static void aiTurn(){
        int x;
        int y;





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
    static boolean checkWin(char dot){

        // Проверка победы по вертикалям
        int count = 0;
        int countMax = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            countMax = 0;
            count = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == dot) {
                    count++;
                    if (count > countMax) countMax = count;
                } else {
                    count = 0;
                }
            }
        }
        if (countMax >= WIN_COUNT) return true;

        // Проверка победы по горизонтали
        for (int j = 0; j < fieldSizeY; j++) {
            countMax = 0;
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
        for (int k = -fieldSizeX - 1; k < fieldSizeY + fieldSizeX - 4; k++) {
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
//            System.out.println();
        }
        if (countMax >= WIN_COUNT) return true;

        return false;
    }


    static boolean check1(int x, int y, char dot, int win){
        return false;
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
        if (checkWin(dot)){
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