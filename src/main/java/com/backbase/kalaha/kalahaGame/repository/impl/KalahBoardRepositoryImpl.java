package com.backbase.kalaha.kalahaGame.repository.impl;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.repository.KalahBoardRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class KalahBoardRepositoryImpl implements KalahBoardRepository {
    private HashMap<String, KalahBoard> gameBoards = new HashMap<String, KalahBoard>();
    @Override
    public KalahBoard saveKalahBoard(KalahBoard board) {
        return gameBoards.put(board.getBoardId(), board);
    }

    @Override
    public KalahBoard findKalahBoardByID(String id) {
        return gameBoards.get(id);
    }
}
