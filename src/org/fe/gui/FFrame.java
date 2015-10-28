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

import static org.lwjgl.opengl.GL11.*;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class FFrame {

    public static final FFrame BASIC_FRAME = new FFrame("basic_frame");

    public String path;
    public FImage lt, ct, rt;
    public FImage lc, cc, rc;
    public FImage lb, cb, rb;

    public FFrame(String path) {
        this.path = path;
        lt = new FImage("gui/" + path + "/lt");
        ct = new FImage("gui/" + path + "/ct");
        rt = new FImage("gui/" + path + "/rt");

        lc = new FImage("gui/" + path + "/lc");
        cc = new FImage("gui/" + path + "/cc");
        rc = new FImage("gui/" + path + "/rc");

        lb = new FImage("gui/" + path + "/lb");
        cb = new FImage("gui/" + path + "/cb");
        rb = new FImage("gui/" + path + "/rb");
    }

    public void render(double x, double y, double w, double h) {
        lt.draw(x, y);
        rt.draw(x + w - rt.getWidth(), y);
        lb.draw(x, y + h - rt.getHeight());
        rb.draw(x + w - rt.getWidth(), y + h - rt.getHeight());
        ct.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x+lt.getWidth(), y);
            glTexCoord2d(((double)w)/((double)lt.getWidth()), 0);
            glVertex2d(x+w-rt.getWidth(), y);
            glTexCoord2d(((double)w)/((double)lt.getWidth()), 1);
            glVertex2d(x+w-rt.getWidth(), y+lt.getHeight());
            glTexCoord2d(0, 1);
            glVertex2d(x+lt.getWidth(), y+lt.getHeight());
        }
        glEnd();
        cb.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x+lt.getWidth(), y+h-lt.getHeight());
            glTexCoord2d(((double)w)/((double)lt.getWidth()), 0);
            glVertex2d(x+w-rt.getWidth(), y+h-lt.getHeight());
            glTexCoord2d(((double)w)/((double)lt.getWidth()), 1);
            glVertex2d(x+w-rt.getWidth(), y+h);
            glTexCoord2d(0, 1);
            glVertex2d(x+lt.getWidth(), y+h);
        }
        glEnd();
        lc.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x, y+lt.getHeight());
            glTexCoord2d(1, 0);
            glVertex2d(x+rt.getWidth(), y+lt.getHeight());
            glTexCoord2d(1, ((double)h)/((double)lt.getHeight()));
            glVertex2d(x+rt.getWidth(), y+h-lt.getHeight());
            glTexCoord2d(0, ((double)h)/((double)lt.getHeight()));
            glVertex2d(x, y+h-lt.getHeight());
        }
        glEnd();
        rc.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(x+w-rt.getWidth(), y+lt.getHeight());
            glTexCoord2d(1, 0);
            glVertex2d(x+w, y+lt.getHeight());
            glTexCoord2d(1, ((double)h)/((double)lt.getHeight()));
            glVertex2d(x+w, y+h-lt.getHeight());
            glTexCoord2d(0, ((double)h)/((double)lt.getHeight()));
            glVertex2d(x+w-rt.getWidth(), y+h-lt.getHeight());
        }
        glEnd();
    }

}
