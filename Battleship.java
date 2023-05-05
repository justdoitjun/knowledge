package newHW03;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Battleship {
    // main method
    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        int player = 0;
        int[][] player1ResultHistory = new int[5][2];       // coordinates you saved.
        int[][] player2ResultHistory = new int[5][2];
        Scanner sc = new Scanner(System.in);
        // Initial coordinates designation (player1 & player2)
        do {
            player ++; //first do loop
            System.out.printf("PLAYER %d, ENTER YOUR SHIPS’ COORDINATES.", player);
            int[][] resultHistory = new int[5][2];
            int input1;
            int input2;
            //Num A
            int i = 1;//count number
            do {        // Each player location designation.
                System.out.printf("Enter ship %d location\n", i);
                input1 = sc.nextInt();
                input2 = sc.nextInt();
                // input Check - checkInteger method.
                boolean checkInteger = checkInteger(input1, input2, resultHistory);
                if(checkInteger==true) {
                    // If input Check has passed, you can save each player's data into 'resultHistory'.
                    resultHistory[(i - 1)][0] = input1;
                    resultHistory[(i - 1)][1] = input2;
                    i++;
                }
            } while (i < 6);
            //display
            printBattleShip(printMap(player, resultHistory));
            //save each opponent's coordinates for preparing duel.
            if(player == 1){
                player1ResultHistory = resultHistory; //save Coordinates data for preparing duel.
            }else if(player ==2){
                player2ResultHistory = resultHistory; //save Coordinates data for preparing duel.
            }
        }while(player<2);
        sc.close();

        duel(player1ResultHistory, player2ResultHistory);
    }
    //duel

    public static void duel(int[][] player1ResultHistory, int[][] player2ResultHistory){
        Scanner sc = new Scanner(System.in);
        //////////////////// duel
        boolean destroyed = false; // If it went "true", a ship of partner is destroyed. -> display"O" sign on map.
        int cnt1 = 1;//count number
        int cnt2 = 1;//count number
        int[] aSingleShot = new int[2];
        int[][] player1ShotHistory = new int[5][2];       // coordinates you saved.
        int[][] player2ShotHistory = new int[5][2];
        char[][] player1duelHistory = new char[5][5];
        player1duelHistory = initializeBattleship(player1duelHistory);
        char[][] player2duelHistory = new char[5][5];
        player2duelHistory = initializeBattleship(player2duelHistory);
        //////duel Start******************
        int duelPlayer = 1; // duelPlayer 1 means player1 is choosing a shot.
        for(int cnt = 1 ;      ; cnt++){
            System.out.printf("Player %d, enter hit row/column:\n", duelPlayer);
            int duelInput1 = sc.nextInt();
            int duelInput2 = sc.nextInt();
            boolean checkDuelInteger = false;
            //duel check Integer
            if(duelPlayer == 1) {
                checkDuelInteger = checkDuelInteger(duelInput1, duelInput2, player2ShotHistory);
                //겹치는지 확인
                if(checkDuelInteger == true) {
                    player1ShotHistory = saveDuelResult(duelInput1, duelInput2, cnt, player1ShotHistory);
                    aSingleShot[0] = duelInput1;
                    aSingleShot[1] = duelInput2;
                    //실제로 쏜 화살이 맞았는지 확인해보는 것.
                    if(duelFireShots(duelPlayer, aSingleShot, player2ResultHistory)==true){
                        System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer+1));
                        player1duelHistory[duelInput1][duelInput2] = 'X';
                        printBattleShip(player1duelHistory);
                    }else{
                        System.out.println("안맞았음");
                        player1duelHistory[duelInput1][duelInput2] = 'O';
                        printBattleShip(player1duelHistory);
                    }
                }
                duelPlayer++;
            }
            if(winCheck(player1duelHistory, player2ResultHistory)==true){
                return;
            }
            else if(duelPlayer ==2){
                checkDuelInteger = checkDuelInteger(duelInput1, duelInput2, player1ShotHistory);
                //겹치는지 확인
                if(checkDuelInteger == true) {
                    player2ShotHistory = saveDuelResult(duelInput1, duelInput2, cnt, player2ShotHistory);
                    aSingleShot[0] = duelInput1;
                    aSingleShot[1] = duelInput2;
                    //실제로 쏜 화살이 맞았는지 확인해보는 것.
                    if(duelFireShots(duelPlayer, aSingleShot, player2ResultHistory)==true){
                        System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer-1));
                        player2duelHistory[duelInput1][duelInput2] = 'X';
                        printBattleShip(player2duelHistory);
                        //게임이 끝났는지 안 끝났는지를 확인
                    }else{
                        System.out.println("안맞았음");
                        player2duelHistory[duelInput1][duelInput2] = 'O';
                        printBattleShip(player2duelHistory);
                    }
                }
                duelPlayer--;
            }
            if(winCheck(player2duelHistory, player1ResultHistory)==true){
                return;
            }
        }
    }



    private static boolean winCheck(char[][] player1DuelHistory, int[][] player2ResultHistory){
        int winCount = 0;
        for(int[] e : player2ResultHistory){
            if(Objects.equals(player1DuelHistory[e[0]][e[1]], "X")){
                winCount++;
            }
        }
        if(winCount==5){
            return true;
        }else{
            return false;
        }
    }


    //=================================================================================

    //(1) input Error check : whether It is invalid coordinates or have already putted on same spot.
    public static boolean checkInteger(int input1, int input2, int[][] resultHistory){
        boolean error = true;
        int[] testInput = new int[2];
        testInput[0] = input1;
        testInput[1] = input2;
        if(input1 <0 || input1 >=5 || input2 <0 || input2 >=5){
            System.out.println("Invalid coordinates. Choose different coordinates.");
            error = false;
        }
        for(int[] el : resultHistory){
            if(Arrays.equals(el, testInput) && el[0] != 0 && el[1] != 0){
                // the reason why I check el's components : because I want to allow [0,0]
                System.out.println("You already have a ship there. Choose different coordinates.");
                error = false;
            }
        }
        return error;
    };

    //(2) input Error check : whether It is invalid coordinates or have already putted on same spot.
    public static boolean checkDuelInteger(int input1, int input2, int[][] resultHistory){
        boolean error = true;
        int[] testInput = new int[2];
        testInput[0] = input1;
        testInput[1] = input2;
        if(input1 <0 || input1 >=5 || input2 <0 || input2 >=5){
            System.out.println("Invalid coordinates. Choose different coordinates.");
            error = false;
        }
        for(int[] el : resultHistory){
            if(Arrays.equals(el, testInput) && el[0] != 0 && el[1] != 0){
                // the reason why I check el's components : because I want to allow [0,0]
                System.out.println("You already fired on this spot. Choose different coordinates.");
                error = false;
            }
        }
        return error;
    };

    private static int[][] saveDuelResult(int duelInput1, int duelInput2, int cnt, int[][] playerShotHistory){
        playerShotHistory[cnt][0] = duelInput1;
        playerShotHistory[cnt][1] = duelInput2;
        return playerShotHistory;
    }

    //(2) To give char Array to printBattleShop methods
    private static char[][] printMap(int player, int[][] resultHistory){
        char[][] playerArray = new char[5][5];
        for(int num1=0; num1<5;num1++){
            for(int num2=0; num2<5; num2 ++){
                playerArray[num1][num2] = '-';
            }
        }
        for(int k=0; k<5 ; k++){
            playerArray[resultHistory[k][0]][resultHistory[k][1]] ='@';
        }
        return playerArray;
    }

    //(3) shots Fired - when you shoot a shot, it returns whether you hit opponent's ship or not.
    public static boolean duelFireShots(int player, int[] aSingleShot, int[][] opponentsCoordinates){
        for(int[] el : opponentsCoordinates){
            if(Arrays.equals(el, aSingleShot)){
                return true;
            }
        }
        return false; // default : false - which means a shot doesn't hit the right coordinates.
    };

    //(4) In the middle of duel, Use this method to print game boards to the console.
    // You are able to see whole map each player's impacted ground-zero("O") and destroyed ships("X")
    // That is why printBattleShip get argument as 'char Array'.
    private static void printBattleShip(char[][] player) {
        System.out.print("  "); // First row, should be blank.
        for (int row = -1; row < 5; row++) {
            if (row > -1) {
                System.out.print(row + " "); //From Second row to 5th row, draw row number.
            }
            for (int column = 0; column < 5; column++) {
                if (row == -1) {
                    System.out.print(column + " "); // FirstRow - column name
                } else {
                    System.out.print(player[row][column] + " "); // From the second row, you can put coordinates of players.
                }
            }
            System.out.println("");
        }
    }
    //(5) initialize Arrays into '-'.
    private static char[][] initializeBattleship(char[][] players) {
        for (int i = 0; i < players.length ; i++){
            Arrays.fill(players[i], '-');
        }
        return players;
    }

}
