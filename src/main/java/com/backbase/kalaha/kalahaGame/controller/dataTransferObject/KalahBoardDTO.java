package com.backbase.kalaha.kalahaGame.controller.dataTransferObject;

import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import com.backbase.kalaha.kalahaGame.model.Pit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KalahBoardDTO {

    @Getter
    @Setter
    private String boardId;

    @Getter
    @Setter
    private HashMap<Integer, Integer> status;

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
