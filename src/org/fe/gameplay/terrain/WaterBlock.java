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

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.fe.graphics.FImage;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

/**
 *
 * @author fax
 */
public class WaterBlock extends Block{
    
    double date = 0;
    FImage shine;

    public WaterBlock(boolean solid, int r, int g, int b) {
        super(solid, r, g, b);
        main = new FImage("tiles/water/main");
        shine = new FImage("tiles/water/shine");
    }

    @Override
    public void update() {
        date += 0.04;
    }

    @Override
    public void render(int x, int y) {
        main.bind();
        glColor3f(1, 1, 1);
        double n = 0.2 * (x % 2 == 0 ? 1 : -1) * (y % 2 == 0 ? 1 : -1);
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0 + cos(date) * n, 0 + cos(-date) * n);
            glVertex2d(x * width, y * height / 2);
            glTexCoord2d(main.getTextureWidth() - sin(date) * n, 0 - cos(-date) * n);
            glVertex2d((x + 1) * width, y * height / 2);
            glTexCoord2d(main.getTextureWidth() + sin(date) * n, main.getTextureHeight() - sin(-date) * n);
            glVertex2d((x + 1) * width, (y + 1) * height / 2);
            glTexCoord2d(0 - cos(date) * n, main.getTextureHeight() + sin(-date) * n);
            glVertex2d(x * width, (y + 1) * height / 2);
        }
        glEnd();
        shine.bind();
        glBegin(GL_QUADS);
        {
            glColor4d(1, 1, 1, cos(date / 2) * n * 2 + 0.4);
            glTexCoord2d(0 + cos(-date) * n, 0 + cos(date) * n);
            glVertex2d(x * width, y * height / 2);
            glColor4d(1, 1, 1, -sin(date / 2) * n * 2 + 0.4);
            glTexCoord2d(main.getTextureWidth() - sin(-date) * n, 0 - cos(date) * n);
            glVertex2d((x + 1) * width, y * height / 2);
            glColor4d(1, 1, 1, sin(date / 2) * n * 2 + 0.4);
            glTexCoord2d(main.getTextureWidth() + sin(-date) * n, main.getTextureHeight() - sin(date) * n);
            glVertex2d((x + 1) * width, (y + 1) * height / 2);
            glColor4d(1, 1, 1, -cos(date / 2) * n * 2 + 0.4);
            glTexCoord2d(0 - cos(-date) * n, main.getTextureHeight() + sin(date) * n);
            glVertex2d(x * width, (y + 1) * height / 2);
        }
        glEnd();
    }
}
