package newHW03;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/// <제출 전 공부하기 위해서>
//// (1) 자바의 배열은 참조값 형태의 경우 null로 초기화 되고, primitive type 의 경우 숫자는 0으로 초기세팅되어 생성된다. 
///////왜 (0, 0)을 넣으면 중복되었다는 에러가 나올까요?
/// 참고로 기본타입(Primitive type)의 배열인 경우 초기값을 가지고 있는 반면에(int = 0)
// 참조타입(Reference type)의 배열을 선언했을 경우 배열내 엘리먼트의 초기값이 null임을 주의하셔야 합니다.
// 그래서 이에 대한 해결방법으로 우리는 논리적인 초기값인 7을 넣어서 모든 배열을 7로 초기화해주었습니다. 왜냐면, (0,0)은 실제 데이터값인데
// 모든 배열이 0으로 초기화가 되어버리면 문제가 생기기 때문이죠. 대신에 0~4 가 아닌 완전히 다른 수 7로 초기화하면 이런 문제가 생기지 않습니다.
// 다시 정리하자면, 자바에서 배열은 참조값 배열 (String)은 null로 초기화되지만, 기본타입은 숫자의 경우 0으로 초기화된다는 것을 반드시 기억해. 
// 아무 생각없이 생성하면 다 0으로 나와. 

//.. (2) 문법 배운 점 Arrays.fill
//자바의 배열은 매우 중요한데 --> 배열의 각 원소를 건드리려면(찾든지 아니면 뭐 조작을 하든지) for 문을 돌려서 해줘야한다. 

//            for(int[] e : resultHistory){           // Initialize
//                Arrays.fill(e , 7);
//            }
//.....(3) 비슷한 맥락에서 자바의 배열을 비교하려면 단순하게 equals로 비교할 수가 없다는 점도 기억하자. 
//처음에 고생했던 건   [1,2].equals([1,2]) 물론 예시다. 저 코드 자체가 말이 안되지만, 둘이 저렇게만 비교하면 당연히 될 줄 알았음. 
// 왜냐면, 어차피 객체(Object) 배열이 애초에 참조변수니깐...근데 Arrays나 hashmap같은 건 Object.equals( 비교대상1, 비교대상2)
// 이렇게 비교해줘야했음. 이걸로 3시간 애먹었는데 그냥 바로 구글링할 걸 그랬다. 
//        for(int[] el : resultHistory){    
//            if(Arrays.equals(el, testInput)){
//                System.out.println("You already fired on this spot. Choose different coordinates.");
//                error = false;
//            }
//        }


