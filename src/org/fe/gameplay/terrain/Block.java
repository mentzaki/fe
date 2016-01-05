/*
 * Copyright (C) 2015 fax
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
import org.fe.graphics.FImage;

/**
 *
 * @author fax
 */
public class Block {

    public static final int WIDTH = 64, HEIGHT = 32;

    static FImage waterTile = new FImage("tiles/south/water/0");
    FImage fi;
    FImage fis[];
    public boolean passable = true, water;

    public Block() {
    }

    public void render(int x, int y, int tick) {
        waterTile.draw(x * WIDTH, y * HEIGHT);
        if (fi != null) {
            fi.draw(x * WIDTH, y * HEIGHT);
        }else{
            fis[(tick - tick / fis.length / 30 * fis.length * 30) / 30].draw(x * WIDTH, y * HEIGHT);
        }
    }

    public void init(File f) {
        if (f.isFile()) {
            fi = new FImage(
                    f.getAbsolutePath().substring(
                            new File("res/textures").getAbsolutePath().length() + 1
                    ).replace(".png", "")
            );
        } else {
            File[] fs = f.listFiles();
            fis = new FImage[fs.length];
            for (int i = 0; i < fs.length; i++) {
                fis[i] = new FImage(
                        f.getAbsolutePath().substring(
                                new File("res/textures").getAbsolutePath().length() + 1
                        ) + "/" + i
                );
            }
        }

    }

}
