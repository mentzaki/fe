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

import org.fe.Main;
import org.newdawn.slick.Graphics;
import org.fe.graphics.FColor;
import org.fe.graphics.FFont;
import org.fe.graphics.FImage;
import org.fe.graphics.FMouse;
import org.fe.main.FLocale;
import org.fe.main.FSound;
import org.newdawn.slick.Color;

/**
 *
 * @author yew_mentzaki
 */
public class FColorSwitchBar extends FElement {

    static Graphics g = new Graphics();

    public float value;
    
    public FColor left = FColor.black, right = FColor.black;

    private boolean playSound = true;

    public FColorSwitchBar(float value, int x, int y, int width) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = 40;
    }

    @Override
    public void handleClick(double mx, double my) {
        if (mx >= x()
                && my >= y()
                && mx <= width + x()
                && my <= height + y()) {
            for (int i = 0; i < size(); i++) {
                get(i).handleClick(mx, my);
            }
            FSound.play("gui/beep");
            click(mx, my);
            click = true;
        } else {
            click = false;
        }
    }

    @Override
    public void hover(double mx, double my) {
        if (FMouse.left) {
            mx -= x() + 10;
            float value = Math.min(Math.max((float) (mx / (width - 20)), 0), 1);
            if(value != this.value){
                valueChanged(value);
                this.value = value;
            }
        }
    }
    
    public void valueChanged(float value){
        
    }

    @Override
    public void render() {
        Main.GRAPHICS.setColor(Color.black);
        Main.GRAPHICS.fillRect((int)(x() + 6), (int)(y() + 8), (int)(width - 12), 24);
        FBackground.BASIC_BACKGROUND.render(x() + 6, y() + 8, width - 12, 24, left, right, right, left);
        FFrame.BASIC_FRAME.render(x(), y() + 4, width, 32);
        int x = (int) (value * (width - 40));
        FColor.magenta.bind();
        FBackground.BASIC_BACKGROUND.render(x() + x + 8, y() + 6, 24, 24);
        FFrame.BASIC_FRAME.render(x() + x, y(), 40, 40);
        super.render();
    }

}
