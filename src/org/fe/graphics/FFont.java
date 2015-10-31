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

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.fe.gui.FAlignment;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;
import org.fe.main.FSerialization;
import sun.font.TrueTypeFont;

/**
 *
 * @author yew_mentzaki
 */
public class FFont implements Serializable {

    public static FFont font;

    private class ZhChar implements Serializable {

        transient FFont f = null;

        int i;
        int w;

        public ZhChar(int i, int w) {
            this.i = i;
            this.w = w;
        }

        public void render(int x, int y) {
            FFont.render(this, f, x, y);
        }
    }

    private static void render(ZhChar c, FFont f, int x, int y) {
        glBegin(GL_QUADS);
        {
            glTexCoord2d(((double) (c.i) / f.t.getImageWidth()) * f.t.getWidth(), 0);
            glVertex2d(x, y);
            glTexCoord2d(((double) (c.i + c.w) / f.t.getImageWidth()) * f.t.getWidth(), 0);
            glVertex2d(x + c.w, y);
            glTexCoord2d(((double) (c.i + c.w) / f.t.getImageWidth()) * f.t.getWidth(), f.t.getHeight());
            glVertex2d(x + c.w, y + f.h);
            glTexCoord2d(((double) (c.i) / f.t.getImageWidth()) * f.t.getWidth(), f.t.getHeight());
            glVertex2d(x, y + f.h);
        }
        glEnd();
    }

    public int h;
    public Font f;
    transient BufferedImage i;
    transient FontMetrics fm;
    transient Graphics2D g;
    transient public byte[] bytes;
    public ZhChar[] ch = new ZhChar[(int) Character.MAX_VALUE];
    Texture t;
    transient int lch = 0;

    public int getWidth(String line) {
        int w = 0;
        for (char c : line.toCharArray()) {
            if (ch[(int) c] != null) {
                w += ch[(int) c].w;
            }
        }
        return w;
    }

    private String md5encode(String fontName, int style, int size) {
        try {
            String plaintext = fontName + "-" + style + "-" + size;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public FFont(String fontName, int style, int size) {
        String md5 = md5encode(fontName, style, size);
        File file = new File("res/fonts/ffont/" + md5);
        if (file.exists()) {
            try {
                ch = new ZhChar[Character.MAX_VALUE];
                ByteBuffer bb = ByteBuffer.wrap(FSerialization.readFromFile(file));
                int l =  bb.getInt();
                for (int j = 0; j < l; j++) {
                    int character = bb.getInt();
                    ZhChar c = new ZhChar(bb.getInt(), bb.getInt());
                    ch[character] = c;
                    ch[character].f = this;
                }
                bytes = FSerialization.readFromFile(new File("res/fonts/ffont/" + md5 + ".png"));
                InputStream is = new ByteArrayInputStream(bytes);
                t = TextureLoader.getTexture("PNG", is);
            } catch (IOException ex) {
                Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            char[] c = "1234567890ABCCDEFGHIJKLMNOPQRSTUVWXYZÜÖÄabcdefghijklmnopqrstuvwxyzüöäß`'[](){}<>:,-‒.?\";/|\\!@#$%^&*_ +-*=§АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
            i = new BufferedImage(size * c.length, size * 3 / 2, BufferedImage.TYPE_4BYTE_ABGR);
            g = (Graphics2D) i.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB);
            g.setColor(java.awt.Color.white);
            File fileFont = new File("res/fonts/ttf/" + fontName + ".ttf");
            if (fileFont.exists()) {
                try {
                    f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fileFont)).deriveFont((float) size);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FontFormatException ex) {
                    Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                f = new Font(fontName, style, size);
            }
            g.setFont(f);
            fm = g.getFontMetrics(f);
            for (char j : c) {
                renderChar(j);
            }
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream(Short.MAX_VALUE);
                new File("res/fonts/ffont/" + md5 + ".png").createNewFile();
                FileOutputStream fos = new FileOutputStream(new File("res/fonts/ffont/" + md5 + ".png"));
                ImageIO.write(i, "png", os);
                ImageIO.write(i, "png", fos);
                bytes = os.toByteArray();
                InputStream is = new ByteArrayInputStream(bytes);
                t = TextureLoader.getTexture("PNG", is);
            } catch (IOException ex) {
                Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ByteBuffer bb = ByteBuffer.allocate(c.length * 12 + 4);
                bb.putInt(c.length);
                for (int j = 0; j < c.length; j++) {
                    if (ch[(int) c[j]] != null) {
                        bb.putInt((int) c[j]);
                        bb.putInt(ch[(int) c[j]].i);
                        bb.putInt(ch[(int) c[j]].w);
                    }
                }
                FSerialization.writeToFile(bb.array(), file);
            } catch (IOException ex) {
                Logger.getLogger(FFont.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        t.setTextureFilter(GL_NEAREST);
        h = size * 3 / 2;
    }

    private void renderChar(char c) {
        if (ch[(int) c] == null) {
            int w = fm.charWidth(c);
            g.drawString(c + "", lch, f.getSize());
            ch[(int) c] = new ZhChar(lch, w);
            ch[(int) c].f = this;
            lch += w;
        }
    }

    public void render(Object o, double x, double y, FColor color) {
        if (o == null) {
            return;
        }
        String string = o.toString();
        color.bind();
        t.bind();
        for (char c : string.toCharArray()) {
            ZhChar z = ch[(int) c];
            z.render((int) x, (int) y);
            x += z.w;
        }
    }

    public void render(Object o, double x, double y, FAlignment align, FColor color) {
        if (o == null) {
            return;
        }
        String string = o.toString();
        color.bind();
        t.bind();
        if (align == FAlignment.CENTER) {
            x -= getWidth(string) / 2;
        } else if (align == FAlignment.RIGHT) {
            x -= getWidth(string);
        }
        for (char c : string.toCharArray()) {
            ZhChar z = ch[(int) c];
            z.render((int) x, (int) y);
            x += z.w;
        }
    }

}
