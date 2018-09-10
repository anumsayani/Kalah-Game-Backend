package com.backbase.kalaha.kalahaGame.repository;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;

public interface KalahBoardRepository {
    KalahBoard saveKalahBoard(KalahBoard board);
    KalahBoard findKalahBoardByID(String id);
}
