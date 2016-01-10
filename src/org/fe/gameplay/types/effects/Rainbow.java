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
import org.fe.graphics.FImage;
import org.fe.gameplay.types.Effect;
import org.newdawn.slick.Color;

import static org.lwjgl.opengl.GL11.*;
import static java.lang.Math.*;

/**
 *
 * @author yew_mentzaki
 */
public class Rainbow extends Effect {

    public Entity a, b;
    double x1, y1, x2, y2;

    static FImage rainbow = new FImage("effects/rainbow_attack");

    public Rainbow(Entity a, Entity b) {
        this.a = a;
        this.b = b;
        this.x2 = b.x;
        this.y2 = b.y / 2;
        this.life = 25;
    }

    @Override
    public boolean onScreen(int cx, int cy, int w, int h) {
        this.x1 = a.x;
        this.y1 = a.y / 2;
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
        glColor4f(1, 1, 1, ((float) life) / 50f);

        rainbow.bind();

        double d = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
        double a = atan2(y2 - y1, x2 - x1);

        glBegin(GL_POLYGON);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x1 + cos(a + Math.PI) * 5, y1 + sin(a + Math.PI) * 2);
            glTexCoord2d(1, 0);
            glVertex2d(x1 + cos(a + 0.07) * d, y1 + sin(a + 0.07) * d);
            glTexCoord2d(1, 1);
            glVertex2d(x1 + cos(a - 0.07) * d, y1 + sin(a - 0.07) * d);
            glTexCoord2d(0, 1);
            glVertex2d(x1 - cos(a + Math.PI) * 5, y1 - sin(a + Math.PI) * 2);
        }
        glEnd();
        glBegin(GL_POLYGON);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x1 + cos(a + Math.PI) * 2, y1 + sin(a + Math.PI) * 2);
            glTexCoord2d(1, 0);
            glVertex2d(x1 + cos(a + 0.05) * d, y1 + sin(a + 0.05) * d);
            glTexCoord2d(1, 1);
            glVertex2d(x1 + cos(a - 0.05) * d, y1 + sin(a - 0.05) * d);
            glTexCoord2d(0, 1);
            glVertex2d(x1 - cos(a + Math.PI) * 2, y1 - sin(a + Math.PI) * 2);
        }
        glEnd();
        glBindTexture(GL_TEXTURE_2D, 0);

    }
}
