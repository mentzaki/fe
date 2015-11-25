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
package org.fe.main;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fax
 */
public class FNetworkSerialization {

    public static byte[] serialize(Object o) {
        Class clazz = o.getClass();
        ByteBuffer bb = ByteBuffer.allocate(clazz.getFields().length * 8);
        int ln = 0;
        try {
            for (Field f : clazz.getFields()) {
                if (f.getType() == int.class) {
                    bb.putInt(f.getInt(o));
                    ln += 4;
                }
                if (f.getType() == double.class) {
                    bb.putDouble(f.getDouble(o));
                    ln += 8;
                }
                if (f.getType() == float.class) {
                    bb.putFloat(f.getFloat(o));
                    ln += 4;
                }
                if (f.getType() == long.class) {
                    bb.putLong(f.getLong(o));
                    ln += 8;
                }
                if (f.getType() == short.class) {
                    bb.putShort(f.getShort(o));
                    ln += 2;
                }
                if (f.getType() == byte.class) {
                    bb.put(f.getByte(o));
                    ln += 1;
                }
                if (f.getType() == char.class) {
                    bb.putChar(f.getChar(o));
                    ln += 2;
                }
                if (f.getType() == boolean.class) {
                    boolean b = f.getBoolean(o);
                    if (b) {
                        bb.put((byte) 1);
                    } else {
                        bb.put((byte) 0);
                    }
                    ln += 1;
                }
            }

            return Arrays.copyOf(bb.array(), ln);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FNetworkSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void merge(Object origin, byte[] b, boolean owner) {
        Class clazz = origin.getClass();
        ByteBuffer bb = ByteBuffer.wrap(b);
        try {
            for (Field f : clazz.getFields()) {

                boolean o = true;
                if(!owner){
                    String n = f.getName();
                    if (n.length() > 5) {
                        n = n.substring(0, 5);
                        if (n.equals("_own_")) {
                            o = false;
                        }
                    }
                }

                if (f.getType() == int.class) {
                    int v = bb.getInt();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == double.class) {
                    double v = bb.getDouble();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == float.class) {
                    float v = bb.getFloat();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == long.class) {
                    long v = bb.getLong();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == short.class) {
                    short v = bb.getShort();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == byte.class) {
                    byte v = bb.get();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == char.class) {
                    char v = bb.getChar();
                    if (o) {
                        f.set(origin, v);
                    }
                }
                if (f.getType() == boolean.class) {
                    byte v = bb.get();
                    if (o) {
                        f.set(origin, (v == (byte) 1));
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FNetworkSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
