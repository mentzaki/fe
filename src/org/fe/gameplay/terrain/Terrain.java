/*
 * Copyright (C) 2016 yew_mentzaki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fe.gameplay.terrain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.Main;
import org.fe.gameplay.types.Chassis;

/**
 *
 * @author yew_mentzaki
 */
public class Terrain {

    private byte[][] tiles;
    public int width, height;

    public Biome biome;
    public int tick;

    public Terrain() {

        biome = new Biome("south");

        width = 64;
        height = 64;
        tiles = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = (byte) (Main.RANDOM.nextInt(20) == 0 ? -127 : -128);
            }
        }
    }

    public Terrain(String name) {

        biome = new Biome("south");
        
        try {
            InputStream in = null;
            try {
                in = new FileInputStream("data/maps/" + name);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Terrain.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte bt[] = new byte[1];
            in.read(bt);
            width = bt[0] + 129;
            in.read(bt);
            height = bt[0] + 129;
            tiles = new byte[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    in.read(bt);
                    tiles[x][height - 1 - y] = bt[0];
                }
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Terrain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTileID(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return Math.min(tiles[x][y] + 128, biome.tiles.length - 1);
        } else {
            return 0;
        }
    }

    public void setTileID(int id, int x, int y) {
        if (x >= 0 && x >= 0 && x < width && y < height) {
            tiles[x][y] = (byte) (id - 128);
        }
    }

    public Block getTile(int x, int y) {
        return biome.tiles[getTileID(x, y)];
    }

    public void render(int cx, int cy, int w, int h) {
        for (int x = cx / Block.WIDTH - 1; x < (cx + w) / Block.WIDTH + 1; x++) {
            for (int y = cy / Block.HEIGHT - 1; y < (cy + h) / Block.HEIGHT + 1; y++) {
                getTile(x, y).render(x, y, tick);
            }
        }
    }

    public boolean[][] getMap(Chassis type) {
        boolean[][] b = new boolean[width][height];
        if (type == Chassis.TRACKS) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    b[x][y] = getTile(x, y).passable;
                }
            }
        }
        return b;
    }

}
