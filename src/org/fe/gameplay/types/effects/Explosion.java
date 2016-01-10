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
package org.fe.gameplay.types.effects;

import org.fe.gameplay.types.Effect;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class Explosion extends Effect{

    public Explosion(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
        this.life = 40;
    }
    
    private static FImage[] explosion = {
        new FImage("effects/small_explosion/0"),
        new FImage("effects/small_explosion/1"),
        new FImage("effects/small_explosion/2"),
        new FImage("effects/small_explosion/3"),
        new FImage("effects/small_explosion/4"),
        new FImage("effects/small_explosion/5"),
        new FImage("effects/small_explosion/6"),
        new FImage("effects/small_explosion/7"),
        new FImage("effects/small_explosion/8"),
    };

    @Override
    public void render() {
        explosion[Math.min(8, 9 - life / 5)].draw(x - 32, y / 2 - 20);
    }
   
}
