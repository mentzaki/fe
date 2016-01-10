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
package org.fe.graphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author yew_mentzaki
 */
public class FAnimation implements Serializable{

    private FImage[] images(ArrayList<FTextureLoader.Tex> t) {
        FTextureLoader.Tex[] tex = new FTextureLoader.Tex[t.size()];
        for (int i = 0; i < t.size(); i++) {
            tex[i] = t.get(i);
        }
        FImage[] i = new FImage[tex.length];
        for (int j = 0; j < i.length; j++) {
            i[j] = new FImage(tex[j]);
        }
        return i;
    }

    public FAnimation(String name, int delay) {
        this.name = name;
        this.delay = delay;
        images = FTextureLoader.animation(name).images;
    }

    public FAnimation(String name) {
        this.name = name;
        images = FTextureLoader.animation(name).images;
    }

    FAnimation(String name, ArrayList<FTextureLoader.Tex> tex) {
        this.name = name;
        images = images(tex);
    }

    public FImage get() {
        long now = new Date().getTime();
        if (now - last > delay) {
            index++;
            if (index == images.length) {
                index = 0;
            }
            last = now;
        }
        return images[index];
    }

    private int index = 0;
    private long last = 0;
    public int delay = 100;
    public String name;
    public FImage[] images;

}