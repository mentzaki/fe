package org.fe.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.BufferedImageUtil;

/**
 *
 * @author yew_mentzaki
 */
public class FImage implements Serializable {

    private static final List<FImage> images = new ArrayList<FImage>();
    public final java.awt.Image origin;
    public final String path;
    private static org.newdawn.slick.Image not_loaded;
    private transient org.newdawn.slick.Image image;
    private boolean loaded = false;
    public float r = 1, g = 1, b = 1, a = 1;

    public FImage(java.awt.Image image) {
        this.origin = image;
        this.path = null;
        for (int i = 0; i < images.size(); i++) {
            if (this.origin == images.get(i).origin) {
                if (images.get(i).loaded) {
                    loaded = true;
                    this.image = images.get(i).image;
                }
                return;
            }
        }
        images.add(this);
    }

    public void setColor(FColor c) {
        r = c.r;
        g = c.g;
        b = c.b;
        a = c.a;
    }

    public void setColor(org.newdawn.slick.Color c) {
        r = c.r;
        g = c.g;
        b = c.b;
        a = c.a;
    }

    public FImage(String path) {
        this.origin = null;
        this.path = path + ".png";
        for (int i = 0; i < images.size(); i++) {
            if (this.path.equals(images.get(i).path)) {
                if (images.get(i).loaded) {
                    loaded = true;
                    this.image = images.get(i).image;
                }
                return;
            }
        }
        images.add(this);
    }

    public boolean isLoaded() {
        load();
        return loaded;
    }

    public void bind() {
        load();
        image.bind();
    }

    private void load() {
        if (image == null) {
            try {
                if (path != null) {
                    for (int i = 0; i < images.size(); i++) {
                        if (images.get(i) != this && this.path.equals(images.get(i).path)) {
                            if (images.get(i).loaded) {
                                this.image = images.get(i).image;
                                loaded = true;
                            }
                            break;
                        }
                    }
                    if (image == null) {
                        if (new File("res/textures/" + path).exists()) {
                            image = new org.newdawn.slick.Image("res/textures/" + path);
                        } else {
                            throw new FileNotFoundException("res/textures/" + path);
                        }
                    }
                } else {
                    BufferedImage bi = new BufferedImage(origin.getWidth(null), origin.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    bi.getGraphics().drawImage(origin, 0, 0, null);
                    try {
                        image = new org.newdawn.slick.Image(BufferedImageUtil.getTexture("PNG", bi));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                loaded = true;
                this.image.setFilter(GL_NEAREST);
                this.image.bind();
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
                glBindTexture(GL_TEXTURE_2D, 0);
            } catch (Exception ex) {
                if (not_loaded == null) {
                    image = not_loaded;
                    ex.printStackTrace();
                }
            }
        }
        if (image != null) {
            image.setImageColor(r, g, b, a);
        } else {
            image = images.get(0).image;
        }
    }

    public int getWidth() {
        load();
        return image.getWidth();
    }

    public int getHeight() {
        load();
        return image.getHeight();
    }

    public double getTextureWidth() {
        load();
        return image.getTextureWidth();
    }

    public double getTextureHeight() {
        load();
        return image.getTextureHeight();
    }

    public void draw() {
        load();
        image.draw();
    }

    public void draw(double x, double y) {
        load();
        image.draw((float) x, (float) y);
    }

    public void draw(double x, double y, double width, double height) {
        load();
        image.draw((float) x, (float) y, (float) width, (float) height);
    }

}
