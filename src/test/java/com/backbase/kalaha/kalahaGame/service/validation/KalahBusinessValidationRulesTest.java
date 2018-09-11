package com.backbase.kalaha.kalahaGame.service.validation;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.exception.BusinessException;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Player;
import com.backbase.kalaha.kalahaGame.service.KalahBoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class KalahBusinessValidationRulesTest {

    @Test(expected = BusinessException.class)
    public void whenFirstMoveIsMade_thenVerifyGameStatus() throws Exception{
       KalahBoard board = this.createBoard();
       board.setGameStatus(GameStatus.GAME_CREATED);
       KalahBusinessValidationRules.validateBusinessRulesForMove(board, board.getFirstPlayer().getPitById(1));
    }

    @Test(expected = BusinessException.class)
    public void whenStoneFromKalahIsMoved_thenStopMove() throws Exception{
        KalahBoard board = this.createBoard();
        KalahBusinessValidationRules.validateBusinessRulesForMove(board, board.getFirstPlayer().getPitById(7));

    }

    @Test(expected = BusinessException.class)
    public void whenMoveFromEmptyPit_thenStopMove() throws Exception{
        KalahBoard board = this.createBoard();
        board.getFirstPlayer().getPitById(1).removeStones();
        KalahBusinessValidationRules.validateBusinessRulesForMove(board, board.getFirstPlayer().getPitById(1));

    }

    private KalahBoard createBoard(){
        KalahBoard board = new KalahBoard();

        Player firstPlayer = new Player(Player.Name.FIRST_PLAYER);
        Player secondPlayer = new Player(Player.Name.SECOND_PLAYER);

        board.setFirstPlayer(firstPlayer);
        board.setSecondPlayer(secondPlayer);
        board.setCurrentPlayer(firstPlayer);

        return board;
    }

}
