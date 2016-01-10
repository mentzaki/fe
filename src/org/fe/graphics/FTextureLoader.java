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
package org.fe.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FLocale;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author yew_mentzaki
 */
public class FTextureLoader {

    static class Tex extends LoadTask {

        String name;
        File file;
        Texture texture;
        Image image;

        public Tex(String name, File file) {
            super(FLocale.get("load.loading") + " " + FLocale.get("load.texture") + " \"" + name + "\"...");
            this.name = name;
            this.file = file;
        }

        @Override
        public void load() {
            try {
                this.texture = TextureLoader.getTexture(name.split("\\.")[0].toUpperCase(), new FileInputStream(file));
                this.image = new Image(this.texture);
                this.image.setFilter(GL11.GL_NEAREST);
                this.image.bind();
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
                glBindTexture(GL_TEXTURE_2D, 0);
                System.out.println("Texture \"" + name + "\" is loaded!");
            } catch (IOException ex) {
                Logger.getLogger(FTextureLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static ArrayList<FAnimation> tempFAnimationList = new ArrayList<FAnimation>();

    private static FTextureLoader.Tex[] textures;
    private static FAnimation[] animations;

    public static void load() {
        ArrayList<Tex> textureList = new ArrayList<Tex>();
        List<File> ff = Arrays.asList(new File("res/textures").listFiles());
        Collections.sort(ff);
        for (File f : ff) {
            if (f.isDirectory()) {
                textureList.addAll(load(f.getName(), f));
            } else if (f.getName().contains(".png")) {
                textureList.add(new Tex(f.getName(), f));
            }
        }
        textures = new Tex[textureList.size()];
        for (int i = 0; i < textureList.size(); i++) {
            textures[i] = textureList.get(i);
        }
        animations = new FAnimation[tempFAnimationList.size()];
        for (int i = 0; i < tempFAnimationList.size(); i++) {
            animations[i] = tempFAnimationList.get(i);
        }
        tempFAnimationList.clear();
    }

    public static Image image(String name) {
        if (textures != null) {
            for (Tex t : textures) {
                if (t.name.equals(name)) {
                    return t.image;
                }
            }
            System.err.println("Image \"" + name + "\" requested but not loaded!");
        } else {
            System.err.println("Textures still not loaded!");
        }
        return null;
    }

    public static FAnimation animation(String name) {
        for (FAnimation a : animations) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    public static Texture texture(String name) {
        for (Tex t : textures) {
            if (t.name.equals(name)) {
                return t.texture;
            }
        }
        return null;
    }

    private static ArrayList<Tex> load(String names, File folder) {
        ArrayList<Tex> textures = new ArrayList<Tex>();
        List<File> ff = Arrays.asList(folder.listFiles());
        Collections.sort(ff);
        for (File f : ff) {
            if (f.isDirectory()) {
                textures.addAll(load(names + "/" + f.getName(), f));
            } else if(f.getName().contains(".png")){
                textures.add(new Tex(names + "/" + f.getName(), f));
            }
        }
        tempFAnimationList.add(new FAnimation(names, textures));
        return textures;
    }
}
