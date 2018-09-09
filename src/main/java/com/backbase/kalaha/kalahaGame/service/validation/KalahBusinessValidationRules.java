package com.backbase.kalaha.kalahaGame.service.validation;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.exception.BusinessException;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Pit;
import com.backbase.kalaha.kalahaGame.model.Player;

public class KalahBusinessValidationRules {

    public static void validateBusinessRulesForMove(KalahBoard board, Pit pit){
        //move cannot happen if the game isnt in progress
        if(!board.getGameStatus().equals(GameStatus.IN_PROGRESS)){
            throw new BusinessException("Game is not in progress. Some one may have already won the game");
        }

        //Are there any stones in the pit ?
        if(pit.getStones() == 0){
            throw new BusinessException("There are no stones in this pit. Please use other pits");
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
