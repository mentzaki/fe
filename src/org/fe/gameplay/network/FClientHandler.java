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

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;
import org.fe.graphics.FColor;

/**
 *
 * @author fax
 */
public class FClientHandler {
    
    FServer server;
    DatagramPacket d;
    FByteBuffer b;

    public FClientHandler(FServer server, DatagramPacket d, FByteBuffer b) {
        this.server = server;
        this.d = d;
        this.b = b;
    }

    public void handle() throws IOException {
        int type = b.getByte();
        if (type == 1) {
            if (server.users.size() < server.players) {
                int deviceIndex = b.getInt();
                for (int i = 0; i < server.users.size(); i++) {
                    if (server.users.get(i).deviceIndex == deviceIndex) {
                        return;
                    }
                }
                String name = new String(b.getBytes());
                int v = b.getInt();

                server.users.add(
                        new FUser(d.getAddress(), d.getPort(), deviceIndex, name, new FColor(v))
                );
                server.setMessage("#joined " + name);
            } else {
                b.clear();
                b.putByte((byte) 91);
                server.socket.send(new DatagramPacket(b.toByteArray(), b.size(), d.getAddress(), d.getPort()));
            }
        } else if (type == 2) {
            FUser user = null;
            int deviceIndex = b.getInt();
            for (int i = 0; i < server.users.size(); i++) {
                if (server.users.get(i).deviceIndex == deviceIndex) {
                    user = server.users.get(i);
                    break;
                }
            }
            if (user == null) {
                return;
            }
            user.punch();
            server.setMessage(new String(b.getBytes()));
            
        }
    }

}
