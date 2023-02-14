package com.example.playground.Classes;

import com.example.playground.R;
import java.util.ArrayList;
import java.util.List;


public class MyGamesList {

    private List<Game> games;

    public MyGamesList() {
        games = new ArrayList<Game>();
    }

    public List<Game> getGamesList() {
        return games;
    }

    public void filterList(String text, GamesRecycleViewAdapter gamesRecycleViewAdapter) {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game: games) {
            if (game.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredGames.add(game);
            }
        }
        if (filteredGames.isEmpty()) {
            return;
        }
        else {
            gamesRecycleViewAdapter.setFilteredGames(filteredGames);
        }
    }

    public void initMyGamesList() {
        games.add(new Game("A.V.A", R.drawable.ava_logo));
        games.add(new Game("Black desert", R.drawable.black_desert_logo));
        games.add(new Game("Dark souls 3", R.drawable.ds3_logo));
        games.add(new Game("League of legends", R.drawable.league_icon));
        games.add(new Game("Maple story", R.drawable.maplestory_logo));
        games.add(new Game("Minecraft", R.drawable.minecraft_logo));
        games.add(new Game("Pubg", R.drawable.pubg_logo));
        games.add(new Game("Sea of thieves", R.drawable.sea_of_thieves_logo));
        games.add(new Game("Terraria", R.drawable.terraria_logo));
        games.add(new Game("Velhaim", R.drawable.velhaim_logo));
        games.add(new Game("Warzone", R.drawable.warzone_logo));
        games.add(new Game("World of warcraft", R.drawable.wow_logo));
    }
}
