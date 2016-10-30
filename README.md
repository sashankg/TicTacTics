# TicTacTics
2 player Java puzzle game based on app by Hidden Variable Studios using networking for playing between systems

## Rules
The game starts with each player (X and O) secretly selecting 9 spots on the board. During this process, only two marks can be made per nested board, and there cannot be two marks in corresponding squares of a nested board. Once the 9 spots are selected by each player, the boards are merged and any overlapping marks are cancelled. 

[![Initial Phase Demo](https://cdn.rawgit.com/sashankg/TicTacTics/master/cjHxpNg%20-%20Imgur.gif)](https://youtu.be/5Lk7Altgheg)

Once the initial phase is complete, the players take turns making marks on the board. The next player must play in the nested board that corresponds to the position of the mark just made. If a player wins a nested board, they own that board. If a nested board ends in a draw, then that board becomes a wildcard. The goal is to make three-in-a-row using the owned nested boards.

[![Gameplay Demo](https://cdn.rawgit.com/sashankg/TicTacTics/master/gkCAn67%20-%20Imgur.gif)](https://youtu.be/18WPJ3ETGwo)

## Technology
- Programmed in Java
- MVC application architecture
- Sockets and Piped Input/Output Streams used for networking between computers
- David Stigant's (my AP Computer Science teacher) animation library for graphics

[![Server Demo](https://cdn.rawgit.com/sashankg/TicTacTics/master/sdJnDP0%20-%20Imgur.gif)](https://youtu.be/-djDIsZ-PuY)
