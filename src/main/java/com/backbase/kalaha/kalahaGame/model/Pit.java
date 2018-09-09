package com.backbase.kalaha.kalahaGame.model;

import lombok.Getter;
import lombok.Setter;

public class Pit {
    @Getter
    private int pitId;

    @Getter
    @Setter
    private int stones;


    public Pit(int pitId, int stones) {
        this.pitId = pitId;
        this.stones = stones;
    }

    public void addStone() {
        stones++;
    }

    public int removeStones() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }
}
