package org.fe.gameplay.terrain;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author fax
 */
public class FogOfWar {

    public final int width, height;

    private short fog[][] = null;

    public FogOfWar(int width, int height) {
        this.width = ++width;
        this.height = ++height;
        this.fog = new short[width][height];
    }

    public void open(double r, double x, double y) {
        int block_size = (Block.width + Block.height) / 2;
        int radius = (int) (r / block_size);
        for (int fx = -radius; fx <= radius; fx++) {
            for (int fy = -radius; fy <= radius; fy++) {
                double power = Math.max(radius - Math.sqrt(Math.pow(fy, 2) + Math.pow(fx, 2)), 0) * 500;
                openFog((int) power, fx + (int) x / Block.width, fy + (int) y / Block.height);
            }
        }
    }

    private void openFog(int power, int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            fog[x][y] = (short) ((fog[x][y] * 20 + Math.min(1000, Math.max(power, fog[x][y]))) / 21);
        }
    }

    private float getFog(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return (float) fog[x][y] / 1000f;
        }
        return 0;
    }
    
    public boolean isObjectVisible(double x, double y) {
        x /= Block.width;
        y /= Block.height;
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return fog[(int)x][(int)y] > 0;
        }
        return false;
    }
    
    public boolean isVisible(double x, double y) {
        x /= Block.width;
        y /= Block.height;
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return fog[(int)x][(int)y] > 200;
        }
        return false;
    }
    
    public boolean isTerrainVisible(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return fog[(int)x][(int)y] > 0;
        }
        return false;
    }

    public void update() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (fog[x][y] > 200) {
                    fog[x][y] -= 5;
                }
            }
        }
    }

    public void render(int camx, int camy, int width, int height) {
        for (int i = camx / Block.width - 1; i <= (width + camx) / Block.width + 1; i++) {
            for (int j = camy / Block.height - 1; j <= (height + camy / 2) / (Block.height / 2) + 1; j++) {
                float fog;
                glBindTexture(GL_TEXTURE_2D, 0);
                glBegin(GL_QUADS);
                {
                    fog = 1 - getFog(i, j);
                    glColor4f(0.039f, 0, 0.243f, fog);
                    glVertex2d((i) * Block.width, (j) * Block.height / 2);
                    fog = 1 - getFog(i + 1, j);
                    glColor4f(0.039f, 0, 0.243f, fog);
                    glVertex2d((i + 1) * Block.width, (j) * Block.height / 2);
                    fog = 1 - getFog(i + 1, j + 1);
                    glColor4f(0.039f, 0, 0.243f, fog);
                    glVertex2d((i + 1) * Block.width, (j + 1) * Block.height / 2);
                    fog = 1 - getFog(i, j + 1);
                    glColor4f(0.039f, 0, 0.243f, fog);
                    glVertex2d((i) * Block.width, (j + 1) * Block.height / 2);
                }
                glEnd();
            }
        }
    }

}
