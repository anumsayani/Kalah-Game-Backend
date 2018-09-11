package com.backbase.kalaha.kalahaGame.repository.impl;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.repository.KalahBoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class KalahBoardRepositoryImplTest {


    @Test
    public void whenCreateBoard_verifyBoardInRepository(){
        KalahBoardRepository boardRepository = new KalahBoardRepositoryImpl();

        KalahBoard board = new KalahBoard();
        boardRepository.saveKalahBoard(board);


        assertNotNull(boardRepository.findKalahBoardByID(board.getBoardId()));
        assertEquals(boardRepository.findKalahBoardByID(board.getBoardId()).getBoardId(), board.getBoardId());
    }
}
