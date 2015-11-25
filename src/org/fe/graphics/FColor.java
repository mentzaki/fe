package org.fe.graphics;

import java.io.Serializable;
import static java.lang.Math.pow;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class FColor implements Serializable {

    public static final FColor transparent = new FColor(0, 0, 0, 0);
    public static final FColor white = new FColor(255, 255, 255);
    public static final FColor yellow = new FColor(255, 255, 0);
    public static final FColor red = new FColor(255, 0, 0);
    public static final FColor blue = new FColor(0, 0, 255);
    public static final FColor green = new FColor(0, 255, 0);
    public static final FColor black = new FColor(0, 0, 0);
    public static final FColor gray = new FColor(128, 128, 128);
    public static final FColor cyan = new FColor(0, 255, 255);
    public static final FColor darkGray = new FColor(64, 64, 64);
    public static final FColor lightGray = new FColor(192, 192, 192);
    public static final FColor pink = new FColor(255, 0, 255);
    public static final FColor orange = new FColor(255, 128, 0);
    public static final FColor magenta = new FColor(128, 0, 128);
    public float r, g, b, a = 1;

    public FColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public FColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1f;
    }

    public FColor(int r, int g, int b, int a) {
        this.r = (float) r / 255f;
        this.g = (float) g / 255f;
        this.b = (float) b / 255f;
        this.a = (float) a / 255f;
    }

    public FColor(int r, int g, int b) {
        this.r = (float) r / 255f;
        this.g = (float) g / 255f;
        this.b = (float) b / 255f;
        this.a = 1f;
    }

    public FColor(int v) {
        int a = (v) & 0xFF;
        int r = (v >> 8) & 0xFF;
        int g = (v >> 16) & 0xFF;
        int b = (v >> 24) & 0xFF;
        
        this.r = 1f - (float) r / 255f;
        this.g = 1f - (float) g / 255f;
        this.b = 1f - (float) b / 255f;
        this.a = 1f - (float) a / 255f;
    }

    public int getV() {
        int alpha = Math.round(255 * a);
        int red = Math.round(255 * r);
        int green = Math.round(255 * g);
        int blue = Math.round(255 * b);

        alpha = (alpha << 24) & 0xFF000000;
        red = (red << 16) &     0x00FF0000;
        green = (green << 8) &  0x0000FF00;
        blue = blue &           0x000000FF;

        return alpha | red | green | blue;
    }

    public void bind() {
        GL11.glColor4f(r, g, b, a);
    }

    public org.newdawn.slick.Color slickColor() {
        return new org.newdawn.slick.Color(r, g, b, a);
    }

    @Override
    public String toString() {
        return "org.fe.graphics.FColor(" + r + ", " + g + ", " + b + ", " + a + ")";
    }

}
