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
package org.fe.main;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.Main;
import org.fe.declaration.Scenes;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 *
 * @author yew_mentzaki
 */
public class FMusic {

    public static void setVolume(float volume) {
        if (currently.music != null) {
            currently.music.setVolume(volume);
        }
        FMusic.volume = volume;
    }

    private static float volume;
    public static long time;
    private static int index;
    private static FMusic currently;
    private static ArrayList<FMusic> fm = new ArrayList<FMusic>();
    String name;
    File file;
    Music music;

    private FMusic(File file) {
        this.file = file;
        this.name = file.getName().substring(0, file.getName().lastIndexOf('.'));
    }

    private boolean playing() {
        if (music != null) {
            return music.playing();
        }
        try {
            this.music = new Music(file.toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(FMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SlickException ex) {
            Logger.getLogger(FMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void play() {
        currently = this;
        music.play();
        music.setPosition(0.00001f);
        music.setVolume(volume);
        if (volume > 0) {
            Scenes.WINDOW.addMessage(FLocale.get("fmusic.nowplaying") + " " + name);
        }
    }

    public static void load() {
        for (File f : new File("res/music").listFiles()) {
            boolean stop = false;
            for (FMusic m : fm) {
                if (m.file == f) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            } else {
                fm.add(new FMusic(f));
            }
            ArrayList<FMusic> fm = FMusic.fm;
            FMusic.fm = new ArrayList<FMusic>();
            for (FMusic m : fm) {
                if (FMusic.fm.size() > 0) {
                    FMusic.fm.add(Main.RANDOM.nextInt(FMusic.fm.size()), m);
                } else {
                    FMusic.fm.add(m);
                }
            }
        }
    }

    public static void init() {
        volume = Main.SETTINGS.def("music").toFloat(0.5f);
        Thread t = new Thread() {

            @Override
            public void run() {
                FSound.init();
                load();
                index = Main.RANDOM.nextInt(fm.size());
                currently = fm.get(index);
                currently.playing();
                currently.play();
                for (FMusic m : fm) {
                    m.playing();
                }
                setPriority(Thread.MIN_PRIORITY);
                while (true) {
                    if (currently.music.getPosition() == 0) {
                        index++;
                        if (index >= fm.size()) {
                            index = 0;
                        }
                        currently = fm.get(index);
                        currently.play();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FMusic.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        };
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

}
