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
import org.fe.graphics.FFont;
import org.fe.graphics.FImage;
import org.fe.main.FLocale;
import org.fe.main.FSound;

/**
 *
 * @author yew_mentzaki
 */
public class FButton extends FElement {
    
    static Graphics g = new Graphics();
    
    public String text;
    public FImage image;
    
    private boolean playSound = true;
    
    public FButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public FButton(String image, String text, int x, int y, int width, int height) {
        this.image = new FImage(image);
        this.toolTip = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
            FSound.play("gui/long_beep");
            click(mx, my);
            click = true;
        } else {
            click = false;
        }
    }
    
    @Override
    public void hover(double mx, double my) {
        if (playSound) {
            FSound.play("gui/beep");
            playSound = false;
        }
    }
    
    public String text() {
        return FLocale.get(text);
    }
    
    @Override
    public void render() {
        if (hover) {
            FColor.pink.bind();
        } else {
            FColor.magenta.bind();
            playSound = true;
        }
        FBackground.BASIC_BACKGROUND.render(x() + 8, y() + 6, width - 16, height - 14);
        if (image != null) {
            image.draw(x() + 8, y() + 8, width - 16, height - 16);
        }
        FFrame.BASIC_FRAME.render(x(), y(), width, height);
        if (text != null) {
            FFont.font.render(text(), x() + width / 2, y() + height / 2 - 8, FAlignment.CENTER, FColor.black);
            FFont.font.render(text(), x() + width / 2, y() + height / 2 - 9, FAlignment.CENTER, FColor.white);
        }
        for (int i = 0; i < size(); i++) {
            get(i).render();
        }
        super.render();
    }
    
}
