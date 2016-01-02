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
package org.fe.gameplay.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.Main;
import org.fe.gameplay.terrain.Terrain;
import org.fe.gameplay.types.Entity;
import org.fe.gameplay.types.entities.KaiseratMammutidae;
import org.fe.graphics.FKeyboard;
import org.fe.graphics.FMouse;
import org.fe.gui.FScene;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 *
 * @author fax
 */
public class World extends FScene implements Serializable {

    int player = 0;
    int players;

    public Terrain terrain;

    public Player playerHandler[];
    public int seed;

    public int selectX, selectY, selectX2, selectY2;
    public int cameraX, cameraY;

    public World(int players) {
        this.players = players;
        this.seed = Main.RANDOM.nextInt(Integer.MAX_VALUE);
        this.terrain = new Terrain();
        this.playerHandler = new Player[players];
    }

    public World(int players, int seed) {
        this.players = players;
        this.seed = seed;
        this.terrain = new Terrain();
        this.playerHandler = new Player[players];
    }

    public void add(Class clazz, double x, double y, double z, double a, int owner) {
        try {
            Entity e = (Entity) clazz.newInstance();
            e.id = id++;
            e.world = this;
            e.x = x;
            e.y = y;
            e.z = z;
            e.a = a;
            e.owner = owner;
            indexedEntities.add(e);
            e.id = indexedEntities.size() - 1;
            entities.add(e);
        } catch (InstantiationException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remove(Entity e) {
        indexedEntities.set(e.id, null);
        entities.remove(e);
    }

    private int id;
    private ArrayList<Entity> indexedEntities = new ArrayList<Entity>(),
            entities = new ArrayList<Entity>();

    @Override
    public void handleHover(double mx, double my) {
        if (FMouse.leftPressed) {
            selectX = selectX2 = (int) (mx + cameraX);
            selectY = selectY2 = (int) (my * 2 + cameraY);
        }
        if (FMouse.left) {
            selectX2 = (int) (mx + cameraX);
            selectY2 = (int) (my * 2 + cameraY);
        }
        if (FMouse.leftReleased) {
            for (Entity e : getEntities()) {
                if (e.x - e.width / 2 <= Math.max(selectX, selectX2)
                        && e.x + e.width / 2 >= Math.min(selectX, selectX2)
                        && e.y - e.width / 2 <= Math.max(selectY, selectY2)
                        && e.y + e.width / 2 >= Math.min(selectY, selectY2)) {
                    e.selected = e.owner == player && e.selectable;
                } else if (!(FKeyboard.isKeyDown(FKeyboard.KEY_LSHIFT)
                        || FKeyboard.isKeyDown(FKeyboard.KEY_RSHIFT))) {
                    e.selected = false;
                }
            }
            selectX = selectX2 = 0;
            selectY = selectY2 = 0;

        }
        if (FMouse.middle) {
            cameraX -= FMouse.dx;
            cameraY -= FMouse.dy * 2;
        }
        if (FMouse.rightReleased) {
            for (Entity e : getEntities()) {
                if (e.selected) {
                    for (int i = 0; i < 50; i++) {
                        e.goTo((int) (mx + cameraX), (int) (my * 2 + cameraY));

                    }
                }
            }
        }
        super.hover(mx, my);
    }

    public Entity getEntity(int id) {
        return indexedEntities.get(id);
    }

    public Entity[] getEntities() {
        Entity e[] = new Entity[entities.size()];
        for (int i = 0; i < e.length; i++) {
            e[i] = entities.get(i);
        }
        return e;
    }

    @Override
    public void init() {
        super.init();

        add(KaiseratMammutidae.class, 64 * 2 + 32, 64 * 4 + 16, 0, 0, 0);
        add(KaiseratMammutidae.class, 64 * 3 + 32, 64 * 3 + 16, 0, 0, 0);
        add(KaiseratMammutidae.class, 64 * 4 + 32, 64 * 4 + 16, 0, 0, 0);

    }

    @Override
    public void tick() {
        Entity[] ent = getEntities();
        for (Entity e : ent) {
            e.tick(ent);
        }
    }

    @Override
    public void render() {
        int cameraX = this.cameraX, cameraY = this.cameraY;
        GL11.glTranslated(-cameraX, -cameraY / 2, 0);
        terrain.render(cameraX, cameraY / 2, (int) width, (int) height);
        Entity[] ent = getEntities();
        for (Entity e : ent) {
            e.render();
        }
        if (FMouse.left) {
            Main.GRAPHICS.setColor(Color.white);
            Main.GRAPHICS.drawRect(selectX,
                    (selectY) / 2,
                    selectX2 - selectX,
                    (selectY2 - selectY) / 2
            );
        }
        GL11.glTranslated(cameraX, cameraY / 2, 0);
        super.render();
    }
}
