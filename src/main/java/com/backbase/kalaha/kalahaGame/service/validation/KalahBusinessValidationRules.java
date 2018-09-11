package com.backbase.kalaha.kalahaGame.service.validation;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.exception.KalahBusinessException;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Pit;
import com.backbase.kalaha.kalahaGame.model.Player;

/**
 * @author  afatima
 * Validates business rules for kalah
 *
 */
public class KalahBusinessValidationRules {

    public static void validateBusinessRulesForMove(KalahBoard board, Pit pit){
        //move cannot happen if the game isnt in progress
        if(!board.getGameStatus().equals(GameStatus.IN_PROGRESS)){
            throw new KalahBusinessException("Game is not in progress. Some one may have already won the game");
        }

        //player should have the pit from which the stones are moved
        if(pit == null){
            throw new KalahBusinessException("Please make a move from your pits");
        }

        //player shouldnt distribute stones from his kalah
        if(pit.getPitId() == board.getCurrentPlayer().getKalah().getPitId()){
            throw new KalahBusinessException("You cannot move a stone from your kalah");
        }

        //Are there any stones in the pit ?
        if(pit.getStones() == 0){
            throw new KalahBusinessException("There are no stones in this pit. Please use other pits");
        }

    }

    public static boolean validateBusinessRulesForGameTermination(KalahBoard kalahBoard){
        int stoneCountPlayer1 = getTotalStones(kalahBoard.getFirstPlayer());
        int stoneCountPlayer2 = getTotalStones(kalahBoard.getSecondPlayer());
        if(stoneCountPlayer1 == 0 || stoneCountPlayer2 == 0){
            return true;
        }

        return false;
    }

    public static int getTotalStones(Player player) {
        int count = 0;
        for(Pit pit : player.getPits()){
            count += pit.getStones();
        }
        return count;
    }
}
