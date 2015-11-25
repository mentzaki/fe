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
import org.fe.gui.FScene;

/**
 *
 * @author fax
 */
public class World extends FScene implements Serializable {

    int player = -1;
    int players;
    
    Terrain terrain;
    
    Player playerHandler[];
    public int seed;

    public World(int players) {
        this.players = players;
        this.seed = Main.RANDOM.nextInt(Integer.MAX_VALUE);
        this.terrain = new Terrain(players * 64 + 64, id * 64 + 64, 
                seed);
        this.playerHandler = new Player[players];
    }
    
    public World(int players, int seed) {
        this.players = players;
        this.seed = seed;
        this.terrain = new Terrain(players * 64 + 64, id * 64 + 64, seed);
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
            indexedEntities.set(e.id, e);
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
    
    public Entity getEntity(int id){
        return indexedEntities.get(id);
    }
    
    public Entity[] getEntities(){
        Entity e[] = new Entity[entities.size()];
        for (int i = 0; i < e.length; i++) {
            e[i] = entities.get(i);
        }
        return e;
    }
    
    @Override
    public void tick(){
        Entity[] ent = getEntities();
        for(Entity e : ent){
            e.tick(ent);
        }
    }
    
    @Override
    public void render(){
        Entity[] ent = getEntities();
        for(Entity e : ent){
            e.render();
        }
    }
}
