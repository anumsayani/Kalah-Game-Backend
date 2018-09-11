package com.backbase.kalaha.kalahaGame.controller;

import com.backbase.kalaha.kalahaGame.controller.dataTransferObject.KalahBoardDTO;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Player;
import com.backbase.kalaha.kalahaGame.service.KalahBoardService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class KalahRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KalahBoardService kalahGameService;

    @Test
    public void whenCreateBoard_verifyBoardId() throws Exception{
        KalahBoard board = new KalahBoard();
        Mockito.when(kalahGameService.createBoard()).thenReturn(board);

        mvc.perform(post("/games"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.boardId").value(board.getBoardId()))
                .andExpect(jsonPath("$.links").exists());

    }

    @Test
    public void whenFindByBoardId_VerifyBoard() throws Exception{
        KalahBoard board = new KalahBoard();

        board.setCurrentPlayer(new Player(Player.Name.FIRST_PLAYER));

        String json = "{" +
                "    \"boardId\": \"%s\"," +
                "    \"gameStatus\": \"%s\"," +
                "    \"currentPlayerName\": \"%s\"," +
                "    \"createdDate\": \"%s\"" +
                "}";
        json = String.format(json, board.getBoardId()
                            , board.getGameStatus(),
                                board.getCurrentPlayer().getName().name(), board.getCreatedDate());

        Mockito.when(kalahGameService.findBoardById(board.getBoardId())).thenReturn(board);

        mvc.perform(get("/games/{boardId}",board.getBoardId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.boardId").value(board.getBoardId()))
                .andExpect(jsonPath("$.gameStatus").value(board.getGameStatus().name()))
                .andExpect(jsonPath("$.currentPlayerName")
                        .value(board.getCurrentPlayer().getName().name()));
    }

    @Test
    public void whenMoveStone_verifyUpdatedStatus() throws Exception{
        KalahBoard board = new KalahBoard();
        Player player1 = new Player(Player.Name.FIRST_PLAYER);
        Player player2 = new Player(Player.Name.SECOND_PLAYER);

        board.setFirstPlayer(player1);
        board.setSecondPlayer(player2);
        board.setCurrentPlayer(player1);

        player1.getPitById(1).removeStones();
        player1.getPits().stream().filter(o -> o.getPitId() > 1).forEach(o -> o.addStone());
        player1.getKalah().addStone();

        Mockito.when(kalahGameService.move(board.getBoardId(), 1)).thenReturn(board);
        String json = "{\"boardId\":\"%s\",\"status\":{\"1\":0,\"2\":7,\"3\":7,\"4\":7,\"5\":7,\"6\":7,\"7\":1,\"8\":6,\"9\":6,\"10\":6,\"11\":6,\"12\":6,\"13\":6,\"14\":0},\"links\":[{\"rel\":\"uri\",\"href\":\"http://localhost/games/%s\"}]}";
        json = String.format(json, board.getBoardId(), board.getBoardId());

        mvc.perform(put("/games/{boardId}/pits/{pitId}", board.getBoardId(), 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json));

    }
}
