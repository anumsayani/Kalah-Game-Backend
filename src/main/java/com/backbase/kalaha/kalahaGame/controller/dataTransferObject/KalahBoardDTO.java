package com.backbase.kalaha.kalahaGame.controller.dataTransferObject;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Pit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author afatima
 * @desc   The class is used to transfer the data over controller,
 * when a move is played on the kalah board.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KalahBoardDTO {

    @Getter
    @Setter
    private String boardId;

    @Getter
    @Setter
    private HashMap<Integer, Integer> status;

    /**
     *
     * @param board
     * @return KalahBoardDTO Data transfer object for the board
     */
    public static KalahBoardDTO createDTO(KalahBoard board){
        KalahBoardDTO dto = new KalahBoardDTO();
        HashMap<Integer, Integer> statusMap = new HashMap<Integer, Integer>();

        dto.setBoardId(board.getBoardId());

        List<Pit> pits = new ArrayList<Pit>();
        pits.addAll(board.getFirstPlayer().getPits());
        pits.add(board.getFirstPlayer().getKalah());
        pits.addAll(board.getSecondPlayer().getPits());
        pits.add(board.getSecondPlayer().getKalah());


       pits.stream().forEach(o -> statusMap.put(o.getPitId(), o.getStones()));

       dto.setStatus(statusMap);

       return dto;

    }



}
