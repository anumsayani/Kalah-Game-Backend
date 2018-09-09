package com.backbase.kalaha.kalahaGame.model;


import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(of = "boardId")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KalahBoard {

    @Getter
    @JsonProperty
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


    public KalahBoard() {
        this.boardId = String.valueOf(new Date().getTime());
        this.createdDate = new Date();
        this.gameStatus = GameStatus.GAME_CREATED;
    }

    @JsonIgnore
    public Player getPlayerWithTurn() {

        return (currentPlayer == null || currentPlayer == firstPlayer) ? firstPlayer : secondPlayer;
    }

}
