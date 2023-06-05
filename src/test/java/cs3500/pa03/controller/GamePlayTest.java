package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.OpponentBoard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.UserBoard;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
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
  private GamePlay gamePlay;
  private Readable input;
  private Appendable output;

  /**
   * Resets the output.
   */
  @BeforeEach
  public void setup() {
    output = new StringBuilder();
  }

  /**
   * Tests the getBoardSize method
   */
  @Test
  public void testGetBoardSize() {
    String inputString = "5 5\n10 19\n10 5\n19 7\n6 6";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);

    gamePlay.getBoardSize();

    String outputString = output.toString();
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
        + "of the game must be in the range (6, 15), inclusive. Try again!\n", outputString);
  }

  /**
   * Tests the getFleet() method
   */
  @Test
  public void testGetFleet() {
    String inputString = "6 6\n1 2 3 4\n1 2 3 0\n1 2 0 1\n1 0 2 1\n0 1 2 3\n1 1 1 1";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);
    gamePlay.getBoardSize();
    gamePlay.getFleet();

    String outputString = output.toString();
    assertEquals("Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width below:\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer,"
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
        + "Remember, your fleet may not exceed size 6, and you need one for each.\n", outputString);
  }

  /**
   * Tests the gameEnd method when it's draw
   */
  @Test
  public void testGameEndDraw() {
    String inputString = "6 6\n1 1 1 1";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);

    assertThrows(NoSuchElementException.class, () -> gamePlay.startGame());

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

    gamePlay.gameEnd(ob, ub);

    String outputString = output.toString();

    // final output should be a draw
    assertTrue(outputString.endsWith("Draw!\n"));
  }

  /**
   * Tests the gameEnd method when player won
   */
  @Test
  public void testGameEndPlayerWon() {
    String inputString = "6 6\n1 1 1 1";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);

    assertThrows(NoSuchElementException.class, () -> gamePlay.startGame());

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

    gamePlay.gameEnd(ob, ub);

    String outputString = output.toString();

    assertTrue(outputString.endsWith("Player won!\n"));
  }

  /**
   * Tests the gameEnd method when AI won
   */
  @Test
  public void testGameAiWon() {
    String inputString = "6 6\n1 1 1 1";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);

    assertThrows(NoSuchElementException.class, () -> gamePlay.startGame());

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

    gamePlay.gameEnd(ob, ub);

    String outputString = output.toString();

    assertTrue(outputString.endsWith("AI won!\n"));
  }

  /**
   * Tests the gameStart method is working properly
   */
  @Test
  public void testGameStart() {
    String inputString = "6 6\n1 1 1 1";
    Readable input = new StringReader(inputString);

    gamePlay = new GamePlay(input, output);

    assertThrows(NoSuchElementException.class, () -> gamePlay.startGame());
  }

}