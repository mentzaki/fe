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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.fe.gameplay.world.World;
import org.fe.graphics.FColor;

/**
 *
 * @author fax
 */
public class FServer {

    int players;
    
    ArrayList<FUser> users = new ArrayList<FUser>();
    ArrayList<FClientHandler> fch = new ArrayList<FClientHandler>();

    int port = 21115;
    int bufferSize = 2048;
    byte[] sendBuffer = new byte[bufferSize],
            receiveBuffer = new byte[bufferSize];

    DatagramSocket socket;

    World world;

    Thread thread = new Thread(toString() + " receiver") {

        @Override
        public void run() {
            try {
                while (true) {
                    receiveDatagram();
                }
            } catch (Exception ex) {
                Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    };

    Timer thread2 = new Timer(1, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            try {
                handle();
            } catch (Exception ex) {
                Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    Timer thread3 = new Timer(100, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            try {
                send();
            } catch (Exception ex) {
                Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });


    public FServer(int port, int player) {
        players = player;
        this.world = new World(player);
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        thread.start();
        thread2.start();
        thread3.start();
    }

    public void handle() throws Exception {
        if (fch.size() > 0) {
            FClientHandler f = fch.get(0);
            fch.remove(0);
            f.handle();
        }
    }
    
    public void send() throws Exception {
        for (int i = 0; i < users.size(); i++) {
            FUser user = users.get(i);
            
        }
    }

    public void receiveDatagram() throws Exception {
        DatagramPacket p = new DatagramPacket(receiveBuffer, 2048);
        socket.receive(p);
        byte[] b = new byte[p.getLength()];
        System.arraycopy(receiveBuffer, 0, b, 0, p.getLength());
        FByteBuffer fbb = new FByteBuffer(b);
        fch.add(new FClientHandler(this, p, fbb));
    }

}
