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

import org.fe.declaration.Scenes;
import org.fe.graphics.FColor;
import org.fe.graphics.FFont;
import org.fe.graphics.FMouse;
import org.fe.gui.FAlignment;
import org.fe.gui.FElement;
import org.fe.gui.FLabel;
import org.fe.gui.FPanel;
import org.fe.gui.FScene;
import org.fe.main.FLocale;

/**
 *
 * @author fax
 */
public class Space extends FScene {

    Star[] stars = new Star[301];

    FPanel panel = new FPanel(10, 16, 100, 50) {

        @Override
        public void render() {
            super.render();
            verticalAlignment = FAlignment.BOTTOM;
        }

    };

    FLabel label = new FLabel(FLocale.getAsData().get("history").get("history").get(0).toString(), 0, 10) {

        @Override
        public void init() {
            label.horisontalAlignment = FAlignment.CENTER;
        }

        @Override
        public String text() {
            return text;
        }

    };

    @Override
    public void init() {
        add(panel);

        panel.add(label);
    }

    int text = 0;
    int text_length = FLocale.getAsData().get("history").get("history").size();
    int text_timer = 100;

    int spacespeed = 30;

    @Override
    public void tick() {
        for (int i = 0; i < 51; i++) {
            stars[i].tick();
        }
        if (text < text_length) {
            text_timer--;
            if (text_timer == 0) {
                text++;
                if (text + 1 != text_length) {
                    label.text = FLocale.getAsData().get("history").get("history").get(text).toString();
                    text_timer = label.text.length() * 10 + 100;
                } else {
                    remove(panel);
                }
            }
        }
    }

    @Override
    public String toolTip() {
        int index = -1;
        double distance = 1000;
        for (int i = 0; i < 51; i++) {
            double dist = Math.sqrt(Math.pow(FMouse.x - (stars[i].x/Star.GALAXY_SIZE*width), 2) + Math.pow(FMouse.y - (stars[i].y/Star.GALAXY_SIZE*height), 2));
            if (dist < 32) {
                if(dist < distance){
                    index = i;
                    distance = dist;
                }
            }
        }
        if(index != -1){
            if(FMouse.leftReleased){
                stars[index].starSystem.space = this;
                Scenes.WINDOW.setScene(stars[index].starSystem);
            }
            return stars[index].getName();
        }
        return super.toolTip(); //To change body of generated methods, choose Tools | Templates.
    }

    public Space() {
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(i + 1);
        }
        Thread t = new Thread() {

            @Override
            public void run() {
                while (spacespeed > 0) {
                    grow();
                }
                Star[] st = stars;
                Star[] st2 = new Star[51];
                for (int i = 0; i < 51; i++) {
                    st2[i] = st[i];
                }
                stars = st2;
            }

        };
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    @Override
    public void render() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].render(width, height);
        }
        panel.width = width - 20;
        if (spacespeed > 0) {
            FFont.font.render((spacespeed) + "0 000 000 " + FLocale.get("history.yearsago"), 10, 10, FColor.white);
        } else {
            FFont.font.render(FLocale.get("history.now"), 10, 10, FColor.white);
        }
        super.render();
    }
    
    

    public void grow() {
        spacespeed--;
        Star.GRAVITATIONAL_CONSTANT = spacespeed * 100 + 2;
        for (int j = 0; j < spacespeed; j++) {
            for (int i = 0; i < stars.length; i++) {
                stars[i].countVelocity(stars);
            }
            for (int i = 0; i < stars.length; i++) {
                stars[i].applyVelocity();
            }
        }
    }

}
