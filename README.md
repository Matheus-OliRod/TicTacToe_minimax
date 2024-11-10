# Tic Tac Toe

Tic Tac Toe is a classic two-player game where players compete on a 3x3 grid, aiming to form a line (vertical, horizontal, or diagonal) using their mark (X or O). Each player can mark a space once per turn. Players are not allowed to mark an already occupied spot, nor skip their turn.

## Project Overview

This project is developed in Java, using native Java APIs for testing and rendering the game window. 

The game offers two modes of play:
1. **Local Multiplayer**: Players share the same computer and take turns.
2. **AI Opponent**: Players can compete against a bot with three levels of difficulty: Easy, Medium, and Hard. Each difficulty is explained below.

## AI Mechanics

The AI in this game is based on the **minimax algorithm**.

The minimax algorithm enables the bot to calculate all possible outcomes of a move by predicting future moves and deciding the optimal path to maximize its chances of winning.

### AI Levels:
1. **Easy Mode**: This mode does not use the minimax algorithm. Instead, it plays randomly, without considering future moves or tactics. The bot can still win by chance.
   
2. **Medium Mode**: This mode predicts only one move ahead, choosing moves that prevent an immediate loss.

3. **Hard Mode**: This mode is unbeatable, as it evaluates all possible moves and selects the one that ensures it doesn't lose. Tic Tac Toe has a _"perfect"_ outcome of a draw if both players play optimally without making mistakes, therefore granting the unbeatability of the bot.

## Notes

Have in mind that the aim of this project is to represent the use of automatic testing and my knowlodge over algorithms, it does **NOT** aim to manifest my design capabilities, so do **NOT** expect to game to look great.