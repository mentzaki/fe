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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;
import org.newdawn.slick.Color;
import org.fe.Main;
import org.fe.graphics.FColor;
import org.fe.graphics.FFont;
import org.fe.graphics.FMouse;
import org.fe.main.FMusic;
import org.fe.main.FSound;

/**
 *
 * @author yew_mentzaki
 */
public class FWindow extends FElement {

    String title;
    private String message;
    private int message_timer;
    public FScene scene;
    private static boolean natives = false;
    public static boolean double_pixels = false;

    public void setScene(FScene scene) {
        this.scene = scene;
    }

    public FWindow(String title, double width, double height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private static void setUpNatives() {
        if (!natives) {
            if (!new File("native").exists()) {
                JOptionPane.showMessageDialog(null, "Error!\nNative libraries not found!");
                System.exit(1);
            }
            try {
                System.setProperty("java.library.path", new File("native").getAbsolutePath());
                //System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Field fieldSysPath = ClassLoader.class
                        .getDeclaredField("sys_paths");
                fieldSysPath.setAccessible(
                        true);
                try {
                    fieldSysPath.set(null, null);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                    System.exit(1);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                    System.exit(1);
                }
            } catch (NoSuchFieldException ex) {
                JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                System.exit(1);
            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                System.exit(1);
            }
            natives = true;
        }
    }

    private void setUpRender() {
        try {
            Display.setDisplayMode(new DisplayMode((int) width, (int) height));
            Display.setTitle(title);
            Display.setLocation((int) x, (int) y);

            if (x == 0 && y == 0) {
                int maxWidth = 0;
                GraphicsDevice gdd = null;
                for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                    if (maxWidth < gd.getDisplayMode().getWidth()) {
                        maxWidth = gd.getDisplayMode().getWidth();
                        gdd = gd;
                    }
                }

                if (gdd == null) {
                    System.err.println("No graphic device found!");
                    System.exit(1);
                } else {
                    Display.setLocation(gdd.getDefaultConfiguration().getBounds().x + (gdd.getDefaultConfiguration().getBounds().width - (int) width) / 2,
                            gdd.getDefaultConfiguration().getBounds().y + (gdd.getDefaultConfiguration().getBounds().height - (int) height) / 2);
                }
            }
            if (Main.SETTINGS.def("fullscreen").toBoolean(false)) {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setFullscreen(true);
            }
            Display.setResizable(true);
            Display.setVSyncEnabled(true);
            Display.create();
            FMusic.init();
            FFont.font = new FFont("europe_ext", 0, 14);
            while (true) {
                if (Display.isCloseRequested()) {
                    System.exit(0);
                }
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                if (width != Display.getWidth() || height != Display.getHeight()) {
                    width = Display.getWidth();
                    height = Display.getHeight();
                    glViewport(0, 0, Display.getWidth(), Display.getHeight());
                }
                glMatrixMode(GL_MODELVIEW);
                glLoadIdentity();
                glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL11.GL_BLEND);
                glEnable(GL11.GL_TEXTURE_2D);
                glEnable(GL_ALPHA_TEST);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
                glClearColor(0, 0, 0, 0);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glOrtho(0, Display.getWidth() / (double_pixels ? 2 : 1), Display.getHeight() / (double_pixels ? 2 : 1), 0, 1, -1);
                FMouse.update();
                try {
                    render();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Display.update();
                Display.sync(60);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleTick() {
        if (scene != null) {
            scene.handleTick();
        }
        if (message_timer > 0) {
            message_timer--;
        }
    }

    @Override
    public void render() {
        if (scene != null) {
            if (!scene.initialized) {
                scene.init();
                scene.initialized = true;
            }
            scene.width = width / (double_pixels ? 2 : 1);
            scene.height = height / (double_pixels ? 2 : 1);
            scene.render();
            scene.handleHover(FMouse.x, FMouse.y);
            if (FMouse.leftReleased) {
                scene.handleClick(FMouse.x, FMouse.y);
            }
            String s = scene.showToolTip(FMouse.x, FMouse.y);
            if (s != null) {
                int w = FFont.font.getWidth(s);
                double mx = FMouse.x;
                if ((w + mx) * (double_pixels ? 2 : 1) > width - 8) {
                    mx -= w + 8;
                }
                FColor.magenta.bind();
                FBackground.BASIC_BACKGROUND.render(mx + 4, FMouse.y + 8, w + 8, 18);
                FFont.font.render(s, mx + 8, FMouse.y + 9, FColor.black);
                FFont.font.render(s, mx + 8, FMouse.y + 8, FColor.white);
            }
            if (message_timer > 0) {
                FFont.font.render(message, 5, 6, FColor.black);
                FFont.font.render(message, 5, 5, FColor.white);
            }
        }
    }

    public void create() {
        Thread t = new Thread(title + " Render Thread") {
            @Override
            public void run() {
                setUpNatives();
                setUpRender();
            }
        };
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        Timer ti = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                handleTick();
            }
        });
        ti.start();
    }

    public void addMessage(String string) {
        this.message = string;
        this.message_timer = 500;
    }

}
