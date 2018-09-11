# Backbase Back-end Assignment

Assignment is based on [Kalah Game](https://en.wikipedia.org/wiki/Kalah)

Assignment Goals
---
Each of the two players has **​six pits** ​in front of him/her. To the right of the six pits, each player has a larger pit, his
Kalah or house.
At the start of the game, six stones are put in each pit.
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in
each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last
stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other
player's turn.
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
other players' pit) and puts them in his own Kalah.
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps
them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.

Back-end Technologies
---

Backend of the game provides REST services for creating the game, and moving the stones in the pit
The game can be built by **maven**, and uses **java8**, **Spring-boot**

Start Game
---

Building the game:

```
$ mvn clean install
```

Starting the game:

```
$ java -jar target/KalahaGame-0.0.1-SNAPSHOT.jar

```

Kalaha WebService
---

- POST http://localhost:8080/games
> Creates a game board, and returns the boardID with Game board URL

- GET http://localhost:8080/games/{boardId}
> Returns Game board status

- PUT http://localhost:8080/games/{boardId}/pits/{pitId}
> Take a turn from the respective pit






