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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

/**
 *
 * @author yew_mentzaki
 */
public class FSound {

    public static float volume = 1;

    String path;
    Audio audio;

    private static ArrayList<FSound> fs = new ArrayList<FSound>();

    private FSound(File f, String path) {
        this.path = path.substring(0, path.lastIndexOf('.'));
        try {
            audio = AudioLoader.getAudio("OGG", new FileInputStream(f));
        } catch (IOException ex) {
            Logger.getLogger(FSound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void play(float vol) {
        if (audio != null) {
            audio.playAsSoundEffect(volume * vol, volume * vol, false);
        }
    }

    public static void init() {
        load(new File("res/sounds"), "");
    }

    public static void play(String sound) {
        play(sound, 1);
    }

    public static void play(String sound, float volume) {
        for (FSound s : fs) {
            if (s.path.equals(sound)) {
                s.play(volume);
            }
        }
    }

    private static void load(File dir, String name) {
        File[] fl = dir.listFiles();
        if (fl != null) {
            for (File f : fl) {
                if (f.isDirectory()) {
                    if (name.equals("")) {
                        load(f, f.getName());
                    } else {
                        load(f, name + "/" + f.getName());
                    }
                } else {
                    fs.add(new FSound(f, name + "/" + f.getName()));
                }
            }
        }
    }
}
