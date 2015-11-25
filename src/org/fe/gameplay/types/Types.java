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

import java.util.ArrayList;
import java.util.List;
import org.fe.gameplay.types.entities.WillRhodes;

/**
 *
 * @author fax
 */
public class Types {

    private static List<Class> entityTypes = new ArrayList<Class>();

    public static Class getClassByID(int id) {
        return entityTypes.get(id);
    }

    public static int getIDOfClass(Class c) {
        return entityTypes.indexOf(c);
    }

    public static int getIDByObject(Entity c) {
        return entityTypes.indexOf(c.getClass());
    }

    public static void init() {
        Faction.init();
    }

    public static void initEntityTypes() {
        entityTypes.add(Entity.class);
        entityTypes.add(WillRhodes.class);

    }
}
