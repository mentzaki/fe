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
package org.fe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.declaration.Scenes;
import static org.fe.declaration.Scenes.SIGN_UP;
import static org.fe.declaration.Scenes.WINDOW;
import org.fe.gameplay.network.NetworkConnection;
import org.fe.gameplay.types.Types;
import org.fe.gui.FWindow;
import org.fe.main.FData;
import org.fe.main.FLocale;
import org.fe.main.FSound;
import org.newdawn.slick.Graphics;

/**
 *
 * @author yew_mentzaki
 */
public class Main {

    public static FData SETTINGS;
    public static final Graphics GRAPHICS = new Graphics();
    public static final Random RANDOM = new Random();
    public static final int DEVICE_INDEX = RANDOM.nextInt(Integer.MAX_VALUE);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (new File("conf/settings.s").exists()) {
            try {
                SETTINGS = FData.fromFile("conf/settings.s");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            SETTINGS = new FData(null);
        }
        FWindow.double_pixels = SETTINGS.def("doublepixels").toBoolean(false);
        Scenes.init();
        FLocale.init();
        NetworkConnection.init();
        Types.init();
        if (Main.SETTINGS.get("nickname").toString().equals("noob")) {
            WINDOW.setScene(SIGN_UP);
        } else {
            System.out.println(Main.SETTINGS.get("nickname").toString());
        }
    }

}
