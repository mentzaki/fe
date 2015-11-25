/*
 * Copyright (C) 2015 fax
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

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import org.fe.Main;
import org.fe.graphics.FImage;
import org.fe.main.FPoint;
import org.newdawn.slick.Color;

/**
 *
 * @author fax
 */
public class Terrain {
    
    public static final Block BLOCK_WATER = new WaterBlock(true, 0, 100, 255);
    public static final Block BLOCK_SAND = new Block("sand", false, 255, 255, 100);
    public static final Block BLOCK_GRASS = new Block("grass", false, 50, 255, 50);
    public static final Block BLOCK_DARK_GRASS = new Block("dark_grass", false, 25, 128, 25);

    public FImage minimap;

    private byte terrain[][];

    public final int width, height;

    public Terrain(int width, int height, int seed) {
        Random random = new Random(seed);
        this.width = width;
        this.height = height;
        width += 48;
        height += 48;
        terrain = new byte[this.width][this.height];
        double pr[][] = new double[width][height];
        for (int i = 5; i < width - 5; i++) {
            for (int j = 5; j < height - 5; j++) {
                pr[i][j] = random.nextInt(160) - 60;
            }
        }
        for (int i = 0; i < 50 + random.nextInt(50); i++) {
            pr[random.nextInt(width)][random.nextInt(height)] = -random.nextInt(150);
            pr[random.nextInt(width)][random.nextInt(height)] = random.nextInt(150);
        }
        for (int i = 0; i < 25 + random.nextInt(25); i++) {
            int x = random.nextInt(width - 30), y = random.nextInt(height - 30);
            for (int j = 0; j < 10 + random.nextInt(35); j++) {
                pr[x + random.nextInt(30)][y + random.nextInt(30)] = random.nextInt(150);
            }
        }
        for (int i = 0; i < 25 + random.nextInt(25); i++) {
            int x = random.nextInt(width - 30), y = random.nextInt(height - 30);
            for (int j = 0; j < 10 + random.nextInt(35); j++) {
                pr[x + random.nextInt(30)][y + random.nextInt(30)] = -random.nextInt(150);
            }
        }
        for (int i = 0; i < 25 + random.nextInt(25); i++) {
            int x = random.nextInt(width - 60), y = random.nextInt(height - 30);
            for (int j = 0; j < 10 + random.nextInt(35); j++) {
                pr[x + random.nextInt(60)][y + random.nextInt(30)] = random.nextInt(150);
            }
        }
        for (int i = 0; i < 25 + random.nextInt(25); i++) {
            int x = random.nextInt(width - 30), y = random.nextInt(height - 60);
            for (int j = 0; j < 10 + random.nextInt(35); j++) {
                pr[x + random.nextInt(30)][y + random.nextInt(60)] = random.nextInt(150);
            }
        }
        for (int t = 0; t < 3; t++) {
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    pr[i][j] = (pr[i][j]
                            + pr[i - 1][j + 1]
                            + pr[i - 1][j]
                            + pr[i - 1][j - 1]
                            + pr[i][j - 1]
                            + pr[i + 1][j - 1]
                            + pr[i + 1][j]
                            + pr[i + 1][j + 1]
                            + pr[i][j + 1]) / 9;
                }
            }
        }
        for (int i = 16; i < width - 32; i++) {
            for (int j = 16; j < height - 32; j++) {
                if (pr[i][j] >= 28) {
                    terrain[i - 16][j - 16] = 3;
                } else if (pr[i][j] >= 23) {
                    terrain[i - 16][j - 16] = 2;
                } else if (pr[i][j] >= 20) {
                    terrain[i - 16][j - 16] = 1;
                }
            }
        }
        BufferedImage bi = new BufferedImage(width - 48, height - 48, BufferedImage.TYPE_4BYTE_ABGR);
        java.awt.Graphics gp = bi.getGraphics();
        for (int i = 16; i < width - 32; i++) {
            for (int j = 16; j < height - 32; j++) {
                float r = (float) (Math.min(1, Block.blocks[terrain[i - 16][j - 16]].color.r * Math.max(0, pr[i][j] / 64 + 0.2)));
                float g = (float) (Math.min(1, Block.blocks[terrain[i - 16][j - 16]].color.g * Math.max(0, pr[i][j] / 64 + 0.2)));
                float b = (float) (Math.min(1, Block.blocks[terrain[i - 16][j - 16]].color.b * Math.max(0, pr[i][j] / 64 + 0.2)));
                gp.setColor(new java.awt.Color(r, g, b));
                gp.fillRect(i - 16, j - 16, 1, 1);
            }
        }
        minimap = new FImage(bi);
    }

    public FPoint getBlockCoords(Block block_type) {
        double x = 0, y = 0;
        do {
            x = Main.RANDOM.nextInt(width * Block.width);
            y = Main.RANDOM.nextInt(height * Block.height);
        } while (get(x, y) != block_type);
        return new FPoint(x, y);
    }

    public Block get(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return Block.blocks[terrain[x][y]];
        } else {
            return Block.blocks[0];
        }
    }

    public void renderMinimap(int camx, int camy, int width, int height) {
        minimap.draw(25, 25);
        Main.GRAPHICS.setColor(Color.black);
        Main.GRAPHICS.drawRect(camx / Block.width + 25, camy / Block.height + 26, width / Block.width, height / Block.height * 2);
        Main.GRAPHICS.setColor(Color.white);
        Main.GRAPHICS.drawRect(camx / Block.width + 25, camy / Block.height + 25, width / Block.width, height / Block.height * 2);
    }

    public Block get(double x, double y) {
        return get((int) (x / Block.width), (int) (y / Block.height));
    }

    public void render(int camx, int camy, int width, int height, FogOfWar fogOfWar) {
        for (int i = 0; i < Block.blocks.length; i++) {
            if (Block.blocks[i] == null) {
                break;
            }
            Block.blocks[i].update();
        }
        for (int i = camx / Block.width - 1; i <= (width + camx) / Block.width + 1; i++) {
            for (int j = camy / Block.height - 1; j <= (height + camy / 2) / (Block.height / 2) + 1; j++) {
                if (fogOfWar.isTerrainVisible(i, j) || fogOfWar.isTerrainVisible(i + 1, j) || fogOfWar.isTerrainVisible(i, j + 1) || fogOfWar.isTerrainVisible(i + 1, j + 1)) {
                    get(i, j).render(i, j);
                    final int _x = i, _y = j;
                    final int _i = get(i, j).index;
                    class RenderQueue implements Comparable<RenderQueue> {

                        int x, y, i;

                        public RenderQueue(int x, int y) {
                            this.x = x + _x;
                            this.y = y + _y;
                            i = get(x + _x, y + _y).index;
                        }

                        public int compareTo(RenderQueue o) {
                            if (i > o.i) {
                                return 1;
                            } else if (i < o.i) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }

                        public void render() {
                            if (i > _i) {
                                Block.blocks[i].expand(_x, _y, x, y);
                            }
                        }
                    }
                    RenderQueue[] rq = new RenderQueue[8];
                    rq[0] = new RenderQueue(1, 0);
                    rq[1] = new RenderQueue(1, 1);
                    rq[2] = new RenderQueue(0, 1);
                    rq[3] = new RenderQueue(-1, 1);
                    rq[4] = new RenderQueue(-1, 0);
                    rq[5] = new RenderQueue(-1, -1);
                    rq[6] = new RenderQueue(0, -1);
                    rq[7] = new RenderQueue(1, -1);
                    Arrays.sort(rq);
                    for (RenderQueue r : rq) {
                        r.render();
                    }
                }
            }
        }
    }
}
