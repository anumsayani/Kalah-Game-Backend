package com.backbase.kalaha.kalahaGame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author afatima
 * @desc Every player has a set of pits. A pit has stones subsequently
 */
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

    /**
     * adds one stone in the pit
     */
    public void addStone() {
        stones++;
    }

    /**
     *
     *  removes all stones from the pit.
     * @return returns the number of stones removed
     */
    public int removeStones() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }
}
