package com.backbase.kalaha.kalahaGame.controller;

import com.backbase.kalaha.kalahaGame.controller.dataTransferObject.GameStatusDTO;
import com.backbase.kalaha.kalahaGame.controller.dataTransferObject.KalahBoardDTO;
import com.backbase.kalaha.kalahaGame.service.KalahBoardService;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author afatima
 * @desc Rest API Controller for Kalah Game
 */
@RestController
public class KalahRestController {

    @Autowired
    private KalahBoardService kalahGameService;

    /**
     *
     * @return HATEOAS resource with boardID and Links having link to the game baord
     */
    @PostMapping(path="/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<KalahBoard> create(){
        KalahBoard board = kalahGameService.createBoard();
        Resource<KalahBoard> boardResource = new Resource<KalahBoard>(board);
        ControllerLinkBuilder uri = linkTo(methodOn(this.getClass()).findById(board.getBoardId()));
        boardResource.add(uri.withRel("uri")); //name mentioned in specs
        return boardResource;
    }

    /**
     *
     * @param boardId
     * @return Displays the game status
     */
    @GetMapping(path="/games/{boardId}")
    public GameStatusDTO findById(@PathVariable String boardId){
        KalahBoard board = kalahGameService.findBoardById(boardId);
        return GameStatusDTO.createDTO(board);

    }

    /**
     *
     * @param boardId
     * @param pitId
     * @return returns the Board DTO after making a move on the board.
     */
    @PutMapping(path="/games/{boardId}/pits/{pitId}")
    public Resource<KalahBoardDTO> move(@PathVariable String boardId, @PathVariable int pitId){
        KalahBoard board = kalahGameService.move(boardId, pitId);


        KalahBoardDTO boardDto = KalahBoardDTO.createDTO(board);
        Resource<KalahBoardDTO> boardDTOResource = new Resource<KalahBoardDTO>(boardDto);
        ControllerLinkBuilder uri = linkTo(methodOn(this.getClass()).findById(board.getBoardId()));

        boardDTOResource.add(uri.withRel("uri")); //name mentioned in specs
        return boardDTOResource;

    }


}
