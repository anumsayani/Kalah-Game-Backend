package com.backbase.kalaha.kalahaGame.service.impl;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Player;
import com.backbase.kalaha.kalahaGame.repository.KalahBoardRepository;
import com.backbase.kalaha.kalahaGame.service.KalahBoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KalahBoardServiceImplTest {

    @InjectMocks
    private KalahBoardServiceImpl kalahBoardService;

    @Mock
    private KalahBoardRepository boardRepository;

    @Test
    public void whenCreateBoard_thenVerifyBoardStatus() throws Exception{
        KalahBoard board = kalahBoardService.createBoard();
        assertNotNull(board);
        assertNotNull(board.getBoardId());
        assertThat(board.getGameStatus(), is(GameStatus.GAME_CREATED));
    }

    /**
     *
     * Rule1:  Distribute stones
     */
    @Test
    public void whenPlayerMovedPit2_thenVerifyStones() throws Exception{
        KalahBoard board = createBoardMock();
        String id = board.getBoardId();

        when(boardRepository.findKalahBoardByID(id)).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(id, 2);

        assertNotNull(updatedBoard);
        Player firstPlayer = updatedBoard.getFirstPlayer();
        Player secondPlayer = updatedBoard.getSecondPlayer();

        int[] expectedUpdatedStonesOfPlayerOne =  {6,0,7,7,7,7};
        final AtomicInteger index = new AtomicInteger(-1);
        firstPlayer.getPits().stream()
                .forEach(o -> {
                    assertThat(o.getStones(),
                            is(expectedUpdatedStonesOfPlayerOne[index.addAndGet(1)]));
                });
        assertThat(firstPlayer.getKalah().getStones(), is(1));
        assertThat(secondPlayer.getPitById(8).getStones(), is(7));
    }

    @Test
    public void whenPlayerMovedStones_thenVerifyTurnChange() throws Exception{
        KalahBoard board = createBoardMock();
        String id = board.getBoardId();

        when(boardRepository.findKalahBoardByID(id)).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(id, 2);

        assertThat(updatedBoard.getCurrentPlayer(), is(board.getSecondPlayer()));

    }

    /**
     * Rule 2 : If the player puts last stone in kalah, then he gets another turn
     */
    @Test
    public void whenPlayerMovedLastStoneInKalah_thenGetAnotherTurn()
            throws Exception{
        KalahBoard board = createBoardMock();
        String id = board.getBoardId();

        when(boardRepository.findKalahBoardByID(id)).thenReturn(board);

        //if move is made from pit 1 then last stone should go to kalah
        KalahBoard updatedBoard = kalahBoardService.move(id, 1);

        Player firstPlayer = updatedBoard.getFirstPlayer();

        //player 1 kalah should have 1 stone
        assertThat(firstPlayer.getKalah().getStones(), is(1));

        //turn should not change!
        assertThat(updatedBoard.getCurrentPlayer(), is(firstPlayer));
    }

    /**
     * Rule 3: If player puts last stone in his own empty
     * pit, then capture opponent's stones from adjascent pit
     */
    @Test
    public void whenPlayerPutLastStoneInOwnEmptyPit_thenCaptureOpponentsStones()
            throws Exception{
        KalahBoard board = createBoardMock();
        Player firstPlayer = board.getFirstPlayer();
        Player secondPlayer = board.getSecondPlayer();

        //modify game state to have one empty pit
        // emptying pit-Id 6
        firstPlayer.getPitById(6).removeStones();

        //pit 1 should have 5 stones so the last stone can reach
        //to empty pit 6
        firstPlayer.getPitById(1).setStones(5);

        //distribute the removed stones on the game board
        secondPlayer.getPitById(8).setStones(13);

        //make move from player 1 from pit 1 (6 stones) so last stone will
        //reach in empty pit 6, and he will get his stone and 13 stones from
        //adjascent pit 8 of the opposite player. Player 1 kalah should have 14 stones.
        when(boardRepository.findKalahBoardByID(board.getBoardId())).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(board.getBoardId(), 1);

        //verify that player1 kalah has 14 stones!!
        assertThat(updatedBoard.getFirstPlayer().getKalah().getStones(), is(14));

    }

    /**
     *
     * Termination of Game, and assigning winner
     */
    @Test
    public void whenPlayerHasEmptyPits_thenAssignWinner(){
        KalahBoard board = createBoardMock();
        Player firstPlayer = board.getFirstPlayer();
        Player secondPlayer = board.getSecondPlayer();

        //set player pits to zero
        firstPlayer.getPits().stream().forEach( o -> o.setStones(0));
        firstPlayer.getKalah().setStones(30);

        board.setCurrentPlayer(secondPlayer);

        when(boardRepository.findKalahBoardByID(board.getBoardId())).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(board.getBoardId(), 8);

        assertThat(updatedBoard.getGameStatus(), is(GameStatus.SECOND_PLAYER_WON));

    }

    @Test
    public void whenAssignWinner_thenVerifyKalahStones(){
        KalahBoard board = createBoardMock();
        Player firstPlayer = board.getFirstPlayer();
        Player secondPlayer = board.getSecondPlayer();

        //set player pits to zero
        firstPlayer.getPits().stream().forEach( o -> o.setStones(0));
        secondPlayer.getPits().stream().forEach( o -> o.setStones(0));

        firstPlayer.getPitById(6).addStone();
        firstPlayer.getKalah().setStones(29);
        secondPlayer.getKalah().setStones(42);

        when(boardRepository.findKalahBoardByID(board.getBoardId())).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(board.getBoardId(), 6);

        //player with more kalah stones win!
        assertThat(updatedBoard.getGameStatus(), is(GameStatus.SECOND_PLAYER_WON));
    }

    @Test
    public void whenAssignWinner_thenVerifyGameDraw(){
        KalahBoard board = createBoardMock();
        Player firstPlayer = board.getFirstPlayer();
        Player secondPlayer = board.getSecondPlayer();

        //set player pits to zero
        firstPlayer.getPits().stream().forEach( o -> o.setStones(0));
        firstPlayer.getKalah().setStones(36);

        board.setCurrentPlayer(secondPlayer);

        when(boardRepository.findKalahBoardByID(board.getBoardId())).thenReturn(board);
        KalahBoard updatedBoard = kalahBoardService.move(board.getBoardId(), 8);

        assertThat(updatedBoard.getGameStatus(), is(GameStatus.GAME_DRAW));

    }

    private KalahBoard createBoardMock(){
        KalahBoard board = new KalahBoard();

        Player firstPlayer = new Player(Player.Name.FIRST_PLAYER);
        Player secondPlayer = new Player(Player.Name.SECOND_PLAYER);

        board.setFirstPlayer(firstPlayer);
        board.setSecondPlayer(secondPlayer);
        board.setCurrentPlayer(firstPlayer);

        return board;
    }



}
