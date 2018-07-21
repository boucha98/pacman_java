# Pacman game developed with JAVA SE8.

## School project where I had to apply some instructions:
- MVC (Model - View - Controller) architecture
- JavaFX library to display the game
- Some design patterns to use:
	- Singleton
	- Observer, for notifications from Model to View
	- Composite, for a special rule: Ghosts can merge when they meet. Composite makes it easy to manipulate simple Ghosts and merged Ghosts
	- Memento, for a special rule: Pacman saves game state when he eats a Mushroom. Old state will be restored if a Ghost  win against Pacman
	
## Personal additions
- Different levels of difficulty: the further you go, the faster the ghosts go
- Some views for a better user experience (see `Pacman/src/View/GameViewFx.java`)
- A* algorithm used for some Ghosts so they can hunt Pacman (Blinky does it and Clyde move randomly from time to time). See `Pacman\src\Model\Motion` for details

## Other
- To start the game, just launch GameControllerFx.java file in `Pacman/src/GameController` folder
- Use keyboard arrow keys to move Pacman