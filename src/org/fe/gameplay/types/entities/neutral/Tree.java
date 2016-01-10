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
package org.fe.gameplay.types.entities.neutral;

import org.fe.Main;
import org.fe.gameplay.types.Entity;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class Tree extends Entity{

    int tree;
    
    static FImage tree_sprite[] = {
        new FImage("entities/neutral/tree/0"), 
        new FImage("entities/neutral/tree/1"), 
        new FImage("entities/neutral/tree/2"), 
        new FImage("entities/neutral/tree/3"), 
        new FImage("entities/neutral/tree/4"), 
        new FImage("entities/neutral/tree/5"), 
        new FImage("entities/neutral/tree/6"), 
        new FImage("entities/neutral/tree/7"), 
        new FImage("entities/neutral/tree/8"), 
        new FImage("entities/neutral/tree/9"), 
        new FImage("entities/neutral/tree/10"), 
        new FImage("entities/neutral/tree/11"),
        new FImage("entities/neutral/tree/12")
    };
    public Tree() {
        hp = maxHp = maxShield = 1;
        shield = 0;
        shieldRestoreSpeed = 0;
        tree = Main.RANDOM.nextInt(13);
    }

    @Override
    public void tick(Entity[] e) {
        
    }
    
    @Override
    public void renderShadow() {
        
    }

    @Override
    public void renderBody() {
        tree_sprite[tree].draw(x - tree_sprite[tree].getWidth() / 2,
                y / 2 - tree_sprite[tree].getHeight() + 10);
    }
    
}
