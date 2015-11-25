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

import org.fe.graphics.FColor;

/**
 *
 * @author fax
 */
public class Player {
    FColor color;
    int faction;
    int index;

    public Player(FColor color, int faction, int index) {
        this.color = color;
        this.faction = faction;
        this.index = index;
    }
    
    public void tick(){
        
    }
}
