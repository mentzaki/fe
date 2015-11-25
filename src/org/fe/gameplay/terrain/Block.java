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

import org.fe.graphics.FColor;
import org.fe.graphics.FImage;

/**
 *
 * @author fax
 */
public class Block {
    public static final int width = 64, height = 64;
    private static int current_block_index;
    public FColor color;
    public static Block[] blocks = new Block[128];
    boolean solid;
    int index;
    public FImage main, left_top, right_top, left_bottom, right_bottom;

    public Block(boolean solid, int r, int g, int b) {
        color = new FColor(r, g, b);
        index = current_block_index++;
        blocks[index] = this;
        this.solid = solid;
    }

    public Block(String texture, boolean solid, int r, int g, int b) {
        color = new FColor(r, g, b);
        index = current_block_index++;
        blocks[index] = this;
        this.solid = solid;
        texture = "tiles/" + texture;
        main = new FImage(texture + "/main");
        left_top = new FImage(texture + "/left_top");
        right_top = new FImage(texture + "/right_top");
        left_bottom = new FImage(texture + "/left_bottom");
        right_bottom = new FImage(texture + "/right_bottom");
    }

    public void render(int x, int y) {
        main.draw(x * width, y * height / 2);
    }

    public void expand(int x, int y, int fx, int fy) {
        if (right_top != null) {
            if (fx >= x) {
                if (fy >= y) {
                    right_bottom.draw(x * width, y * height / 2);
                }
                if (fy <= y) {
                    right_top.draw(x * width, y * height / 2);
                }
            }
            if (fx <= x) {
                if (fy >= y) {
                    left_bottom.draw(x * width, y * height / 2);
                }
                if (fy <= y) {
                    left_top.draw(x * width, y * height / 2);
                }
            }
        }
    }

    public void update() {

    }
}
