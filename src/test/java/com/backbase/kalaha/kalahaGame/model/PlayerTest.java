package com.backbase.kalaha.kalahaGame.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    @Test
    public void newPlayer() throws Exception {
        Player firstPlayer = new Player(Player.Name.FIRST_PLAYER);
        Player secondPlayer = new Player(Player.Name.SECOND_PLAYER);

        assertThat(firstPlayer.getName().name, is(Player.Name.FIRST_PLAYER.name));
        assertThat(firstPlayer.getPits().size(), is(6));

        List<Pit> pits = firstPlayer.getPits();
        pits.addAll(secondPlayer.getPits());

        pits.stream().forEach(o -> assertThat(o.getStones(), is(6)));

        assertThat(firstPlayer.getKalah().getStones(), is(0));
        assertThat(secondPlayer.getKalah().getStones(), is(0));
    }

    @Test
    public void getPitByIdWithSuccess() throws Exception{
        Player firstPlayer = new Player(Player.Name.FIRST_PLAYER);
        Player secondPlayer = new Player(Player.Name.SECOND_PLAYER);

        List<Pit> pits = firstPlayer.getPits();
        pits.addAll(secondPlayer.getPits());

        pits.stream().forEach(o -> assertThat(o.getPitId(), lessThan(14)));

    }

}

