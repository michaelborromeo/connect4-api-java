package connect4.model;

import java.io.Serializable;

public class GridCell implements Serializable {
  private int usedAtTurn;
  private int column;
  private int row;
  private int player;

  public int getUsedAtTurn() {
    return usedAtTurn;
  }

  public void setUsedAtTurn(int usedAtTurn) {
    this.usedAtTurn = usedAtTurn;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getPlayer() {
    return player;
  }

  public void setPlayer(int player) {
    this.player = player;
  }
}
