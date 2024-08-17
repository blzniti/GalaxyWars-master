# Galaxy Wars

Galaxy Wars is a space-themed shooting game developed as a learning project for an Object-Oriented Programming (OOP) class. The game's concept revolves around protecting our world by shooting meteors.
![alt text](res/images/banner/banner.png)

## Game Concept

In Galaxy Wars, players defend a planet from incoming meteors. The main objectives are:

1. Shoot meteors to prevent them from hitting the planet
2. Collect power-ups to enhance gameplay
3. Progress through levels with increasing difficulty

## Key Features

- Space-themed graphics and sound effects
- Player-controlled spaceship for shooting meteors
- Various types of meteors with different behaviors
- Power-up items with special effects (e.g., freeze, shield, automatic shooting)
- Multiple difficulty levels
- Score tracking and time-based gameplay
- Level progression system
- Customizable planet selection

## Object-Oriented Programming Concepts Demonstrated

This project showcases several OOP concepts, including:

1. Inheritance: The `Entity` class serves as a base class for game objects like `Player`, `Meteor`, and `Item`.
2. Encapsulation: Classes like `GamePlay` encapsulate game logic and state.
3. Polymorphism: The `update()` and `draw()` methods are implemented differently for various game entities.
4. Interfaces: The `ItemType` interface defines different types of power-ups.
5. Enums: The `Difficulty` enum represents different game difficulty levels.

## Project Structure

The project is organized into several packages:

- `game`: Contains the main game logic and components
- `game.controller`: Handles user input
- `game.entity`: Defines game objects (Player, Meteor, Item, etc.)
- `game.gui`: Manages game UI components
- `game.interfaces`: Defines interfaces used in the game
- `game.util`: Provides utility classes for file management, image handling, and sound

## How to Run

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Open a terminal or command prompt.
3. Navigate to the project's `/src` directory.
4. Compile the project using the following command:
   ```
   javac -d ../bin Main.java game/*.java game/controller/*.java game/entity/*.java game/gui/*.java game/interfaces/*.java game/util/*.java
   ```
5. After successful compilation, navigate to the parent directory:
   ```
   cd ..
   ```
6. Run the main file using the following command:
   ```
   java -cp bin Main
   ```

## Controls

- Mouse Movement: Aim the crosshair
- Left Mouse Click: Shoot
- Hold Left Mouse Button: Continuous shooting (when automatic power-up is active)
- ESC Key: Pause game / Return to main menu

## Dependencies

- Java Swing: Used for the game's graphical user interface
- Java AWT: Used for graphics and event handling

## Contributors

- 65011212122 Phothiphong Meethonglang: Code and Design
- 65011212178 Nitipong Boonprasert: Code and Design
- 65011212148 Apidsada Laochai: Design and Assets
- 65011212151 Atsadawut Trakanjun: Design and Assets
- 65011212132 Wiritphon DuangDusan: Sound and Assets

## Acknowledgements

This project was developed as part of the Object-Oriented Programming course (1204203) at Mahasarakham University, under the guidance of Professor Natthariya Laopracha.

## License

This project is licensed under the MIT License.

Copyright (c) 2023 MaouStan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
