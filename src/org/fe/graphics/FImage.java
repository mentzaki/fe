package org.fe.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.BufferedImageUtil;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class FImage implements Serializable {

    public final String path;
    private transient org.newdawn.slick.Image subimage;
    public int width, height;
    public float r = 1, g = 1, b = 1, a = 1;

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

    public int getWidth() {
        load();
        return subimage.getWidth();
    }

    public int getHeight() {
        load();
        return subimage.getHeight();
    }

    public double getTextureWidth() {
        load();
        return subimage.getTextureWidth();
    }

    public double getTextureHeight() {
        load();
        return subimage.getTextureHeight();
    }

    public FImage(String path) {
        this.path = path;
        this.subimage = FTextureLoader.image(path);
        if (this.subimage != null) {
            this.width = subimage.getWidth();
            this.height = subimage.getHeight();
        }
    }

    FImage(FTextureLoader.Tex tex) {
        this.path = tex.name;
        this.subimage = tex.image;
        this.width = 1;
        this.height = 1;
    }

    public FImage getNxCopy(float n) {
        load();
        subimage = subimage.getScaledCopy(n);
        subimage.setFilter(GL11.GL_NEAREST);
        return this;
    }

    public FImage getFlipped(boolean horizontal, boolean vertical) {
        load();
        subimage = subimage.getFlippedCopy(horizontal, vertical);
        subimage.setFilter(GL11.GL_NEAREST);
        return this;
    }

    public void draw() {
        load();
        subimage.setImageColor(r, g, b, a);
        subimage.draw(0, 0);
    }

    private void load() {
        if (subimage == null) {
            this.subimage = FTextureLoader.image(path + ".png");
            if(this.subimage == null){
            this.subimage = FTextureLoader.image("unloaded.png");
            }
            this.width = subimage.getWidth();
            this.height = subimage.getHeight();
        }
    }

    public void draw(double x, double y) {
        load();
        subimage.setImageColor(r, g, b, a);
        subimage.draw((float) x, (float) y);
    }

    public void draw(double x, double y, double width, double height) {
        load();
        subimage.setImageColor(r, g, b, a);
        subimage.draw((float) x, (float) y, (float) width, (float) height);
    }

    public void draw(double angle) {
        load();
        subimage.setImageColor(r, g, b, a);
        draw(0, 0, angle);
    }

    public void draw(double x, double y, double angle) {
        load();
        subimage.setImageColor(r, g, b, a);
        draw(x, y, width, height, angle);
    }

    public void draw(double x, double y, double width, double height, double angle) {
        load();
        subimage.setImageColor(r, g, b, a);
        GL11.glTranslated(x, y, 0);
        GL11.glRotated(angle / Math.PI * 180.0, 0, 0, 1);
        subimage.draw((float) -width / 2f, (float) -height / 2f, (float) width, (float) height);
        GL11.glRotated(angle / Math.PI * 180.0, 0, 0, -1);
        GL11.glTranslated(-x, -y, 0);
    }

    public void bind() {
        load();
        subimage.setImageColor(r, g, b, a);
        subimage.bind();
    }

    public static void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

}
