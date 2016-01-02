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

    static final int WIDTH = 64, HEIGHT = 32;
    
    FImage fi;
    public boolean passable = true, water;

    public Block() {
    }
    
    public void render(int x, int y, int tick){
        if(fi != null){
            fi.draw(x * WIDTH, y * HEIGHT);
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

        }

    }

}
