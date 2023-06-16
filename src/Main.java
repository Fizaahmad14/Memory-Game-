//necessary imports
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
    	//Displaying the welcome message 
		System.out.println(" <><><><><><><><><><><><><><><><><><><><>");
		System.out.println(" <>                                    <>");
		System.out.println(" <>      WELCOME TO MEMORY GAME        <>");
		System.out.println(" <>                                    <>");
		System.out.println(" <><><><><><><><><><><><><><><><><><><><>");
		instructions();
        int[][] board = new int[4][4];
        char[][] solution = new char[4][4];        
        boolean[][] revealed = new boolean[4][4];

        // Initialize the board with numbers 1-16
        int count = 1;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                board[i][j] = count++;
            }
        }

        // Initialize solution with random symbols
        Random rand = new Random();
        char[] symbols = {'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F', 'G', 'G', 'H', 'H'};
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                int randIndex = rand.nextInt(16 - (i * 4 + j));
                solution[i][j] = symbols[randIndex];
                symbols[randIndex] = symbols[15 - (i * 4 + j)];
            }
        }
       
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
            	System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
        
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        int score = 0;
        
        //the while will continue until all cards have been revealed
        //and 20 attempts have been completed
        while (!isComplete(revealed) && attempts < 20) {
        	
        	//incrementing attempts
        	attempts++;
            System.out.println("\n*-----------------Attempt no.  " + attempts);
            System.out.println("Enter card numbers (1-16) you want to flip");  
            //calling printBoard to print current situation of game
            printBoard(board, solution, revealed);
            //taking input for card 1 and validating it 
            int firstCard = getValidCard(scanner, "--> First card : ", revealed);
            //revealing the card to see the symbol
            revealCard(firstCard, solution, revealed);
            //calling printBoard to print current situation of game
            printBoard(board, solution, revealed);

            //taking input for card 1 and validating it 
            int secondCard = getValidCard(scanner, "--> Second card : ", revealed);
            //revealing the card to see the symbol
            revealCard(secondCard, solution, revealed);
            //calling printBoard to print current situation of game
            printBoard(board, solution, revealed);

            //Comparing the user entered cards with the solution and displaying messages accordingly
            if (solution[getI(firstCard)][getJ(firstCard)] != solution[getI(secondCard)][getJ(secondCard)]) {
                System.out.println("\n!!Match not found!!\nFlipping the cards back over...");
                hideCard(firstCard, revealed);
                hideCard(secondCard, revealed);
            }
            else {
            	System.out.println("\n*** Match Found ***");
            	score++;
            }

            System.out.println("\n SCORE : "+score);
        }

        //checking conditions and displaying results accordingly
        if (isComplete(revealed)) {
        	System.out.println("\n----------------------------------");
            System.out.println("*** CONGRATULATIONS, YOU WIN :) ***");
            System.out.println("--> Total Attempts : "+attempts);
            System.out.println("--> Total Score    : "+score);
        } else {
        	System.out.println("\nx----x----x----x----x----x----GAME OVER");
            System.out.println("!!  Ahh you lose, Better Luck next time !!");
            System.out.println("--> Total Attempts : "+attempts);
            System.out.println("--> Total Score    : "+score);
        }

        scanner.close();
    }

    //fuction to print the board
    private static void printBoard(int[][] board, char[][] solution, boolean[][] revealed) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(revealed[i][j]) {
                    System.out.print(solution[i][j] + " ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

    //validating the user input
    private static int getValidCard(Scanner scanner, String prompt, boolean[][] revealed) {
        while (true) {
            System.out.println(prompt);
            int input = scanner.nextInt();

            if (input < 1 || input > 16 || revealed[getI(input)][getJ(input)]) {
                System.out.println("\n!! Invalid card number or already revealed card !!\nPlease try again.\n");
            } else {
                return input;
            }
        }
    }

    //if match found then make reveal equals false so that the card will reveal
    private static void revealCard(int card, char[][] solution, boolean[][] revealed) {
        int i = getI(card);
        int j = getJ(card);
        revealed[i][j] = true;
    }

    //if no match found then make reveal equals false so that the card will hide
    private static void hideCard(int card, boolean[][] revealed) {
        int i = getI(card);
        int j = getJ(card);
        revealed[i][j] = false;
    }

    //function to get the index value i according to the card the user have picked
    private static int getI(int card) {
        return (card - 1) / 4;
    }

    //function to get the index value  j according to the card the user have picked
    private static int getJ(int card) {
        return (card - 1) % 4;
    }

    //function to check if the board is all revealed or not
    private static boolean isComplete(boolean[][] revealed) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(!revealed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void instructions()
    {
    	System.out.println("Rules and Instructions for the Memory Game:\n");
    	System.out.println("1. Welcome: Welcome to the Memory Game! Your task is to match pairs of cards.");
    	System.out.println("2. Board Setup: The game is played on a 4x4 board,\n   making a total of 16 cards. Each card has a symbol from 'A' to 'H',\n   with each symbol appearing exactly twice.");
    	System.out.println("3. Gameplay: When the game starts, all cards are face down.\n   Your job is to flip over two hidden cards at a time to find the matching pairs.");
    	System.out.println("4. User Input: You will be prompted to select two cards on each turn.\n   Cards are numbered from 1 to 16. Enter the number of the card you wish to flip.");
    	System.out.println("5. Match Making: If the two cards match, they remain flipped over.\n   If they don't match, they are turned back over.");
    	System.out.println("6. Attempts: You have a maximum of 20 attempts to find all matching pairs.");
    	System.out.println("7. End of Game: The game ends when you have found all matching pairs or \n   when you have used up all of your attempts.");
    	System.out.println("8. Scoring: Your score is the number of matches you have made.");
    	System.out.println("9. Victory Condition: If you reveal all the pairs within 20 attempts, you win the game!");
    	System.out.println("10. Game Over: If you fail to reveal all pairs within 20 attempts, the game is over, \n   and you can try again.");
    	System.out.println("\nEnjoy the game!");

    }
}
