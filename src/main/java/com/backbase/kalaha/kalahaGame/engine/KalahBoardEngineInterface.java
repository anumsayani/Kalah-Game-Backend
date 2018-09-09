package com.backbase.kalaha.kalahaGame.engine;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Player;

import java.util.Collection;
import java.util.HashMap;

public interface KalahBoardEngineInterface {
    KalahBoard createBoard();
    KalahBoard move(String boardId, Integer pitId);
    KalahBoard findBoardById(String boardId);

}
