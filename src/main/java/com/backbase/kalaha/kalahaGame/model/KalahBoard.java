package com.backbase.kalaha.kalahaGame.model;


import com.backbase.kalaha.kalahaGame.enumerations.GameStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * @author afatima
 * @desc Kalah Board Model
 *
 */
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
        this.boardId = getUniqueID();
        this.createdDate = new Date();
        this.gameStatus = GameStatus.GAME_CREATED;
    }

    /**
     *
     * @return the current player with turn
     */
    @JsonIgnore
    public Player getPlayerWithTurn() {
        return (currentPlayer == null || currentPlayer == firstPlayer) ? firstPlayer : secondPlayer;
    }

    private String getUniqueID(){
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        UUID uuid = UUID.randomUUID();
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        Base64 base64 = new Base64();
        return base64.encodeBase64URLSafeString(bb.array());

    }

}
