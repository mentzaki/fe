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

import org.fe.Main;
import org.fe.graphics.FImage;
import org.newdawn.slick.Color;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author yew_mentzaki
 */
public class FogOfWar {

    byte[][] fw = null;
    int width, height;

    public FogOfWar(int width, int height) {
        width *= 2;
        width++;
        height *= 2;
        height++;
        this.width = width;
        this.height = height;
        fw = new byte[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fw[i][j] = -128;
            }
        }
    }

    private void openPoint(int n, int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (n > 255) {
                n = 255;
            }
            if (n < 0) {
                n = 0;
            }
            if (fw[x][y] + 128 < n) {
                fw[x][y] = (byte) (n - 128);
            }
        }
    }

    public boolean isVisible(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return fw[x][y] > 0;
        }
        return false;
    }

    private float getPoint(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            float f = fw[x][y] + 128;
            f /= 256;
            return f;
        }
        return 0;
    }

    public void open(int size, int x, int y) {
        x /= 32;
        y /= 32;
        for (int i = -size; i < size; i++) {
            for (int j = -size; j < size; j++) {
                int d = (int) ((1 - Math.sqrt(i * i + j * j) / size) * 255);
                openPoint(d, x + i, y + j);
            }
        }
    }

    public void tick() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (fw[x][y] > 0) {
                    fw[x][y]--;
                }
            }
        }
    }

    public void render(int cx, int cy, int w, int h) {
        FImage.unbind();
        for (int x = cx / 32 - 1; x < (cx + w) / 32 + 1; x++) {
            for (int y = cy / 16 - 1; y < (cy + h) / 16 + 1; y++) {
                glBegin(GL_POLYGON);
                {
                    glColor4f(0.05f, 0, 0.07f, 1f - getPoint(x, y));
                    glVertex2d(x * 32, y * 16);
                    glColor4f(0.05f, 0, 0.07f, 1f - getPoint(x + 1, y));
                    glVertex2d((x + 1) * 32, y * 16);
                    glColor4f(0.05f, 0, 0.07f, 1f - getPoint(x + 1, y + 1));
                    glVertex2d((x + 1) * 32, (y + 1) * 16);
                    glColor4f(0.05f, 0, 0.07f, 1f - getPoint(x, y + 1));
                    glVertex2d(x * 32, (y + 1) * 16);
                }
                glEnd();

            }
        }
    }

}
