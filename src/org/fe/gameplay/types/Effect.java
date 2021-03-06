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
package org.fe.gameplay.types;

/**
 *
 * @author yew_mentzaki
 */
public class Effect {

    public boolean remove = false;
    public int life = 100;
    public int x, y;
    public int width = 100;

    public boolean onScreen(int cx, int cy, int w, int h) {
        return (x - width / 2 <= cx + w
                && x + width / 2 >= cx
                && y - width / 2 <= cy + h * 2
                && y + width / 2 >= cy);
    }

    public void tick() {
        life--;
        if (life <= 0) {
            remove = true;
        }
    }

    public void render() {

    }
}
