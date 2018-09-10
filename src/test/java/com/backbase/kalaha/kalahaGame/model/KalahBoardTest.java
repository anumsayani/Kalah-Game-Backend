package com.backbase.kalaha.kalahaGame.model;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class KalahBoardTest {

    @Test
    public void createNewKalahBoardWithSuccess() throws Exception {
        KalahBoard kalahBoard = this.createBoard();

        assertNotNull(kalahBoard.getBoardId());
        assertNotNull(kalahBoard.getFirstPlayer());
        assertNotNull(kalahBoard.getSecondPlayer());
        assertThat(kalahBoard.getCurrentPlayer(), is(kalahBoard.getFirstPlayer()));
        assertThat(kalahBoard.getGameStatus(), is(GameStatus.GAME_CREATED));

        assertThat(kalahBoard.getFirstPlayer().getPits().size(), is(6));
        assertThat(kalahBoard.getSecondPlayer().getPits().size(), is(6));
        assertThat(kalahBoard.getFirstPlayer().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
        assertThat(kalahBoard.getSecondPlayer().getPits().stream().mapToInt(Pit::getStones).sum(), is(36));
    }

    @Test
    public void equalsAndHashCodeWithSuccess() throws Exception {
        KalahBoard boardOne = this.createBoard();
        KalahBoard boardTwo = this.createBoard();

        System.out.println("board ID" + boardOne.getBoardId());
        System.out.println("board ID" + boardTwo.getBoardId());


        assertFalse(boardOne.equals(boardTwo));
    }

    private KalahBoard createBoard() {
        Player playerOne = new Player(Player.Name.FIRST_PLAYER);
        Player playerTwo = new Player(Player.Name.SECOND_PLAYER);
        KalahBoard kalahBoard = new KalahBoard();
        kalahBoard.setFirstPlayer(playerOne);
        kalahBoard.setSecondPlayer(playerTwo);
        kalahBoard.setCurrentPlayer(playerOne);

        return kalahBoard;
    }
}
