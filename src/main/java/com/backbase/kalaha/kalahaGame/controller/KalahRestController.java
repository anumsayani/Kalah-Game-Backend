package com.backbase.kalaha.kalahaGame.controller;

import com.backbase.kalaha.kalahaGame.service.KalahBoardEngineInterface;
import com.backbase.kalaha.kalahaGame.model.KalahBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class KalahRestController {

    @Autowired
    private KalahBoardEngineInterface kalahGameEngine;

    @PostMapping(path="/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<KalahBoard> create(){
        KalahBoard board = kalahGameEngine.createBoard();
        Resource<KalahBoard> boardResource = new Resource<KalahBoard>(board);
        ControllerLinkBuilder uri = linkTo(methodOn(this.getClass()).findById(board.getBoardId()));
        boardResource.add(uri.withRel("uri")); //name mentioned in specs
        return boardResource;
    }

    @GetMapping(path="/games/{boardId}")
    public KalahBoard findById(@PathVariable String id){
        KalahBoard board = kalahGameEngine.findBoardById(id);
        return board;
    }


}
