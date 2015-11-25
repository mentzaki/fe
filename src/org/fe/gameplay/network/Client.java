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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fax
 */
public class Client {
    String ip;
    int port;
    Socket cs;
    
    public Client(String ip) {
        String t[] = ip.split(":");
        this.ip = ip = t[0];
        this.port = Integer.parseInt(t[1]);
        try {
            cs = new Socket(ip, port);
            DataInputStream bis = new DataInputStream(cs.getInputStream());
            while (true){
                System.out.println("Receiving length");
                int l = bis.read();
                if(l > 0){
                System.out.println("Receiving data");
                byte b[] = new byte[l];
                bis.read(b);
                System.out.println(Arrays.toString(b));
                FByteBuffer bb = new FByteBuffer(b);
                System.out.println(bb.getByte() == 1 ? "Playing" : "Waiting");
                System.out.println(bb.getInt());
                System.out.println(new String(bb.getBytes()));
                }
                /*
                bb.putByte((byte)(server.playing()?1:0));
        bb.putInt(server.world.seed);
        String message = "Something good";
        bb.putBytes(message.getBytes());
        byte b[] = bb.toByteArray();
        bos.write(b.length);
                */
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
