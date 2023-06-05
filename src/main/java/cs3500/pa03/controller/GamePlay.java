package cs3500.pa03.controller;

import cs3500.pa03.model.Ai;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Human;
import cs3500.pa03.model.OpponentBoard;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import cs3500.pa03.view.Display;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the user's input and take charge of the major gameplay process
 *
 */
public class GamePlay {
  // Human player
  private Player human;
  // AI Player
  private Player computer;
  // User's board
  private UserBoard userBoard;
  // AI's board
  private OpponentBoard aiBoard;
  // height of the board
  private int height;
  // width of the board
  private int width;
  // specifications of the feet
  private Map<ShipType, Integer> fleet = new HashMap<>();
  // shots from the players
  private List<Coord> playerShots = new ArrayList<>();
  // shots from the AI
  private List<Coord> aiShots = new ArrayList<>();
  // Display
  private Display display = new Display();

  /**
   * Display the prompt to the user and retrieve the size of the board from the user's input
   *
   * @param scanner to scan user's input
   */
  public void getBoardSize(Scanner scanner) {
    display.showPrompt("Hello! Welcome to the OOD BattleSalvo Game!");
    display.showPrompt("Please enter a valid height and width below:");
    height = scanner.nextInt();
    width = scanner.nextInt();
    while ((height < 6 || height > 15) || (width < 6 || width > 15)) {
      display.showPrompt(
          "Uh Oh! You've entered invalid dimensions. Please remember that the height "
              + "and width\n"
              + "of the game must be in the range (6, 15), inclusive. Try again!");
      height = scanner.nextInt();
      width = scanner.nextInt();
    }
  }

  /**
   * Display the prompt to the user and retrieve the fleet specifications from the user's input
   *
   * @param scanner to scan user's input
   */
  public void getFleet(Scanner scanner) {
    int maxSize = Math.min(height, width);
    display.showPrompt("Please enter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine], you need at least one for each type.\n"
        + "Remember, your fleet may not exceed size " + maxSize + ".");
    fleet.put(ShipType.CARRIER, scanner.nextInt());
    fleet.put(ShipType.BATTLESHIP, scanner.nextInt());
    fleet.put(ShipType.DESTROYER, scanner.nextInt());
    fleet.put(ShipType.SUBMARINE, scanner.nextInt());
    int numOfFleet = 0;
    for (int occurrences : fleet.values()) {
      numOfFleet += occurrences;
    }
    while (numOfFleet > maxSize || fleet.get(ShipType.CARRIER) <= 0
        || fleet.get(ShipType.BATTLESHIP) <= 0 || fleet.get(ShipType.DESTROYER) <= 0
        || fleet.get(ShipType.SUBMARINE) <= 0) {
      fleet.clear();
      display.showPrompt(
          "Uh Oh! You've entered invalid fleet sizes.\n"
              + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
              + " Submarine].\n"
              + "Remember, your fleet may not exceed size 6, and you need one for each.");
      fleet.put(ShipType.CARRIER, scanner.nextInt());
      fleet.put(ShipType.BATTLESHIP, scanner.nextInt());
      fleet.put(ShipType.DESTROYER, scanner.nextInt());
      fleet.put(ShipType.SUBMARINE, scanner.nextInt());
      numOfFleet = 0;
      for (int occurrences : fleet.values()) {
        numOfFleet += occurrences;
      }
    }
  }

  /**
   * Display the prompt to the user and retrieve the shots from the user's input
   *
   * @param numOfShots number of shots the player can enter
   * @return list of Coord representing each shot
   */
  public List<Coord> getShots(int numOfShots) {
    Scanner scanner = new Scanner(System.in);
    display.showPrompt("Please enter " + numOfShots + " shots.");
    int i = 0;
    while (i < numOfShots) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      playerShots.add(new Coord(x, y));
      if ((x >= height || x < 0) || (y >= width || y < 0)) {
        playerShots.clear();
        display.showPrompt("Invalid shot coordinates. Please try again.");
        i = 0;
        continue;
      }
      i++;
    }
    return playerShots;
  }

  /**
   * Set up the user's board
   *
   * @param board the user's board
   */
  public void setUserBoard(UserBoard board) {
    this.userBoard = board;
  }

  /**
   * Set up the AI's board
   *
   * @param board the AI's board
   */
  public void setAiBoard(OpponentBoard board) {
    this.aiBoard = board;
  }

  /**
   * End the game and display the result to the user
   *
   * @param aiBoard the AI's board
   * @param userBoard the user's board
   */
  public void gameEnd(Board aiBoard, Board userBoard) {
    if (aiBoard.allSunk() && !userBoard.allSunk()) {
      display.showPrompt("Player won!");
      human.endGame(GameResult.WON, "All of Opponent's ships are sunk!");
      computer.endGame(GameResult.LOST, "All of your ships are sunk!");
    }
    if (userBoard.allSunk() && !aiBoard.allSunk()) {
      display.showPrompt("AI won!");
      computer.endGame(GameResult.WON, "All of Opponent's ships are sunk!");
      human.endGame(GameResult.LOST, "All of your ships are sunk!");
    }
    if (userBoard.allSunk() && aiBoard.allSunk()) {
      display.showPrompt("Draw!");
      human.endGame(GameResult.DRAW, "Draw!");
      computer.endGame(GameResult.DRAW, "Draw!");
    }
  }

  /**
   * The major playing process of the game
   *
   */
  public void gameLoop() {
    while (!userBoard.allSunk() && !aiBoard.allSunk()) {
      aiShots = computer.takeShots();
      playerShots = human.takeShots();
      display.showPrompt("Opponent Board Data:");
      aiBoard.updateMissed(playerShots);
      List<Coord> aiDamaged = computer.reportDamage(playerShots);
      human.successfulHits(aiDamaged);
      aiBoard.updateHit(aiDamaged);
      display.displayBoard(aiBoard);
      display.showPrompt("Your Board Data:");
      userBoard.updateMissed(aiShots);
      List<Coord> playerDamaged = human.reportDamage(aiShots);
      computer.successfulHits(playerDamaged);
      userBoard.updateHit(playerDamaged);
      display.displayBoard(userBoard);
      if (userBoard.allSunk() || aiBoard.allSunk()) {
        break;
      }
    }
  }

  /**
   * The setup and start of the game
   *
   * @param scanner to scan user's input
   */
  public void startGame(Scanner scanner) {
    getBoardSize(scanner);
    human = new Human(this);
    computer = new Ai(this);
    getFleet(scanner);
    display.showPrompt("Opponent Board Data:");
    computer.setup(height, width, fleet);
    display.displayBoard(aiBoard);
    display.showPrompt("Your Board Data:");
    human.setup(height, width, fleet);
    display.displayBoard(userBoard);
    gameLoop();
    gameEnd(aiBoard, userBoard);
  }
}
