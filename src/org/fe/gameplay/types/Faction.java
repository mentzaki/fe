/*
 * Copyright (C) 2015 fax
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
package org.fe.gameplay.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FData;
import org.fe.main.FLocale;

/**
 *
 * @author fax
 */
public class Faction{
    public static Faction[] factions;
    public FData factionInfo;
    private FData title;
    
    public String getTitle(){
        return title.get(FLocale.getLocaleName()).toString();
    }

    public Faction(File f) {
        try {
            factionInfo = FData.fromFile(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Faction.class.getName()).log(Level.SEVERE, null, ex);
        }
        title = factionInfo.bind("title");
    }
    
    public static void init(){
        File f[] = new File("data/factions").listFiles();
        factions = new Faction[f.length];
        for (int i = 0; i < f.length; i++) {
            factions[i] = new Faction(f[i]);
        }
    }
}
