package com.backbase.kalaha.kalahaGame.service.impl;

import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.backbase.kalaha.kalahaGame.exception.KalahException;
import com.backbase.kalaha.kalahaGame.model.*;
import com.backbase.kalaha.kalahaGame.model.repository.KalahBoardRepository;
import com.backbase.kalaha.kalahaGame.service.KalahBoardEngineInterface;
import com.backbase.kalaha.kalahaGame.service.validation.KalahBusinessValidationRules;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.backbase.kalaha.kalahaGame.model.Player.Name.FIRST_PLAYER;
import static com.backbase.kalaha.kalahaGame.model.Player.Name.SECOND_PLAYER;

@Service
@Slf4j
public class KalahBoardEngineInterfaceImpl implements KalahBoardEngineInterface {


    @Autowired
    private KalahBoardRepository boardRepository;


    @Override
    public KalahBoard createBoard() {
        KalahBoard kalahBoard = new KalahBoard();
        //create the players
        Player firstPlayer = new Player(FIRST_PLAYER);
        Player secondPlayer = new Player(SECOND_PLAYER);

        kalahBoard.setFirstPlayer(firstPlayer);
        kalahBoard.setSecondPlayer(secondPlayer);

        //first player will be player one
        kalahBoard.setCurrentPlayer(firstPlayer);

        //save the kalah board
        boardRepository.saveKalahBoard(kalahBoard);

        //return board
        return kalahBoard;

    }

    @Override
    public KalahBoard move(String boardId, Integer pitId) {

        //get the kalah board from the repository
        KalahBoard kalahBoard = boardRepository.findKalahBoardByID(boardId);

        //get the acting player
        Player actingPlayer = kalahBoard.getPlayerWithTurn();

        //retrieve the pit from the pit ID;
        Pit pit = actingPlayer.getPitById(pitId);

        //handle Game State on first move
        handleGameStateOnFirstMove(kalahBoard);

        //validates move business
        KalahBusinessValidationRules.validateBusinessRulesForMove(kalahBoard, pit);

        //if all's good, move the stones!!
        takeTurn(kalahBoard, pit, actingPlayer);
        //anybody won?
        boolean terminateGame = KalahBusinessValidationRules.validateBusinessRulesForGameTermination(kalahBoard);
        if (terminateGame) {
            assignKalahWinner(kalahBoard);
        }

        return kalahBoard;

    }

    @Override
    public KalahBoard findBoardById(String boardId) {
        if (StringUtils.isBlank(boardId)) {
            throw new KalahException("Please specify a board Id to find from board repository");
        }
        return boardRepository.findKalahBoardByID(boardId);
    }

    private void handleGameStateOnFirstMove(KalahBoard kalahBoard) {
        if (kalahBoard.getGameStatus().equals(GameStatus.GAME_CREATED)) {
            //it is the first move after the game has been created,
            //so game is not in_progress
            kalahBoard.setGameStatus(GameStatus.IN_PROGRESS);
        }
    }

    private void assignKalahWinner(KalahBoard kalahBoard) {
        int stones = KalahBusinessValidationRules.getTotalStones(kalahBoard.getFirstPlayer());
        if (stones > 0) {
            kalahBoard.getFirstPlayer().getKalah().setStones(stones);
        } else {
            stones = KalahBusinessValidationRules.getTotalStones(kalahBoard.getSecondPlayer());
            kalahBoard.getSecondPlayer().getKalah().setStones(stones);
        }

        //compute kalah winner
        int kalahStonesPlayer1 = kalahBoard.getFirstPlayer().getKalah().getStones();
        int kalahStonesPlayer2 = kalahBoard.getSecondPlayer().getKalah().getStones();

        if (kalahStonesPlayer1 > kalahStonesPlayer2) {
            kalahBoard.setGameStatus(GameStatus.FIRST_PLAYER_WON);
        } else {
            kalahBoard.setGameStatus(GameStatus.SECOND_PLAYER_WON);
        }
    }


