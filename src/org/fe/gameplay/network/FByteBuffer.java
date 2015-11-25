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
package org.fe.gameplay.network;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author fax
 */
public class FByteBuffer {

    private ArrayList<Byte> bytes = new ArrayList<Byte>();
    private ByteBuffer bb = ByteBuffer.allocate(4);
    private int carret;

    public FByteBuffer() {
    }

    public FByteBuffer(byte[] bytes) {
        putByteSequence(bytes);
    }

    private byte[] getByteSequence(int length) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = getByte();
        }
        return b;
    }

    public int getInt() {
        return ByteBuffer.wrap(getByteSequence(4)).getInt();
    }

    public float getFloat() {
        bb.clear();
        bb.put(getByteSequence(4));
        return bb.getFloat();
    }
    
    public byte[] getBytes() {
        return getByteSequence(getInt());
    }

    public byte getByte() {
        return bytes.get(carret++);
    }

    private void putByteSequence(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            putByte(bytes[i]);
        }
    }

    public void putInt(int value) {
        bb.clear();
        putByteSequence(bb.putInt(value).array());
    }

    public void putFloat(float value) {
        bb.clear();
        putByteSequence(bb.putFloat(value).array());
    }

    public void putByte(byte value) {
        bytes.add(value);
    }

    public void putBytes(byte[] bytes) {
        putInt(bytes.length);
        putByteSequence(bytes);
    }
    
    public int size(){
        return bytes.size();
    }
    
    public byte[] toByteArray() {
        int c = this.carret;
        this.carret = 0;
        byte[] b = getByteSequence(bytes.size());
        this.carret = c;
        return b;
    }

    void clear() {
        bytes.clear();
        carret = 0;
    }

}
