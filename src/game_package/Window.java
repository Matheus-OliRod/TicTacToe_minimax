package game_package;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

/**
 * Loads the main window. <p>
 * The selection of the game mode (AI or second Player) is made here
 */

public class Window extends JFrame {

    // Getting the size of the user's screen
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int WINDOW_SIZE = SCREEN_SIZE.width / 2 > SCREEN_SIZE.height / 2 ? SCREEN_SIZE.height / 2 : SCREEN_SIZE.width / 2;

    private JPanel container, mainPanel, gameModePanel, difficultyPanel;
    private Game gamePanel;

    private ButtonGroup gameModeSelector;
    private JRadioButton[] gameModes = {
        new JRadioButton("Player", true),
        new JRadioButton("AI")
    };

    private ButtonGroup difficultySelector;
    private JRadioButton[] difficulties = {
        new JRadioButton("Easy", true),
        new JRadioButton("Medium"),
        new JRadioButton("Hard")
    };
    
    public Window() {

        container = new JPanel(new CardLayout());
        mainPanel = new JPanel(new BorderLayout());
        gamePanel = new Game(WINDOW_SIZE, container);
        gameModePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gameModeSelector = new ButtonGroup();
        difficultySelector = new ButtonGroup();

        // Adding the radio buttons to their groups

        for(JRadioButton rb : gameModes) {
            gameModeSelector.add(rb);
        }

        // Adding action listeners to change the difficulty panel visibility and set the AI level to -1 if singleplayer is selected

        gameModes[0].addActionListener(e -> {
            difficultyPanel.setVisible(gameModes[1].isSelected());
            if(!gameModes[1].isSelected())
                gamePanel.setAIlevel(-1);
            else
                gamePanel.setAIlevel(0);
            });
        gameModes[1].addActionListener(e -> {
            difficultyPanel.setVisible(gameModes[1].isSelected());
            if(!gameModes[1].isSelected())
                gamePanel.setAIlevel(-1);
            else
                gamePanel.setAIlevel(0);
        });

        for(JRadioButton rb : difficulties) {
            difficultySelector.add(rb);
        }

        difficulties[0].addActionListener(e -> {gamePanel.setAIlevel(0);});
        difficulties[1].addActionListener(e -> {gamePanel.setAIlevel(1);});
        difficulties[2].addActionListener(e -> {gamePanel.setAIlevel(2);});

        // Setting main panel attributes

        mainPanel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

        // ----- Setting the contents of each panel ----- //

        // Game modes

        for(JRadioButton rb : gameModes) {
            gameModePanel.add(rb);
        }

        gameModePanel.add(new JButton("Start Game") {{
            addActionListener(e -> startGame());
        }});

        // Difficulties

        for(JRadioButton rb : difficulties) {
            difficultyPanel.add(rb);
        }

        difficultyPanel.setVisible(gameModes[1].isSelected());

        // Main Panel

        mainPanel.add(gameModePanel, BorderLayout.NORTH);
        mainPanel.add(difficultyPanel, BorderLayout.CENTER);

        // Putting all togheter

        container.add(mainPanel, "Main");
        container.add(gamePanel, "Game");

        // Setting Window attributes
        
        this.setTitle("Tic Tac Toe");
        this.add(container);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    /**
     * Changes the shown card to the game card
     */
    private void startGame() {
        ((CardLayout) container.getLayout()).show(container, "Game"); // Switching cards to the game panel
        this.pack();
    }
}
