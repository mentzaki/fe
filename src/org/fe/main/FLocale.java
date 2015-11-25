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
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.Main;
import org.fe.graphics.FImage;

/**
 *
 * @author yew_mentzaki
 */
public class FLocale {

    protected static int currentLocale, defaultLocale;
    protected static FLocale[] locales;
    protected static String localeName;

    public static String getLocaleName() {
        return localeName;
    }

    public static void set(String locale) {
        localeName = locale;
        for (FLocale f : locales) {
            if (f.name.equals(locale)) {
                currentLocale = f.index;
                return;
            }
        }
    }

    public static FData getAsData() {
        return locales[currentLocale].table;
    }

    public static String get(String key) {
        if (key == null) {
            return null;
        }
        String k[] = key.toLowerCase().split("\\.");
        FData fd = locales[currentLocale].table;
        for (String k1 : k) {
            fd = fd.get(k1);
        }
        String s = fd.toString();
        if (s != null) {
            return s;
        } else if (currentLocale != defaultLocale) {
            fd = locales[defaultLocale].table;
            for (String k1 : k) {
                fd = fd.get(k1);
            }
            s = fd.toString();
            if (s != null) {
                return s;
            }
        }
        return key;
    }

    public static void init() {
        File f[] = new File("locale").listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }

        });
        locales = new FLocale[f.length];
        for (int i = 0; i < f.length; i++) {
            locales[i] = new FLocale(i, f[i].getName());
        }
        set(Main.SETTINGS.def("locale").toString("en_US"));
    }

    protected int index;
    protected String name;
    protected FData table;

    protected FLocale(int index, String name) {
        this.index = index;
        this.name = name;
        try {
            table = FData.fromFile(new File("locale/" + name + "/sheet.locale"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FLocale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
