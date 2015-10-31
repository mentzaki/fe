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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.Main;
import static org.fe.Main.GRAPHICS;
import org.fe.graphics.FColor;
import org.fe.gui.*;
import org.fe.main.FData;
import org.fe.main.FLocale;
import org.fe.main.FMusic;
import org.fe.main.FSound;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

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

        public FPanel panel = new FPanel(-10, -32, 250, 240) {

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
        public FButton exit = new FButton("mainmenu.exit", 10, 190, 230, 40) {

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

        public FPanel panel = new FPanel(0, 0, 650, 350) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
                verticalAlignment = FAlignment.CENTER;
            }

        };

        public FButton confirm = new FButton("settings.confirm", -15, -15, 150, 40) {

            @Override
            public void click(double mx, double my) {
                FData.toFile(Main.SETTINGS, new File("conf/settings.s"));
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
                WINDOW.setScene(CONTROLS);
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

        public FTrackBar musicVolume = new FTrackBar(Main.SETTINGS.def("music").toFloat(0.5f), 16, 55, 618) {

            @Override
            public void valueChanged(float value) {
                FMusic.setVolume(value);
                Main.SETTINGS.get("music").setValue(value);
            }

        };

        public FTrackBar soundVolume = new FTrackBar(Main.SETTINGS.def("sound").toFloat(1.0f), 16, 125, 618) {

            @Override
            public void valueChanged(float value) {
                FSound.volume = value;
                Main.SETTINGS.get("sound").setValue(value);
            }

        };

        public FTuggleSwitch reducedViolence = new FTuggleSwitch(Main.SETTINGS.def("reducedviolence").toBoolean(false), "settings.reducedviolence", 16, 180, 309) {

            @Override
            public void valueChanged(boolean value) {
                Main.SETTINGS.def("reducedviolence").setValue(value);
            }

        };
        public FTuggleSwitch particles = new FTuggleSwitch(Main.SETTINGS.def("particles").toBoolean(true), "settings.particles", 325, 180, 309) {

            @Override
            public void valueChanged(boolean value) {
                Main.SETTINGS.def("particles").setValue(value);
            }

        };

        public FTuggleSwitch doublePixels = new FTuggleSwitch(Main.SETTINGS.def("doublepixels").toBoolean(false), "settings.doublepixels", 16, 230, 309) {

            @Override
            public void valueChanged(boolean value) {
                FWindow.double_pixels = value;
                Main.SETTINGS.def("doublepixels").setValue(value);
            }

        };

        public FTuggleSwitch fullscreenMode = new FTuggleSwitch(Main.SETTINGS.def("fullscreen").toBoolean(false), "settings.fullscreen", 325, 230, 309){

            @Override
            public void valueChanged(boolean value) {
                if(value){
                    try {
                        Display.setDisplayMode(Display.getDesktopDisplayMode());
                        Display.setFullscreen(true);
                    } catch (LWJGLException ex) {
                        Logger.getLogger(Scenes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try {
                        Display.setFullscreen(false);
                    } catch (LWJGLException ex) {
                        Logger.getLogger(Scenes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Main.SETTINGS.def("fullscreen").setValue(value);
            }
            
        };

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
            panel.add(doublePixels);
            panel.add(fullscreenMode);
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
                    final String l = f.getName();

                    @Override
                    public void init() {
                        horisontalAlignment = FAlignment.CENTER;
                    }

                    @Override
                    public void click(double mx, double my) {
                        FLocale.set(l);
                        Main.SETTINGS.def("locale").setValue(l);
                        FData.toFile(Main.SETTINGS, new File("conf/settings.s"));
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

    public static FScene CONTROLS = new FScene() {

        public FLabel title = new FLabel("settings.controls", 0, 15) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
            }

        };

        public FPanel panel = new FPanel(0, 0, 650, 300) {

            @Override
            public void init() {
                horisontalAlignment = FAlignment.CENTER;
                verticalAlignment = FAlignment.CENTER;
            }

        };

        @Override
        public void init() {
            add(panel);

            panel.add(title);
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
