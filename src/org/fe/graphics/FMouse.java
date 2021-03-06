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
package org.fe.graphics;

import static org.fe.gui.FWindow.double_pixels;
import org.lwjgl.opengl.Display;

/**
 *
 * @author yew_mentzaki
 */
public class FMouse {

    public static double x, y;
    public static double dx, dy;
    public static boolean left, middle, right;
    public static boolean leftReleased, middleReleased, rightReleased;
    public static boolean leftPressed, middlePressed, rightPressed;
    public static double dwheel;

    public static void update() {
        dx = (org.lwjgl.input.Mouse.getX() - x * (double_pixels ? 2 : 1)) / (double_pixels ? 2 : 1);
        dy = (Display.getHeight() - org.lwjgl.input.Mouse.getY() - y * (double_pixels ? 2 : 1)) / (double_pixels ? 2 : 1);
        dwheel = (double) org.lwjgl.input.Mouse.getDWheel() / 500;
        x = (org.lwjgl.input.Mouse.getX()) / (double_pixels ? 2 : 1);
        y = (Display.getHeight() - org.lwjgl.input.Mouse.getY()) / (double_pixels ? 2 : 1);
        leftReleased = left & !org.lwjgl.input.Mouse.isButtonDown(0);
        middleReleased = middle & !org.lwjgl.input.Mouse.isButtonDown(2);
        rightReleased = right & !org.lwjgl.input.Mouse.isButtonDown(1);
        leftPressed = !left & org.lwjgl.input.Mouse.isButtonDown(0);
        middlePressed = !middle & org.lwjgl.input.Mouse.isButtonDown(2);
        rightPressed = !right & org.lwjgl.input.Mouse.isButtonDown(1);
        left = org.lwjgl.input.Mouse.isButtonDown(0);
        middle = org.lwjgl.input.Mouse.isButtonDown(2);
        right = org.lwjgl.input.Mouse.isButtonDown(1);
    }
}
