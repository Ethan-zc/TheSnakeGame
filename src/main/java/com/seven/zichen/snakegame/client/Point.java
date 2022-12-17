package com.seven.zichen.snakegame.client;

import com.seven.zichen.snakegame.utilities.GameOptions;

import java.awt.*;

// Les points sont deux coordonnees et une couleur
class Point {
    int x, y;
    Color color;
    private final int taille = GameOptions.gridSize;

    Point(int x, int y) {
        while (x < 0)
            x += taille;
        while (y < 0)
            y += taille;
        this.x = x % taille;
        this.y = y % taille;
        color = Color.white;
    }

    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
