package com.backbase.kalaha.kalahaGame.enumerations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GameStatusTest {

    @Test
    public void checkGameStatusWithSuccess() throws Exception {
        System.out.println("Name");
        System.out.println("NAME IS" + GameStatus.GAME_CREATED.name());
        assertThat(GameStatus.GAME_CREATED.name(), is("GAME_CREATED"));
        assertThat(GameStatus.IN_PROGRESS.name(), is("IN_PROGRESS"));
        assertThat(GameStatus.FIRST_PLAYER_WON.name(), is("FIRST_PLAYER_WON"));
        assertThat(GameStatus.SECOND_PLAYER_WON.name(), is("SECOND_PLAYER_WON"));

    }

}
