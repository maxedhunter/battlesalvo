package cs3500.pa03.model;

import cs3500.pa03.controller.GamePlay;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an AI player in a game of BattleSalvo.
 */
public class Ai implements Player {
  // a GameStatus
  private final GameStatus gs = new GameStatus();
  // AI's board
  private OpponentBoard board;
  // a game result
  private GameResult result;
  // AI's fleet
  private List<Ship> aiShips;
  // shots that hits the opponent
  private final List<Coord> hitOpponent = new ArrayList<>();
  // shots that has already been fired
  private final List<Coord> alreadyShot = new ArrayList<>();
  // a gamePlay controller
  private final GamePlay gp;

  /**
   * Constructor of the Ai class that initializes the gp
   *
   * @param gp dependency injection
   */
  public Ai(GamePlay gp) {
    this.gp = gp;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "shuerry";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new OpponentBoard(height, width);
    this.board.initialize();
    this.aiShips = new ArrayList<>();
    List<Coord> occupied = new ArrayList<>();

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipType = entry.getKey();
      int shipCount = entry.getValue();

      for (int i = 0; i < shipCount; i++) {
        Ship ship = ShipGenerator.generateRandomShip(shipType, this.board, occupied);
        this.aiShips.add(ship);
        this.board.placeShip(ship);
        occupied.addAll(ship.getOccupiedPositions());
      }
    }
    gp.setAiBoard(board);
    return this.aiShips;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Ship> notSank = gs.checkSunk(aiShips);
    List<Coord> shots = new ArrayList<>();
    Random random = new Random();
    int shotSize;
    if ((board.getHeight() * board.getWidth() - alreadyShot.size()) < notSank.size()) {
      shotSize = board.getHeight() * board.getWidth() - alreadyShot.size();
    } else {
      shotSize = notSank.size();
    }
    while (shots.size() < shotSize) {
      int col = random.nextInt(board.getWidth());
      int row = random.nextInt(board.getHeight());
      Coord newShot = new Coord(col, row);
      if (!alreadyShot.contains(newShot)) {
        shots.add(newShot);
        alreadyShot.add(newShot);
      }
    }
    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *        ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damaged = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      for (Ship ship : aiShips) {
        List<Coord> updatedPos = new ArrayList<>(ship.getOccupiedPositions());
        for (Coord pos : ship.getOccupiedPositions()) {
          if (shot.getX() == pos.getX() && shot.getY() == pos.getY()) {
            updatedPos.remove(pos);
            damaged.add(pos);
          }
        }
        ship.setOccupiedPositions(updatedPos);
      }
    }
    return damaged;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    hitOpponent.addAll(shotsThatHitOpponentShips);

  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    this.result = result;
  }
}
