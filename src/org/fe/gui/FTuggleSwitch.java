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
public class FTuggleSwitch extends FElement {

    static Graphics g = new Graphics();

    public boolean value;

    private double valueChange;

    public String text;
    private static final FImage image = new FImage("gui/tuggle_switch");

    public FTuggleSwitch(boolean value, String text, int x, int y, int width) {
        this.value = value;
        this.valueChange = value ? 40 : 0;
        this.text = text;
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
            FSound.play("gui/long_beep");
            click(mx, my);
            valueChanged(!value);
            value = !value;
            click = true;
        } else {
            click = false;
        }
    }

    @Override
    public void handleTick() {
        double t = value ? 40 : 0;
        valueChange = (valueChange * 19 + t) / 20;
        super.handleTick();
    }

    @Override
    public void hover(double mx, double my) {
    }
    
    public void valueChanged(boolean value){
        
    }

    public String text() {
        return FLocale.get(text);
    }

    @Override
    public void render() {
        FColor.pink.bind();
        FBackground.BASIC_BACKGROUND.render(x() + width - 72, y() + 7, valueChange - 7, 23);
        FColor.black.bind();
        FBackground.BASIC_BACKGROUND.render(x() + width - 40 + valueChange, y() + 7, 31 - valueChange, 23);

        FFrame.BASIC_FRAME.render(x() + width - 80, y(), 80, 40);

        image.draw(x() + width - 80 + valueChange, y());
        if (text != null) {
            FFont.font.render(text(), x() + (width - 80) / 2, y() + height / 2 - 8, FAlignment.CENTER, FColor.black);
            FFont.font.render(text(), x() + (width - 80) / 2, y() + height / 2 - 9, FAlignment.CENTER, FColor.white);
        }
        for (int i = 0; i < size(); i++) {
            get(i).render();
        }
        super.render();
    }

}
