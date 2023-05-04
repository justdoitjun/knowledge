package newHW03;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BattleShip {

    public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        HashMap<Integer, Object > resultSave = new HashMap<>();
        int player = 0;
        int[][] player1resultHistory = new int[5][2];
        int[][] player2resultHistory = new int[5][2];
        Scanner sc = new Scanner(System.in);
        // Initial coordinates designation (player1 & player2)
        do {
            player++;
            System.out.printf("PLAYER %d, ENTER YOUR SHIPS’ COORDINATES.", player);
            //String resultHistory = ""; //reusltHistory is String type variance.
            int[][] resultHistory = new int[5][2];
            int input1 = 0;
            int input2 = 0;
            //Num A
            int i = 1;//count number
            do {        // Each player location designation.
                System.out.printf("Enter ship %d location\n", i);
                input1 = sc.nextInt();
                input2 = sc.nextInt();
                // input Check - checkInteger method.
                if (checkInteger(input1, input2, resultHistory).equals("E001")) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                    i += 0;
                }
                if (checkInteger(input1, input2, resultHistory).equals("E002")) {
                    System.out.println("You already have a ship there. Choose different coordinates.");
                    i += 0;
                }
                // If input Check has passed, you can save each player's data into 'resultHistory'.
                else {
                    resultHistory[(i-1)][0] = input1;
                    resultHistory[(i-1)][1] = input2;
                    i++;
                }
            } while (i < 6);

            //diplay
            printBattleShip(printMap(player, resultHistory));
            //save each opponent's cooridnates for preparing duel.
            if(player == 1){
               player1resultHistory = resultHistory; //save Coordinates data for preapring duel.
            }else if(player ==2){
                player2resultHistory = resultHistory; //save Coordinates data for preparing duel.
            }
        }while(player<2);

        // duel
        boolean destroyed = false; // If it went "true", a ship of partner is destroyed. -> display"O" sign on map.
        boolean winResult = false;
        int[][] player1ResultHistory = new int[5][2];
        int[][] player2ResultHistory = new int[5][2];
        int cnt1 = 1;//count number
        int cnt2 = 1;//count number
        int winStack1 = 0;  // Plyaer 1 has to get 5 points to win this game.
        int winStack2 = 0;

        char[][] player1duelHistory = new char[5][5];
        char[][] player2duelHistory = new char[5][5];
        int duelPlayer = 1; // duelPlayer 1 means player1 is choosing a shot.
        do{
            System.out.printf("Player %d, enter hit row/column:\n", duelPlayer);
            int duelInput1 = sc.nextInt();
            int duelInput2 = sc.nextInt();
            //duel check Integer
            if (checkInteger(duelInput1, duelInput2, player1ResultHistory).equals("E001")) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
            }
            if (checkInteger(duelInput1, duelInput2, player2ResultHistory).equals("E002")) {
                System.out.println("You already fired on this spot. Choose different coordinates.");
            }
            // If input Check has passed, you can save each player's data into 'resultHistory'.
            else {
                if(duelPlayer == 1){
                    player1ResultHistory[(cnt1-1)][0] = duelInput1;
                    player1ResultHistory[(cnt1-1)][1] = duelInput2;
                    cnt1++;
                }
                if(duelPlayer ==2) {
                    player2ResultHistory[(cnt2 - 1)][0] = duelInput1;
                    player2ResultHistory[(cnt2 - 1)][1] = duelInput2;
                    cnt2++;
                }
            }
            int[] aSingleShot = new int[2];
            aSingleShot[0] = duelInput1;
            aSingleShot[1] = duelInput2;
            //System.out.println(aSingleShot);
            if(duelPlayer == 1){
                destroyed = duelFireShots(duelPlayer, aSingleShot, player2resultHistory); // a shot fired!!!
                System.out.println(destroyed);
                if(destroyed==true){
                    System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer+1));
                    player1duelHistory[duelInput1][duelInput2] = 'X';
                    printBattleShip(player1duelHistory);
                    winStack1 ++;
                    if(winStack1 == 5){
                        System.out.printf("PLAYER %d WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!\n", duelPlayer);
                        winResult = true;
                    }
                }else{
                    System.out.printf("PLAYER %d MISSED!", duelPlayer);
                    player1duelHistory[duelInput1][duelInput2] = 'O';
                    printBattleShip(player1duelHistory);
                }
                duelPlayer++;
            }else if(duelPlayer == 2){
                destroyed = duelFireShots(duelPlayer, aSingleShot, player1resultHistory); // a shot fired!!!
                System.out.println(destroyed);
                if(destroyed==true){
                    System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer+1));
                    player2duelHistory[duelInput1][duelInput2] = 'X';
                    printBattleShip(player2duelHistory);
                    winStack2 ++;
                    if(winStack2 == 5){
                        System.out.printf("PLAYER %d WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!\n", duelPlayer);
                        winResult = true;
                    }
                }else{
                    System.out.printf("PLAYER %d MISSED!", duelPlayer);
                    player2duelHistory[duelInput1][duelInput2] = 'O';
                    printBattleShip(player2duelHistory);
                }

                duelPlayer--;
            }
        }while(winResult == false);
    }




    public static boolean duelFireShots(int player, int[] aSingleShot, int[][] opponentsCoordinates){
        for(int[] el : opponentsCoordinates){
            if(Arrays.equals(el, aSingleShot)){
                return true;
            }
        }
        return false; // default : false - which means a shot doesn't hit the right coordinates.
    };


    //(1) input Error check : whether It is invalid coordinates or have already putted on same spot.
    public static String checkInteger(int input1, int input2, int[][] resultHistory){
        String Error = "";
        int[] testInput = new int[2];
        testInput[0] = input1;
        testInput[1] = input2;
        if(input1 <0 || input1 >=5 || input2 <0 || input2 >=5){
            Error = "E001";//Invalid coordinates. Choose different coordinates.
        }
        for(int[] el : resultHistory){
            if(Arrays.equals(el, testInput) && el[0] != 0 && el[1] != 0){
             // the reason why I check el's components is because I want to allow [0,0]
                Error = "E002"; //"You already have a ship there. Choose different coordinates."
            }
        }
        return Error;
    };


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

    //(3) In the middle of duel, Use this method to print game boards to the console.
    // You are able to see whose ship is hit.
    private static void printBattleShip(char[][] player) {
        System.out.print("  "); //첫줄은 일단 한 칸을 비우자.
        for (int row = -1; row < 5; row++) {
            if (row > -1) {
                System.out.print(row + " "); //두번째줄부터 5번째 줄까지는 row 숫자를 적어주자.
            }
            for (int column = 0; column < 5; column++) {
                if (row == -1) {
                    System.out.print(column + " "); //첫줄은 column 만 적어주자.
                } else {
                    System.out.print(player[row][column] + " "); // 두번재 줄부터 이미 불러온 player 좌표 모양을 박아주자.
                }
            }
            System.out.println("");
        }
    }



}
