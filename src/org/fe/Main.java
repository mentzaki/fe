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

import java.util.Random;
import org.fe.declaration.Scenes;
import org.fe.main.FLocale;
import org.fe.main.FSound;
import org.newdawn.slick.Graphics;

/**
 *
 * @author yew_mentzaki
 */
public class Main {

    public static final Graphics GRAPHICS = new Graphics();
    public static final Random RANDOM = new Random();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FLocale.init();
        Scenes.init();
    }

}
