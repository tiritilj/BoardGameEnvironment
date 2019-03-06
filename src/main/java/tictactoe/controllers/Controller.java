package tictactoe.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

import tictactoe.models.*;
import tictactoe.utility.*;

/**
 * Represents the contoller for the Tic Tac Toe game
 * in the MVC patter. Acts as the intermidiary between the
 * FXML view and the {@code TicTacToe} model. Contains various
 * actions that are executed when an event is triggerd
 * in the view, and outlets for controlling objects in the view.
 * 
 * @author Jacob Tiritilli
 */
public class Controller {

    // Holds the Tic Tac Toe game. Pass in player "X" as
    // the starting player, but this option may be given
    // to the user at a later point.
    private TicTacToe game = new TicTacToe(Player.X);

    // Outlet associate with label in view that will
    // display the status of the game.
    public Label statusLabel;

    @FXML
    // Outlet collection of all of the labels that act
    // as cells on the game board in the
    // view.
    private List<Label> cellList;

    @FXML
    /**
     * Automatically called by the system when the FXML is loaded.
     * Performs initial setup by setting the value of the status label
     * and setting an event handler that will be executed when a cell
     * on the game board is clicked with the mouse. The handler is
     * passed both the number of the cell and a reference to that cell's
     * label.
     */
    protected void initialize() {
        for (int i = 0; i < cellList.size(); i++) {
            final int cellNum = i;
            final Label label = cellList.get(i);
            cellList.get(i).setOnMouseClicked(event -> cellPressed(cellNum, label));
        }

        statusLabel.setText("Turn: " + game.getCurrentPlayer().toString());
    }

    /**
     * Event handler that is executed when a given cell on a game board
     * is clicked. It informs the model of the move and updates the view.
     * @param cellNum the number of the cell that was clicked (0-9)
     * @param label a reference to the label of the clicked cell so that it
     * can be updated with the appropriate player
     */
    private void cellPressed(int cellNum, Label label) {
        Player currentPlayer = game.getCurrentPlayer();
        
        // If move was invalid, then exit early.
        if (!game.makeMove(cellNum)) { return; }

        label.setText(currentPlayer.toString());

        if (game.gameWon()) {
            statusLabel.setText("Game Over: Player " + currentPlayer.toString() + " wins!");
        } else if (game.boardIsFull()) {
            statusLabel.setText("Cat's Game!");
        } else {
            statusLabel.setText("Turn: " + game.getCurrentPlayer().toString());
        }
    }

    /**
     * Begins a new Tic Tac Toe game and updates the view to
     * clear the board and status label.
     */
    public void startNewGame(ActionEvent actionEvent) {
        game = new TicTacToe(Player.X);
        statusLabel.setText("Turn: " + game.getCurrentPlayer().toString());
        
        for (Label cell: cellList) {
            cell.setText("");
        }
    }
}
