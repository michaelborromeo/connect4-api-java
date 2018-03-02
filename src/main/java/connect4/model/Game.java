package connect4.model;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Date timestamp;
  private int winningPlayer;
  private int numberOfTurns;
  private GridCell[][] grid;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public int getWinningPlayer() {
    return winningPlayer;
  }

  public void setWinningPlayer(int winningPlayer) {
    this.winningPlayer = winningPlayer;
  }

  public int getNumberOfTurns() {
    return numberOfTurns;
  }

  public void setNumberOfTurns(int numberOfTurns) {
    this.numberOfTurns = numberOfTurns;
  }

  public GridCell[][] getGrid() {
    return grid;
  }

  public void setGrid(GridCell[][] grid) {
    this.grid = grid;
  }
}
