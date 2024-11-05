package io.ckgxrg.xchelf.math;

import java.util.function.Consumer;

/** Minimal implementation of a n * n square matrix of boolean values. */
public class Matrixnb {

  boolean[][] mat;
  final int length;

  /**
   * Initialises a matrix with given width and height.
   *
   * @param n Side length of the square matrix
   */
  public Matrixnb(int n) {
    length = n;
    mat = new boolean[length][length];
  }

  /**
   * Sets the value in a given position.
   *
   * @param row Row of the position
   * @param col Column of the position
   * @param value The value in that position
   */
  public void set(int row, int col, boolean value) {
    mat[row][col] = value;
  }

  /**
   * Sets the value in a given position.
   *
   * @param row Row of the position
   * @param col Column of the position
   * @param value The value in that position
   */
  public void set(int row, int col, int value) {
    mat[row][col] = value == 0 ? false : true;
  }

  /**
   * Removes the given column.
   *
   * @param col The column number
   */
  public void rmColumn(int col) {
    for (int i = 0; i < length; i++) {
      mat[i][col] = false;
    }
  }

  /**
   * Removes the given row.
   *
   * @param row The row number
   */
  public void rmRow(int row) {
    for (int i = 0; i < length; i++) {
      mat[row][i] = false;
    }
  }

  /**
   * Gets the value in a position.
   *
   * @param row Row of the position
   * @param col Column of the position
   * @return The value as a boolean
   */
  public boolean get(int row, int col) {
    return mat[row][col];
  }

  /**
   * Gets the value in a position.
   *
   * @param row Row of the position
   * @param col Column of the position
   * @return The value as an int
   */
  public int getInt(int row, int col) {
    return mat[row][col] ? 1 : 0;
  }

  /**
   * Traverses the matrix and execute actions defined in func.
   *
   * @param func The operations to be executed
   */
  public void forEach(Consumer<Boolean> func) {
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        func.accept(mat[i][j]);
      }
    }
  }

  /**
   * Superimposes the right-top section of the matrix to the left-bottom section, since an adjacency
   * matrix for a graph with no directions is symmetric.
   */
  public void superimpose() {
    for (int i = 1; i < length; i++) {
      for (int j = i; j < length; j++) {
        mat[j][i] = mat[i][j];
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < length; i++) {
      res.append(i + " | ");
      for (int j = 0; j < length; j++) {
        res.append((mat[i][j] ? 1 : 0) + " ");
      }
      res.append("|\n");
    }
    return res.toString();
  }
}
