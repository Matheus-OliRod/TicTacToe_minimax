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

import java.util.Random;
import java.util.stream.IntStream;

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
    private Random rand = new Random();
    
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

    /**
     * Gets all the currently available spots
     * @return array of indexes that are available
     */
    private int[] getAvailableSpots() {
        return IntStream.range(0, spots.length)
                        .filter(i -> spots[i].getText().equals(""))
                        .toArray();
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
            spots[i].addActionListener(e ->{

                if(!isPlayer1Turn && AIlevel > -1)
                    return;

                if(!spots[index].getText().equals(""))
                    return;

                markSpot(index);
                isPlayer1Turn = !isPlayer1Turn;
                headerLabel.setText(isPlayer1Turn ? "X Turn" : "O Turn");

                if(hasWinner()) {
                    gameEnd(false);
                    return;
                }
                if(isTie()) {
                    gameEnd(true);
                    return;
                }

                nextAIMove();
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
    protected void markSpot(int index) {

        if(!spots[index].getText().equals(""))
            return;

        spots[index].setText(isPlayer1Turn ? "X" : "O");
        spots[index].setForeground((isPlayer1Turn ? Color.blue : Color.red)); // Setting different colors to the markers
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
            headerLabel.setText((!isPlayer1Turn ? "Player 1" : (AIlevel > -1 ? "AI" : "Player 2")) + " Won!");
        
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

    // ---- Foward there is only AI related code ---- //

    /**
     * Clears the spot. This method is only used by the AI
     * @param index
     */
    protected void clearSpot(int index) {
        spots[index].setText("");
    }

    private void nextAIMove() {

        switch (AIlevel) {
            case 0:
                makeRandomMove(getAvailableSpots());
                isPlayer1Turn = !isPlayer1Turn;
                headerLabel.setText(isPlayer1Turn ? "X Turn" : "O Turn");
                break;
        
            case 1:
                makeMediumMove(getAvailableSpots());
                isPlayer1Turn = !isPlayer1Turn;
                headerLabel.setText(isPlayer1Turn ? "X Turn" : "O Turn");
                break;
            
            case 2:
                makeBestMove(getAvailableSpots());
                isPlayer1Turn = !isPlayer1Turn;
                headerLabel.setText(isPlayer1Turn ? "X Turn" : "O Turn");
                break;

            default:
                break;
        }

        if(hasWinner()) {
            gameEnd(false);
            return;
        }
        if(isTie()) {
            gameEnd(true);
            return;
        }
    }

    /**
     * Will mark a random available spot of the game grid
     * @param availableSpots All available spots on the game grid
     */
    private void makeRandomMove(int[] availableSpots) {
        markSpot(availableSpots[rand.nextInt(availableSpots.length)]);
    }

    /**
     * Will mark the least worse move, where it won't lose the next move and preferelly will win if it can
     * @param availableSpots All available spots on the game grid
     */
    private void makeMediumMove(int[] availableSpots) {
        int bestIndex = 0;
        int highest = -1; // -1 is a lost game, 0 is tie or on going match
    
        // Making its move
        for(int i : availableSpots) {
    
            // making it find itself winning "attractive"
           markSpot(i);
           if(hasWinner() || isTie()) {
               bestIndex = i;
               highest = 1;
            }
            
            isPlayer1Turn = !isPlayer1Turn;

            // Predicting if it loses or draws
           for(int j : availableSpots) {

            if(i == j) // Ignoring the already marked spot
                continue;

            markSpot(j);
            isPlayer1Turn = !isPlayer1Turn;

            if(hasWinner()) {
                clearSpot(j);
                isPlayer1Turn = !isPlayer1Turn;
                if(highest != 1) bestIndex = j; // This prevents the player from winning in 1 *IF* the player has only one win and the AI doesn't have a winning move
                continue;
            }

            if(isTie() && highest < 0) {
                bestIndex = i;
                highest = 0;
            }

            if(highest < 0) {
                bestIndex = i;
                highest = 0;
            }

            clearSpot(j);
            isPlayer1Turn = !isPlayer1Turn;
           }
            
           clearSpot(i);
           isPlayer1Turn = !isPlayer1Turn;
        }

        markSpot(bestIndex);
    }

    /**
     * Will use minimax to find the best path for itself, will draw or win, but never lose
     * @param availableSpots All available spots on the game grid
     */
    private void makeBestMove(int[] availableSpots) {
        int bestIndex = -1;
        int bestOutcome = Integer.MIN_VALUE;
    
        // First, check for immediate win or loss
        bestIndex = checkImmediateWinOrLoss(availableSpots);
        if (bestIndex != -1) {
            markSpot(bestIndex);
            return;
        }
    
        // If no immediate win/loss, run minimax for the best move
        for (int i : availableSpots) {
            markSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
    
            int currentAnalysis = minimax(getAvailableSpots(), 0);
    
            // Restore the spot
            clearSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
    
            if (currentAnalysis > bestOutcome) {
                bestOutcome = currentAnalysis;
                bestIndex = i;
            }
        }
        markSpot(bestIndex);
    }
    
    // Helper method for checking immediate win or loss
    private int checkImmediateWinOrLoss(int[] availableSpots) {
        // Check for immediate win
        for (int i : availableSpots) {
            markSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
            if (hasWinner() && isPlayer1Turn) {
                clearSpot(i);
                isPlayer1Turn = !isPlayer1Turn;
                return i; // Immediate win found, return the move
            }
            clearSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
        }
    
        // Check for immediate loss (block the opponent)
        for (int i : availableSpots) {
            markSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
            for(int j : availableSpots) {
                if(j == i) continue; // Ignores the already marked spot

                markSpot(j);
                isPlayer1Turn = !isPlayer1Turn;

                if(hasWinner()) {
                    clearSpot(j);
                    clearSpot(i);
                    return j;
                }

                clearSpot(j);
                isPlayer1Turn = !isPlayer1Turn;
                
            }
                clearSpot(i);
                isPlayer1Turn = !isPlayer1Turn;
        }
    
        return -1; // No immediate win or loss
    }
    
    // Minimax algorithm (no changes, same as before)
    private int minimax(int[] availableSpots, int depth) {
        // Handling end game cases
        if (hasWinner())
            return isPlayer1Turn ? -1 + depth : 1 - depth;
        
        if (isTie())
            return 0;
    
        int bestScore = isPlayer1Turn ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    
        // Recursively explore all available spots
        for (int i : availableSpots) {
            markSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
            int score = minimax(getAvailableSpots(), depth + 1);
            clearSpot(i);
            isPlayer1Turn = !isPlayer1Turn;
    
            // Minimize score for Player, maximize for AI
            if (isPlayer1Turn) {
                bestScore = Math.min(score, bestScore);
            } else {
                bestScore = Math.max(score, bestScore);
            }
        }
    
        return bestScore;
    }
    

}

