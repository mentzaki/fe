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
package org.fe.gameplay.space;

import java.io.Serializable;
import org.fe.declaration.Scenes;
import org.fe.graphics.FImage;
import org.fe.graphics.FKeyboard;
import org.fe.gui.FScene;

/**
 *
 * @author fax
 */
public class StarSystem extends FScene implements Serializable{
    Star star; Space space;
    
    private static final FImage STAR_SPRITE = new FImage("space/planets/star");

    public StarSystem(Star star) {
        this.star = star;
    }

    @Override
    public void render() {
        if(FKeyboard.isKeyDown(FKeyboard.KEY_ESCAPE)){
            Scenes.WINDOW.setScene(space);
        }
        for (int i = 0; i < star.sl.size(); i++) {
            star.sl.get(i).render(width, height, star.system_size);
        }
        STAR_SPRITE.draw((width - STAR_SPRITE.getWidth()) / 2, (height - STAR_SPRITE.getHeight()) / 2);
    }

    @Override
    public void tick() {
        star.tick();
    }
    
    
}
