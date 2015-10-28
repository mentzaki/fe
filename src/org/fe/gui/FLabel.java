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
import org.fe.main.FLocale;

/**
 *
 * @author yew_mentzaki
 */
public class FLabel extends FElement {

    static Graphics g = new Graphics();
    
    public String text;

    public FLabel(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.height = 18;
    }
    
    public String text(){
        return FLocale.get(text);
    }

    @Override
    public void render() {
        if (hover) {
            FColor.pink.bind();
        } else {
            FColor.magenta.bind();
        }
        width = FFont.font.getWidth(text());
        FFont.font.render(text(), x() + width / 2, y() + height / 2 - 8, FAlignment.CENTER, FColor.black);
        FFont.font.render(text(), x() + width / 2, y() + height / 2 - 9, FAlignment.CENTER, FColor.white);
        for (int i = 0; i < size(); i++) {
            get(i).render();
        }
    }

}
