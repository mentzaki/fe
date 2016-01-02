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
package org.fe.gameplay.types.entities;

import org.fe.gameplay.types.Entity;
import org.fe.graphics.FColor;
import org.fe.graphics.FImage;
import org.newdawn.slick.Color;

/**
 *
 * @author yew_mentzaki
 */
public class KaiseratMammutidae extends Entity{
    
    FImage[] stands={
        new FImage("entities/kaiserat/mammutidae/stand/0"),
        new FImage("entities/kaiserat/mammutidae/stand/1"),
        new FImage("entities/kaiserat/mammutidae/stand/2"),
        new FImage("entities/kaiserat/mammutidae/stand/3"),
        new FImage("entities/kaiserat/mammutidae/stand/4"),
        new FImage("entities/kaiserat/mammutidae/stand/5"),
        new FImage("entities/kaiserat/mammutidae/stand/6"),
        new FImage("entities/kaiserat/mammutidae/stand/7")
    };

    FImage[] stands_team={
        new FImage("entities/kaiserat/mammutidae/stand/0_team"),
        new FImage("entities/kaiserat/mammutidae/stand/1_team"),
        new FImage("entities/kaiserat/mammutidae/stand/2_team"),
        new FImage("entities/kaiserat/mammutidae/stand/3_team"),
        new FImage("entities/kaiserat/mammutidae/stand/4_team"),
        new FImage("entities/kaiserat/mammutidae/stand/5_team"),
        new FImage("entities/kaiserat/mammutidae/stand/6_team"),
        new FImage("entities/kaiserat/mammutidae/stand/7_team")
    };

    @Override
    public void renderShadow() {
        
    }

    @Override
    public void tick(Entity[] e) {
        super.tick(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderBody() {
        stands[angleToDir8(a)].draw(x - stands[0].getWidth() / 2, y / 2 - 44);
        stands_team[angleToDir8(a)].setColor(FColor.magenta);
        stands_team[angleToDir8(a)].draw(x - stands[0].getWidth() / 2, y / 2 - 44);
    }
    
    
    
}
