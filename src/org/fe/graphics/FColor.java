package org.fe.graphics;



import java.io.Serializable;
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

    public void bind() {
        GL11.glColor4f(r, g, b, a);
    }

    public org.newdawn.slick.Color slickColor() {
        return new org.newdawn.slick.Color(r, g, b, a);
    }

}
