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
package org.fe.gameplay.types.effects;

import org.fe.Main;
import org.fe.gameplay.types.Entity;
import org.fgameplay.types.Effect;
import org.newdawn.slick.Color;

/**
 *
 * @author yew_mentzaki
 */
public class Laser extends Effect {
    public Entity a, b;
    double x1, y1, x2, y2;

    public Laser(Entity a, Entity b) {
        this.a = a;
        this.b = b;
        this.life = 25;
    }

    @Override
    public boolean onScreen(int cx, int cy, int w, int h) {
        this.x1 = a.x;
        this.y1 = a.y / 2;
        this.x2 = b.x;
        this.y2 = b.y / 2;
        return (x1 - width / 2 <= cx + w
                && x1 + width / 2 >= cx
                && y1 - width / 2 <= cy + h
                && y1 + width / 2 >= cy)
                || (x2 - width / 2 <= cx + w
                && x2 + width / 2 >= cx
                && y2 - width / 2 <= cy + h
                && y2 + width / 2 >= cy);
    }
    @Override
    public void render() {
        this.x1 = a.x;
        this.y1 = a.y / 2;
        this.x2 = b.x;
        this.y2 = b.y / 2;
        Main.GRAPHICS.setColor(new Color(255, 255, 255, life * 10));
        Main.GRAPHICS.setLineWidth(3);
        Main.GRAPHICS.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        Main.GRAPHICS.setColor(new Color(255, 255, 255, life * 2));
        Main.GRAPHICS.setLineWidth(9);
        Main.GRAPHICS.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        Main.GRAPHICS.setLineWidth(1);
    }
}
