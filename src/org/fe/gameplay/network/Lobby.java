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
package org.fe.gameplay.network;

import java.io.File;
import org.fe.Main;
import static org.fe.Main.GRAPHICS;
import static org.fe.declaration.Scenes.MAIN_MENU;
import static org.fe.declaration.Scenes.WINDOW;
import org.fe.graphics.FColor;
import org.fe.gui.FAlignment;
import org.fe.gui.FButton;
import org.fe.gui.FColorSwitchBar;
import org.fe.gui.FPanel;
import org.fe.gui.FPicture;
import org.fe.gui.FScene;
import org.fe.main.FData;

/**
 *
 * @author fax
 */
public class Lobby extends FScene {

    private Server server;

    public void updateServerList() {
        FData fd = NetworkUtils.request("r", "list");
        for (FData f : fd.get("servers")) {
            System.out.println(f.toString());
        }
    }

    public FPanel panel = new FPanel(0, 0, 650, 350) {

        @Override
        public void init() {
            horisontalAlignment = FAlignment.CENTER;
            verticalAlignment = FAlignment.CENTER;
        }

    };

    public FButton left = new FButton("skirmish.left", 15, -15, 150, 40) {

        @Override
        public void click(double mx, double my) {
            WINDOW.setScene(MAIN_MENU);
        }

        @Override
        public void init() {
            horisontalAlignment = FAlignment.LEFT;
            verticalAlignment = FAlignment.BOTTOM;
        }

    };

    public FButton ready = new FButton("skirmish.ready", -15, -15, 150, 40) {

        @Override
        public void click(double mx, double my) {
            server = new Server(8);
        }

        @Override
        public void init() {
            horisontalAlignment = FAlignment.RIGHT;
            verticalAlignment = FAlignment.BOTTOM;
        }

    };

    public void updateColors() {
        red.left = new FColor(0, green.value, blue.value);
        red.right = new FColor(1, green.value, blue.value);
        green.left = new FColor(red.value, 0, blue.value);
        green.right = new FColor(red.value, 1, blue.value);
        blue.left = new FColor(red.value, green.value, 0);
        blue.right = new FColor(red.value, green.value, 1);
        preview_team.picture.setColor(new FColor(red.value, green.value, blue.value));
    }

    public FColorSwitchBar red = new FColorSwitchBar(0, 15, 15, 450) {

        @Override
        public void valueChanged(float value) {
            updateColors();
        }

        @Override
        public void init() {
            right = FColor.red;
        }
    };

    public FColorSwitchBar green = new FColorSwitchBar(0, 15, 55, 450) {

        @Override
        public void valueChanged(float value) {
            updateColors();
        }

        @Override
        public void init() {
            right = FColor.green;
        }
    };

    public FColorSwitchBar blue = new FColorSwitchBar(0, 15, 95, 450) {

        @Override
        public void valueChanged(float value) {
            updateColors();
        }

        @Override
        public void init() {
            right = FColor.blue;
        }
    };

    public FPicture preview = new FPicture("gui/preview/will", -16, 16, 128, 128) {

        @Override
        public void init() {
            horisontalAlignment = FAlignment.RIGHT;
        }
    };

    public FPicture preview_team = new FPicture("gui/preview/will_team", -16, 16, 128, 128) {

        @Override
        public void init() {
            horisontalAlignment = FAlignment.RIGHT;
        }

    };

    @Override
    public void init() {
        add(panel);
        panel.add(left);
        panel.add(ready);

        panel.add(red);
        panel.add(green);
        panel.add(blue);

        panel.add(preview);
        panel.add(preview_team);

        updateServerList();
    }

    @Override
    public void render() {
        MAIN_MENU.width = width;
        MAIN_MENU.height = height;
        MAIN_MENU.render();
        GRAPHICS.setColor(new FColor(0, 0, 0, 100).slickColor());
        GRAPHICS.fillRect(0, 0, (int) width, (int) height);
        super.render();
    }
}
