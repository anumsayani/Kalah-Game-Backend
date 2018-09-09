package com.backbase.kalaha.kalahaGame.model;


import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(of = "boardId")
public class KalahBoard {

    @Getter
    private String boardId;

    @Getter
    @Setter
    @JsonIgnore
    private Player firstPlayer;

    @Getter
    @Setter
    @JsonIgnore
    private Player secondPlayer;

    @Getter
    @JsonIgnore
    private Date createdDate;

    @Getter
    @Setter
    @JsonIgnore
    private GameStatus gameStatus;

    @Getter
    @Setter
    @JsonIgnore
    private Player currentPlayer;

    @Getter
    private int[] pits;

    public KalahBoard() {
        this.boardId = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.gameStatus = GameStatus.GAME_CREATED;
        this.currentPlayer = firstPlayer;
        this.pits = new int[14];
    }

    public Player getPlayerWithTurn() {

        return (currentPlayer == null || currentPlayer == firstPlayer) ? firstPlayer : secondPlayer;
    }

}
