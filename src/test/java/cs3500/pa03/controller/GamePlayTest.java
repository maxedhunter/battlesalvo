package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.OpponentBoard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for GamePlay
 */
class GamePlayTest {
  private GamePlay gp;
  private InputStream standardIn;
  private PrintStream standardOut;
  private ByteArrayOutputStream outputStream;

  /**
   * Initialized gp
   */
  @BeforeEach
  public void setup() {
//    gp = new GamePlay(); //TODO fix this constructor set up
  }

  /**
   * Tests the getBoardSize method
   */
  @Test
  public void testGetBoardSize() {
    String input = "5 5\n10 19\n10 5\n19 7\n6 6";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner = new Scanner(System.in);
    gp.getBoardSize(scanner);

    System.setIn(standardIn);
    System.setOut(standardOut);

    String output = outputStream.toString();
    assertEquals("Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width below:\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height "
        + "and width\n"
        + "of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height "
        + "and width\n"
        + "of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height "
        + "and width\n"
        + "of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "Uh Oh! You've entered invalid dimensions. Please remember that the height "
        + "and width\n"
        + "of the game must be in the range (6, 15), inclusive. Try again!\n", output);
  }

  /**
   * Tests the getFleet() method
   */
  @Test
  public void testGetFleet() {
    String input = "6 6";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    System.setIn(inputStream);

    Scanner scanner = new Scanner(System.in);
    gp.getBoardSize(scanner);

    System.setIn(standardIn);

    String input2 = "1 2 3 4\n1 2 3 0\n1 2 0 1\n1 0 2 1\n0 1 2 3\n1 1 1 1";
    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream2);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner2 = new Scanner(System.in);
    gp.getFleet(scanner2);

    System.setIn(standardIn);
    System.setOut(standardOut);

    String output = outputStream.toString();
    assertEquals("Please enter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine], you need at least one for each type.\n"
        + "Remember, your fleet may not exceed size 6.\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine].\n"
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine].\n"
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine].\n"
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine].\n"
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n"
        + "Uh Oh! You've entered invalid fleet sizes.\n"
        + "Please reenter your fleet in the order [Carrier, Battleship, Destroyer,"
        + " Submarine].\n"
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n", output);
  }

  /**
   * Tests the gameEnd method when it's draw
   */
  @Test
  public void testGameEndDraw() {
    String input = "6 6\n1 1 1 1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner = new Scanner(System.in);

    assertThrows(NoSuchElementException.class, () -> gp.startGame(scanner));

    Board ub = new UserBoard(6, 6);
    ub.initialize();
    List<Coord> occupiedS = new ArrayList<>();
    occupiedS.add(new Coord(0, 0));
    occupiedS.add(new Coord(0, 1));
    occupiedS.add(new Coord(0, 2));
    Ship submarine = new Ship(ShipType.SUBMARINE, occupiedS);
    ub.placeShip(submarine);
    List<Coord> hitPlayer = new ArrayList<>();
    hitPlayer.add(new Coord(0, 0));
    hitPlayer.add(new Coord(0, 1));
    hitPlayer.add(new Coord(0, 2));
    ub.updateHit(hitPlayer);

    Board ob = new OpponentBoard(6, 6);
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship sub = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(sub);

    List<Coord> hitAi = new ArrayList<>();
    hitAi.add(new Coord(0, 0));
    hitAi.add(new Coord(0, 1));
    hitAi.add(new Coord(0, 2));
    ob.updateHit(hitAi);

    standardOut = System.out;
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    gp.gameEnd(ob, ub);

    System.setOut(standardOut);
    String output = outputStream.toString();

    assertEquals("Draw!\n", output);

  }

  /**
   * Tests the gameEnd method when player won
   */
  @Test
  public void testGameEndPlayerWon() {
    String input = "6 6\n1 1 1 1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner = new Scanner(System.in);

    assertThrows(NoSuchElementException.class, () -> gp.startGame(scanner));

    Board ub = new UserBoard(6, 6);
    ub.initialize();
    List<Coord> occupiedS = new ArrayList<>();
    occupiedS.add(new Coord(0, 0));
    occupiedS.add(new Coord(0, 1));
    occupiedS.add(new Coord(0, 2));
    Ship submarine = new Ship(ShipType.SUBMARINE, occupiedS);
    ub.placeShip(submarine);
    List<Coord> hitPlayer = new ArrayList<>();
    hitPlayer.add(new Coord(0, 0));
    ub.updateHit(hitPlayer);

    Board ob = new OpponentBoard(6, 6);
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship sub = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(sub);

    List<Coord> hitAi = new ArrayList<>();
    hitAi.add(new Coord(0, 0));
    hitAi.add(new Coord(0, 1));
    hitAi.add(new Coord(0, 2));
    ob.updateHit(hitAi);

    standardOut = System.out;
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    gp.gameEnd(ob, ub);

    System.setOut(standardOut);
    String output = outputStream.toString();

    assertEquals("Player won!\n", output);
  }

  /**
   * Tests the gameEnd method when AI won
   */
  @Test
  public void testGameAiWon() {
    String input = "6 6\n1 1 1 1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner = new Scanner(System.in);

    assertThrows(NoSuchElementException.class, () -> gp.startGame(scanner));

    Board ub = new UserBoard(6, 6);
    ub.initialize();
    List<Coord> occupiedS = new ArrayList<>();
    occupiedS.add(new Coord(0, 0));
    occupiedS.add(new Coord(0, 1));
    occupiedS.add(new Coord(0, 2));
    Ship submarine = new Ship(ShipType.SUBMARINE, occupiedS);
    ub.placeShip(submarine);
    List<Coord> hitPlayer = new ArrayList<>();
    hitPlayer.add(new Coord(0, 0));
    hitPlayer.add(new Coord(0, 1));
    hitPlayer.add(new Coord(0, 2));
    ub.updateHit(hitPlayer);

    Board ob = new OpponentBoard(6, 6);
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship sub = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(sub);

    List<Coord> hitAi = new ArrayList<>();
    hitAi.add(new Coord(0, 0));
    ob.updateHit(hitAi);

    standardOut = System.out;
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    gp.gameEnd(ob, ub);

    System.setOut(standardOut);
    String output = outputStream.toString();

    assertEquals("AI won!\n", output);
  }

  /**
   * Tests the gameStart method is working properly
   */
  @Test
  public void testGameStart() {
    String input = "6 6\n1 1 1 1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    standardIn = System.in;
    standardOut = System.out;
    System.setIn(inputStream);

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    Scanner scanner = new Scanner(System.in);

    assertThrows(NoSuchElementException.class, () -> gp.startGame(scanner));
  }

}