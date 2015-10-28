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
package org.fe.declaration;

import java.io.File;
import static org.fe.Main.GRAPHICS;
import org.fe.graphics.FColor;
import org.fe.gui.*;
import org.fe.main.FLocale;
import org.fe.main.FSound;

/**
 *
 * @author yew_mentzaki
 */
public class Scenes {

    public static void init() {
        WINDOW = new FWindow("ForcedEngine", 800, 600);
        WINDOW.create();

        WINDOW.setScene(MAIN_MENU);
    }

    public static FWindow WINDOW;

    public static FScene MAIN_MENU = new FScene() {

        public FPicture background = new FPicture("gui/background", 0, 0, 800, 800) {

            @Override
            public void render() {
                width = WINDOW.width;
                height = WINDOW.height;
                super.render();
            }

        };

        public FPicture logo = new FPicture("gui/logo", 0, 0, 375, 150) {

            @Override
            public void init() {
                verticalAlignment = FAlignment.BOTTOM;
            }

        };

        public FPanel panel = new FPanel(-10, 0, 250, 300) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.RIGHT;
                verticalAlignment = FAlignment.CENTER;
            }

        };

        public FButton singleplayer = new FButton("mainmenu.singleplayer", 10, 10, 230, 40);
        public FButton multiplayer = new FButton("mainmenu.multiplayer", 10, 60, 230, 40);
        public FButton settings = new FButton("mainmenu.settings", 10, 110, 230, 40) {

            @Override
            public void click(double mx, double my) {
                WINDOW.setScene(SETTINGS);
            }

        };
        public FButton exit = new FButton("mainmenu.exit", 10, 250, 230, 40) {

            @Override
            public void click(double mx, double my) {
                System.exit(0);
            }

        };
        public FButton locale = new FButton("gui/icons/locale", "mainmenu.language", -10, -10, 80, 80) {

            @Override
            public void click(double mx, double my) {
                WINDOW.setScene(LANGUAGE);
                languageExitScene = MAIN_MENU;
            }

            @Override
            public void init() {
                horisontalAlignment = FAlignment.RIGHT;
                verticalAlignment = FAlignment.BOTTOM;
            }

        };

        @Override

        public void init() {
            add(background);
            add(logo);
            add(panel);
            add(locale);

            panel.add(singleplayer);
            panel.add(multiplayer);
            panel.add(settings);
            panel.add(exit);
        }

    };

    public static FScene SETTINGS = new FScene() {

        public FLabel title = new FLabel("settings.settings", 0, 15) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
            }

        };

        public FLabel music = new FLabel("settings.music", 20, 35);
        public FLabel sound = new FLabel("settings.sound", 20, 105);

        public FPanel panel = new FPanel(0, 0, 650, 450) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
                verticalAlignment = FAlignment.CENTER;
            }

        };

        public FButton confirm = new FButton("settings.confirm", -15, -15, 150, 40) {

            @Override
            public void click(double mx, double my) {
                WINDOW.setScene(MAIN_MENU);
            }

            @Override
            public void init() {
                horisontalAlignment = FAlignment.RIGHT;
                verticalAlignment = FAlignment.BOTTOM;
            }

        };

        public FButton controls = new FButton("settings.controls", 15, -15, 150, 40) {

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

        public FButton language = new FButton("settings.language", 0, -15, 150, 40) {

            @Override
            public void click(double mx, double my) {
                WINDOW.setScene(LANGUAGE);
                languageExitScene = SETTINGS;
            }

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
                verticalAlignment = FAlignment.BOTTOM;
            }

        };

        public FTrackBar musicVolume = new FTrackBar(0.5f, 16, 55, 618);
        public FTrackBar soundVolume = new FTrackBar(0.5f, 16, 125, 618) {

            @Override
            public void valueChanged(float value) {
                FSound.volume = value;
            }

        };

        public FTuggleSwitch reducedViolence = new FTuggleSwitch(true, "settings.reducedviolence", 16, 200, 309);
        public FTuggleSwitch particles = new FTuggleSwitch(true, "settings.particles", 325, 200, 309);

        @Override
        public void init() {
            add(panel);

            panel.add(title);
            panel.add(music);
            panel.add(musicVolume);
            panel.add(sound);
            panel.add(soundVolume);
            panel.add(reducedViolence);
            panel.add(particles);
            panel.add(controls);
            panel.add(language);
            panel.add(confirm);
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

    };

    public static FScene languageExitScene = MAIN_MENU;
    public static FScene LANGUAGE = new FScene() {
        @Override
        public void init() {
            int y = 100;
            for (final File f : new File("locale").listFiles()) {
                add(new FButton("gui/locale/" + f.getName(), null, 0, y, 148, 84) {
                    String l = f.getName();

                    @Override
                    public void init() {
                        horisontalAlignment = FAlignment.CENTER;
                    }

                    @Override
                    public void click(double mx, double my) {
                        FLocale.set(l);
                        WINDOW.setScene(languageExitScene);
                    }

                });
                y += 100;
            }
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

    };
}
