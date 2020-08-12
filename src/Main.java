import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	private static final String INTRO = "Hello! What is your name?\n";
	private static final String GAME_START = "\nWell, |name|, I am thinking of a number between 1 and 20.";
	private static final String PROMPT = "Take a guess.\n";
	private static final String TOO_LOW = "\nYour guess is too low.";
	private static final String TOO_HIGH = "\nYour guess is too high.";
	private static final String TOO_LOW_WRONG_WAY = "\nYour guess is too low! (You went the wrong way)";
	private static final String TOO_HIGH_WRONG_WAY = "\nYour guess is too high! (You went the wrong way)";
	private static final String WIN_MESSAGE = "\nGood job, |name|! You guessed my number in |guess| guesses!";
	private static final String WIN_FIRST_TRY = "\nGood job, |name|! You guessed my number first try!";
	private static final String LOSE_MESSAGE = "Sorry, you have ran out of guesses. The number was |number|.";
	private static final String PLAY_AGAIN_PROMPT = "Would you like to play again? (y or n)\n";

	/**
	 * The player's name they enter at the start of the game.
	 */
	private static String name = "";
	/**
	 * The number of guesses the player has made.
	 */
	private static int guesses = 0;
	/**
	 * The correct number the game will accept.
	 */
	private static int number = 0;
	/**
	 * The number the player has entered as a guess.
	 */
	private static int choice = 0;
	/**
	 * The last number the player entered as a guess. If it is 0, no previous guess was made.
	 */
	private static int previousChoice = 0;

	public static void main(String[] args) {
		showMessage(INTRO);
		name = grabName();
		boolean playing = true;
		while(playing) {
			generateNumber();
			guesses = 0;
			previousChoice = 0;
			showMessage(GAME_START);
			while(guesses < 6) {
				guesses++;
				showMessage(PROMPT);
				choice = getChoice();
				if(evaluateChoice()) {
					showMessage(guesses == 1 ? WIN_FIRST_TRY : WIN_MESSAGE);
					break;
				}
				if(guesses == 6)
					showMessage(LOSE_MESSAGE);
			}
			showMessage(PLAY_AGAIN_PROMPT);
			playing = playAgainChoice();
		}
	}

	/**
	 * Transforms a template message into text for the player.
	 *
	 * @param message Message template to display
	 */
	private static void showMessage(String message) {
		String filtered = message.replace("|name|", name)
				.replace("|guess|", String.valueOf(guesses))
				.replace("|number|", String.valueOf(number));
		System.out.println(filtered);
	}

	/**
	 * Sets {@link Main#number} to a random number between 1 and 20.
	 */
	private static void generateNumber() {
		var random = new Random();
		number = random.nextInt(20) + 1;
	}

	/**
	 * Requests the player to give their name.
	 *
	 * @return Non-empty name
	 */
	private static String grabName() {
		var scanner = new Scanner(System.in);
		while(true) {
			String choice = scanner.nextLine();
			if(!choice.equals(""))
				return choice;
			System.out.println("Please enter your name.\n");
		}
	}

	/**
	 * Requests the player to give a number between 1 and 20.
	 *
	 * @return Number between 1 and 20
	 */
	private static int getChoice() {
		var scanner = new Scanner(System.in);
		while(true) {
			try {
				int choice = scanner.nextInt();
				if(choice >= 1 && choice <= 20)
					return choice;
			}
			catch(InputMismatchException ignored) {
				scanner.next();
			}
			System.out.println("Please enter an integer between 1 and 20.\n");
		}
	}

	/**
	 * Turns in the choice and displays a message to the player.
	 *
	 * @return True if choice was correct, false otherwise.
	 */
	private static boolean evaluateChoice() {
		if(previousChoice == 0)
			previousChoice = choice;
		if(choice == number)
			return true;
		if(choice < number)
			showMessage(choice < previousChoice ? TOO_LOW_WRONG_WAY : TOO_LOW);
		else
			showMessage(choice > previousChoice ? TOO_HIGH_WRONG_WAY : TOO_HIGH);
		previousChoice = choice;
		return false;
	}

	/**
	 * Checks if the player wants to play again
	 *
	 * @return True if player is playing again, false otherwise.
	 */
	private static boolean playAgainChoice() {
		var scanner = new Scanner(System.in);
		while(true) {
			String choice = scanner.nextLine().toLowerCase();
			if(choice.equals("y") || choice.equals("yes"))
				return true;
			if(choice.equals("n") || choice.equals("no"))
				return false;
			System.out.println("Please enter 'y' or 'n' if you want to play again.\n");
		}
	}
}