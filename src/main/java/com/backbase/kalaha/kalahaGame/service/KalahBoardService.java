package com.backbase.kalaha.kalahaGame.service;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;

public interface KalahBoardService {
    KalahBoard createBoard();
    KalahBoard move(String boardId, Integer pitId);
    KalahBoard findBoardById(String boardId);

}
