package com.backbase.kalaha.kalahaGame.controller.dataTransferObject;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Pit;
import com.backbase.kalaha.kalahaGame.model.Player;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author  afatima
 * @Desc Game status dto, created by Rest controller.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameStatusDTO {

    @Getter
    @Setter
    private String boardId;

    @Getter
    @Setter
    private GameStatus gameStatus;

    @Getter
    @Setter
    private String currentPlayerName;

    @Getter
    @Setter
    private Date createdDate;

    public static GameStatusDTO createDTO(KalahBoard board){
        GameStatusDTO dto = new GameStatusDTO();

        dto.setBoardId(board.getBoardId());
        dto.setGameStatus(board.getGameStatus());
        dto.setCreatedDate(board.getCreatedDate());
        dto.setCurrentPlayerName(board.getCurrentPlayer().getName().name());

        return dto;

    }
}
