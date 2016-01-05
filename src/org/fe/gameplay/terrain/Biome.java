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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FData;

/**
 *
 * @author yew_mentzaki
 */
public class Biome {

    Block[] tiles;

    public Biome(String name) {
        FData fdata = null;
        try {
            fdata = FData.fromFile("data/biomes/" + name + ".s");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Biome.class.getName()).log(Level.SEVERE, null, ex);
        }
        tiles = new Block[fdata.get("tile_length").toInteger()];
        for (int i = 0; i < tiles.length; i++) {
            File f = new File("res/textures/tiles/" + name + "/" + i + ".png");
                tiles[i] = new Block();
            if (f.exists()) {
                tiles[i].init(f);
            } else {
                f = new File("res/textures/tiles/" + name + "/" + i);
                if (f.exists()) {
                    tiles[i].init(f);
                }
            }
            FData tile = fdata.get("tiles").get(String.valueOf(i));
            
                System.out.println("tile " + i + ":");
                tiles[i].passable = tile.get("passable").toBoolean(true);
                System.out.println("   passable - " + tiles[i].passable);
                tiles[i].water = tile.get("water").toBoolean(false);
                System.out.println("      water - " + tiles[i].water);
            
        }
    }

}
