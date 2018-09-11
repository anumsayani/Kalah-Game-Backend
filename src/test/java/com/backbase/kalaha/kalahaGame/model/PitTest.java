package com.backbase.kalaha.kalahaGame.model;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PitTest {


    @Test
    public void whenInitializePit_verifyStonesInPit() throws Exception {
        Pit pitOne = new Pit(1, Player.STONES_PER_PIT);
        Pit pitTwo = new Pit(2, Player.STONES_PER_PIT);

        assertThat(pitOne.getStones(), is(6));
        assertThat(pitTwo.getStones(), is(6));
    }


    @Test
    public void equalsAndHashCodeWithSuccess() throws Exception {
        Pit pitOne = new Pit(1, Player.STONES_PER_PIT);
        Pit pitTwo = new Pit(2, Player.STONES_PER_PIT);

        assertFalse(pitOne.equals(pitTwo));
    }



}
