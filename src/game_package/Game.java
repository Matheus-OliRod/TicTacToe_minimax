package game_package;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Runs the Tic Tac Toe gameplay and contains all its utilities
 */

public class Game extends JPanel {

    private JPanel container;
    private JPanel header;
    private JLabel headerLabel;
    private JButton goToStartButton;
    private JPanel TicTacToe;
    private JButton[] spots = new JButton[9];
    private boolean isPlayer1Turn = true;
    private int WINDOW_SIZE;
    private int AIlevel = -1; // Setting default play mode
    
    protected Game(int WINDOW_SIZE, JPanel container) {

        this.WINDOW_SIZE = WINDOW_SIZE;
        this.container = container;

        headerLabel = new JLabel("X Turn"); // Setting default turn
        goToStartButton = new JButton("Go To Menu");
        header = createHeader();
        TicTacToe = createGamePanel();

        goToStartButton.setVisible(false);

        this.setLayout(new BorderLayout());
        this.add(header, BorderLayout.NORTH);
        this.add(TicTacToe, BorderLayout.CENTER);
    }

    /**
     * Sets the desired level of AI. <p>
     * 
     * If the level is -1, there will be no AI, but another player
     * @param AIlevel
     */
    protected void setAIlevel(int AIlevel) {
        this.AIlevel = AIlevel;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.add(headerLabel);
        header.add(goToStartButton);

        headerLabel.setFont(new Font("Monospace", Font.BOLD, WINDOW_SIZE / 10));

        goToStartButton.addActionListener(e -> reset());

        return header;
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gamePanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

        for(int i = 0; i < 9; i++) {
            int index = i;
            spots[i] = new JButton();
            spots[i].addActionListener(e -> {

                if(!isPlayer1Turn && AIlevel > -1)
                return;

                markSpot(index);
                if(hasWinner())
                    gameEnd(false);
                if(isTie())
                    gameEnd(true);
            });
            spots[i].setBackground(Color.GRAY);
            spots[i].setFont(new Font("Monospace", Font.BOLD, WINDOW_SIZE / 20));
            gamePanel.add(spots[i]);
        }

        return gamePanel;
    }

    /**
     * Will verify if the player can mark the spot. Will do nothing if the player is playing against an AI and it is not his turn
     * @param index
     */
    private void markSpot(int index) {
        if(!spots[index].getText().equals(""))
            return;

        spots[index].setText(isPlayer1Turn ? "X" : "O");
        isPlayer1Turn = !isPlayer1Turn;

        headerLabel.setText(isPlayer1Turn ? "X Turn" : "O Turn");
    }

    /**
     * Verifies if the game has a winner, doesn't tell the winner or if it was a tie.
     * @return true if the game have a winner
     */
    private boolean hasWinner() {

        // Checking for victory by any player

        if(!spots[0].getText().equals("") &&
            spots[0].getText().equals(spots[1].getText()) && spots[0].getText().equals(spots[2].getText()))
            return true;
        if(!spots[3].getText().equals("") &&
            spots[3].getText().equals(spots[4].getText()) && spots[3].getText().equals(spots[5].getText()))
            return true;
        if(!spots[6].getText().equals("") &&
            spots[6].getText().equals(spots[7].getText()) && spots[6].getText().equals(spots[8].getText()))
            return true;
        if(!spots[0].getText().equals("") &&
            spots[0].getText().equals(spots[3].getText()) && spots[0].getText().equals(spots[6].getText()))
            return true;
        if(!spots[1].getText().equals("") &&
            spots[1].getText().equals(spots[4].getText()) && spots[1].getText().equals(spots[7].getText()))
            return true;
        if(!spots[2].getText().equals("") &&
            spots[2].getText().equals(spots[5].getText()) && spots[2].getText().equals(spots[8].getText()))
            return true;
        if(!spots[0].getText().equals("") &&
            spots[0].getText().equals(spots[4].getText()) && spots[0].getText().equals(spots[8].getText()))
            return true;
        if(!spots[2].getText().equals("") &&
            spots[2].getText().equals(spots[4].getText()) && spots[2].getText().equals(spots[6].getText()))
            return true;

        return false;
    }

    /**
     * Verifies if the current game ended in a tie
     * @return true if the game is a tie
     */
    private boolean isTie() {

        for(JButton btn : spots) {
            if(btn.getText().equals(""))
                return false;
        }

        return true;
    }

    private void gameEnd(boolean isTie) {
        
        // Disabling all buttons

        for(JButton btn : spots) {
            btn.setEnabled(false);
        }

        if(isTie)
            headerLabel.setText("Tie");
        else
            headerLabel.setText((!isPlayer1Turn ? "Player 1" : "Player 2") + " Won!");
        
            goToStartButton.setVisible(true);
    }

    /**
     * Will reset the entire state of the game. Will also hide the game panel and enable the start menu panel
     */
    private void reset() {

        // Reseting all spots

        for(JButton btn : spots) {
            btn.setText("");
            btn.setEnabled(true);
        }

        isPlayer1Turn = true;

        headerLabel.setText("X Turn");

        goToStartButton.setVisible(false);
        
        ((CardLayout) container.getLayout()).show(container, "Main"); // Switching back to the main menu
    }
}