//(3) 아쉬운 점 
// 일단 지금 코드가 너무 길다. 클래스별로 기능을 구현하여 쪼개긴 햇지만, player1과 2가 바뀌는 건 굳이 저렇게 하나하나 코드를 짤게 아니라, 
// 자동으로 1번 player가 인풋을 마쳣으면 player2로 넘어가는 것을 해주면 좋았겠지만, 아무리 생각해봐도 duelPlayer라는 변수 하나 설정해놓고
//duelPlayer ++ duelPlayer --로 원시적으로 반복문 if 문 쓰는 거말고는 생각이 안났다. 이건 좀 아쉽긴 하다. 
// 


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
            for(int[] e : resultHistory){           // Initialize
                Arrays.fill(e , 7);
            }
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
        
        boolean destroyed = false; // If it went "true", a ship of partner is destroyed. -> display"O" sign on map.
        int cnt1 = 1;//count number
        int cnt2 = 1;//c0
        // ount number
        int[] aSingleShot = new int[2];
        int[][] player1ShotHistory = new int[25][2];       // coordinates you saved.
        for(int[] e : player1ShotHistory){           //  Initialize
            Arrays.fill(e , 7);
        }
        int[][] player2ShotHistory = new int[25][2];       // As it has 25 cases,
        for(int[] e : player2ShotHistory){           // Initialize 
            Arrays.fill(e , 7);
        }
        char[][] player1duelHistory = new char[5][5];
        player1duelHistory = initializeBattleship(player1duelHistory);
        char[][] player2duelHistory = new char[5][5];
        player2duelHistory = initializeBattleship(player2duelHistory);
        //////duel Start******************
        int duelPlayer = 1; // duelPlayer 1 means player1 is choosing a shot.
        for(int cnt = 1 ;      ; cnt++){

            boolean checkDuelInteger = false;
            //duel check Integer
            if(duelPlayer == 1) {
                System.out.printf("Player %d, enter hit row/column:\n", duelPlayer);
                int duelInput1 = sc.nextInt();
                int duelInput2 = sc.nextInt();
                checkDuelInteger = checkDuelInteger(duelInput1, duelInput2, player1ShotHistory);
                // Check whether input is good or bad. - whether it is wrong number or duplicated. 
                if(checkDuelInteger == true) {
                    player1ShotHistory = saveDuelResult(duelInput1, duelInput2, cnt, player1ShotHistory);
                    aSingleShot[0] = duelInput1;
                    aSingleShot[1] = duelInput2;
                    // Check whether a shot hit the right spot. 
                    if(duelFireShots(duelPlayer, aSingleShot, player2ResultHistory)==true){
                        System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer+1));
                        player1duelHistory[duelInput1][duelInput2] = 'X';
                        printBattleShip(player1duelHistory);
                    }else{
                        System.out.printf("PLAYER %d MISSED!", duelPlayer);
                        player1duelHistory[duelInput1][duelInput2] = 'O';
                        printBattleShip(player1duelHistory);
                    }
                }
                duelPlayer++;
            }
            if(winCheck(player1duelHistory, player2ResultHistory)==true){
                System.out.printf("PLAYER %d WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!", (duelPlayer-1));
                return;
            }

            else if(duelPlayer ==2){
                System.out.printf("Player %d, enter hit row/column:\n", duelPlayer);
                int duelInput1 = sc.nextInt();
                int duelInput2 = sc.nextInt();
                checkDuelInteger = checkDuelInteger(duelInput1, duelInput2, player2ShotHistory);
                //  Check whether your input is good or bad. Whether you put wrong number or duplicated number. 
                if(checkDuelInteger == true) {
                    player2ShotHistory = saveDuelResult(duelInput1, duelInput2, cnt, player2ShotHistory);
                    aSingleShot[0] = duelInput1;
                    aSingleShot[1] = duelInput2;
                    // Check whether shot hit the right spot 
                    if(duelFireShots(duelPlayer, aSingleShot, player1ResultHistory)==true){
                        System.out.printf("PLAYER %d HIT PLAYER %d's SHIP!\n", duelPlayer, (duelPlayer-1));
                        player2duelHistory[duelInput1][duelInput2] = 'X';
                        printBattleShip(player2duelHistory);
                    }else{
                        System.out.printf("PLAYER %d MISSED!", duelPlayer);
                        player2duelHistory[duelInput1][duelInput2] = 'O';
                        printBattleShip(player2duelHistory);
                    }
                }
                duelPlayer--;
            }
            if(winCheck(player2duelHistory, player1ResultHistory)==true){
                System.out.printf("PLAYER %d WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!", (duelPlayer+1));
                return;
            }
        }
    }




    //=================================================================================
    ////.....input
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
            if(Arrays.equals(el, testInput)){
                // the reason why I check el's components : because I want to allow [0,0]
                System.out.println("You already have a ship there. Choose different coordinates.");
                error = false;
            }
        }
        return error;
    };
    //////....duel
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
            if(Arrays.equals(el, testInput)){
                System.out.println("You already fired on this spot. Choose different coordinates.");
                error = false;
            }
        }
        return error;
    };
    /////----duel
    ////(3)
    private static int[][] saveDuelResult(int duelInput1, int duelInput2, int cnt, int[][] playerShotHistory){
        playerShotHistory[cnt][0] = duelInput1;
        playerShotHistory[cnt][1] = duelInput2;
        return playerShotHistory;
    }
    ///......general
    //(4) To give char Array to printBattleShop methods
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
    ////---duel
    //(5) shots Fired - when you shoot a shot, it returns whether you hit opponent's ship or not.
    public static boolean duelFireShots(int player, int[] aSingleShot, int[][] opponentsCoordinates){
        for(int[] el : opponentsCoordinates){
            if(Arrays.equals(el, aSingleShot)){
                return true;
            }
        }
        return false; // default : false - which means a shot doesn't hit the right coordinates.
    };

    //(6) In the middle of duel, Use this method to print game boards to the console.
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
    //(7) initialize Arrays into '-'.
    private static char[][] initializeBattleship(char[][] players) {
        for (int i = 0; i < players.length ; i++){
            Arrays.fill(players[i], '-');
        }
        return players;
    }

    /////(8).....check whether players win this game or not. 
    private static boolean winCheck(char[][] player1DuelHistory, int[][] player2ResultHistory){
        int winCount = 0;
        for(int[] e : player2ResultHistory){
            if(Objects.equals(player1DuelHistory[e[0]][e[1]], 'X')){
                winCount++;
            }
        }
        if(winCount==5){
            return true;
        }else{
            return false;
        }
    }

}
