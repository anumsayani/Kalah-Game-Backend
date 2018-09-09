package com.backbase.kalaha.kalahaGame.model;


import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(of = "boardId")
public class KalahBoard {

    @Getter
    private String boardId;

    @Getter
    @Setter
    private Player firstPlayer;

    @Getter
    @Setter
    private Player secondPlayer;

    @Getter
    private Date createdDate;

    @Getter
    @Setter
    private GameStatus gameStatus;

    @Getter
    @Setter
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