    private void takeTurn(KalahBoard kalahBoard, Pit pit, Player player) {

        int stones = pit.removeStones();

        /*
            RULE 1: Distribute stone
         */
        int distributeFromPitId = pit.getPitId() + 1;
        int currentPitID = distributeStones(kalahBoard, stones, distributeFromPitId);

        /*
            RULE 2: Check if last stone in player Kalah
         */
        if (isLastStoneInKalah(currentPitID, player)) {
            return;
        }

        /*
            RULE 3: If last stone in player's own empty pit then capture opponents stones.
         */
        if (isLastStoneInPlayerEmptyPit(kalahBoard, currentPitID)) {
            captureOpponentPitStones(kalahBoard, currentPitID);
        }

        this.switchTurn(kalahBoard, player);
    }


    private int distributeStones(KalahBoard kalahBoard, int stones, int pitId) {

        Player pitsOwner = this.getPitOwner(kalahBoard, pitId);

        /**
         * Distribute stones to following pits
         */
        int currentPitId = pitId;
        while (stones > 0 && pitsOwner.canAddStoneToPit(currentPitId)) {
            currentPitId = pitId;
            pitsOwner.getPitById(pitId).addStone();
            stones--;
            pitId++;
        }

        /**
         *  Should add stone in player Kalah
         */
        if (shouldAddToPlayerKalah(kalahBoard, pitsOwner, stones)) {
            stones = pitsOwner.addStoneToKalahAndReturnRemaining(stones);
            currentPitId = pitsOwner.getKalah().getPitId();
        }

        /**
         * After adding stones to player pits, now distribute remaining stones in opponents pits.
         */
        if (stones > 0) {
            Player oppositePlayer = getOppositePlayer(kalahBoard, pitsOwner);
            return distributeStones(kalahBoard, stones, oppositePlayer.pitsStartingIndex());
        }

        return currentPitId;

    }

    private boolean isLastStoneInPlayerEmptyPit(KalahBoard kalahBoard, int pitId) {

        Player pitOwner = getPitOwner(kalahBoard, pitId);
        Pit pit = pitOwner.getPitById(pitId);
        boolean isOpponentPit = kalahBoard.getCurrentPlayer().getPitById(pitId) == null;
        return pit.getStones() == 1 && !isOpponentPit;

    }

    private boolean canCaptureOpponentPitStone(KalahBoard kalahBoard, int pitId) {

        return kalahBoard.getCurrentPlayer().getPitById(pitId) != null;
    }

    private boolean isLastStoneInKalah(int pitId, Player player) {
        return pitId == player.getKalah().getPitId() ? true : false;
    }

    private boolean shouldAddToPlayerKalah(KalahBoard kalahBoard, Player player, int stone) {
        return (stone > 0 && kalahBoard.getCurrentPlayer() == player);
    }

    private void captureOpponentPitStones(KalahBoard kalahBoard, int pitId) {

        int oppositePitId = 14 - pitId;
        Player oppositePitOwner = getPitOwner(kalahBoard, oppositePitId);
        Pit oppositePit = oppositePitOwner.getPitById(oppositePitId);
        int OppositePitStones = oppositePit.removeStones();

        Player pitOwner = getPitOwner(kalahBoard, pitId);
        Pit pit = pitOwner.getPitById(pitId);
        int totalStone = pit.getStones() + OppositePitStones;
        pit.setStones(totalStone);


    }


    private void switchTurn(KalahBoard kalahBoard, Player actingPlayer) {
        if (actingPlayer.isFirstPlayer()) {
            kalahBoard.setCurrentPlayer(kalahBoard.getSecondPlayer());
        } else {
            kalahBoard.setCurrentPlayer(kalahBoard.getFirstPlayer());
        }
    }

    private Player getOppositePlayer(KalahBoard kalahBoard, Player actingPlayer) {
        if (actingPlayer.isFirstPlayer()) {
            return kalahBoard.getSecondPlayer();
        } else {
            return kalahBoard.getFirstPlayer();
        }
    }

    private Player getPitOwner(KalahBoard kalahBoard, int pitId) {
        return (pitId < kalahBoard.getSecondPlayer().pitsStartingIndex()) ?
                kalahBoard.getFirstPlayer() : kalahBoard.getSecondPlayer();
    }
}
