/*
 * Copyright (C) 2015 yew_mentzaki
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
package org.fe.gui;

import java.io.File;
import static org.lwjgl.opengl.GL11.*;
import org.fe.Main;
import org.fe.graphics.FColor;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class FBackground {

    public static final FBackground BASIC_BACKGROUND = new FBackground("basic_background");

    public FImage image[];

    public FBackground(String path) {
        int i = new File("res/textures/gui/" + path).list().length;
        image = new FImage[i];
        for (int j = 0; j < i; j++) {
            image[j] = new FImage("gui/" + path + "/" + j);

        }
    }

    public void render(double x, double y, double w, double h) {
        int i = 0;
        while (i < image.length - 1 && Main.RANDOM.nextInt(i + 2) == 0) {
            i++;
        }
        image[i].bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x, y);
            glTexCoord2d(((double) w) / image[i].getWidth() * image[i].getTextureWidth(), 0);
            glVertex2d(x + w, y);
            glTexCoord2d(((double) w) / image[i].getWidth() * image[i].getTextureWidth(), ((double) h) / image[i].getHeight() * image[i].getTextureHeight());
            glVertex2d(x + w, y + h);
            glTexCoord2d(0, ((double) h) / image[i].getHeight() * image[i].getTextureHeight());
            glVertex2d(x, y + h);
        }
        glEnd();
    }

    public void render(double x, double y, double w, double h, FColor c) {
        c.bind();
        render(x, y, w, h);
    }

    public void render(double x, double y, double w, double h, FColor c1, FColor c2, FColor c3, FColor c4) {
        int i = 0;
        while (i < image.length - 1 && Main.RANDOM.nextInt(i + 2) == 0) {
            i++;
        }
        image[i].bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            c1.bind();
            glVertex2d(x, y);
            glTexCoord2d(((double) w) / image[i].getWidth() * image[i].getTextureWidth(), 0);
            c2.bind();
            glVertex2d(x + w, y);
            glTexCoord2d(((double) w) / image[i].getWidth() * image[i].getTextureWidth(), ((double) h) / image[i].getHeight() * image[i].getTextureHeight());
            c3.bind();
            glVertex2d(x + w, y + h);
            glTexCoord2d(0, ((double) h) / image[i].getHeight() * image[i].getTextureHeight());
            c4.bind();
            glVertex2d(x, y + h);
        }
        glEnd();
    }
}
