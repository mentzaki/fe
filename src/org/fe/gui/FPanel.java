/*
 * Copyright (C) 2015 yew_mentzaki
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
package org.fe.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.fe.graphics.FColor;

/**
 *
 * @author yew_mentzaki
 */
public class FPanel extends FElement{
    static Graphics g = new Graphics();

    public FPanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void render() {
        FColor.magenta.bind();
        FBackground.BASIC_BACKGROUND.render(x() + 8, y() + 6, width - 16, height - 14);
        FFrame.BASIC_FRAME.render(x(), y(), width, height);
        for (int i = 0; i < size(); i++) {
            get(i).render();
        }
    }
    
}
