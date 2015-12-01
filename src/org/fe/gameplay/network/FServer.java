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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    String message = "";

    ArrayList<FUser> users = new ArrayList<FUser>();
    ArrayList<FClientHandler> fch = new ArrayList<FClientHandler>();

    int mes = 0;
    int timer = 0;

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
        try {
            DatagramPacket p = new DatagramPacket(new byte[]{1, 1, 1, 1}, 4,
                    InetAddress.getByName(NetworkConnection.network.get("punch_server").toString().split(":")[0]),
                    Integer.parseInt(NetworkConnection.network.get("punch_server").toString().split(":")[1])
            );
            System.out.println(p.getAddress());
            System.out.println(p.getPort());
            for (int i = 0; i < 10; i++) {
                socket.send(p);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void setMessage(String message) {
        if (!this.message.equals(message)) {
            mes++;
            this.message = message;
            timer = 10;
        }
    }

    public void handle() throws Exception {
        if (fch.size() > 0) {
            FClientHandler f = fch.get(0);
            fch.remove(0);
            f.handle();
        }
    }

    public void send() throws Exception {
        String message = this.message;
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                this.message = "";
            }
        }
        for (int i = 0; i < users.size(); i++) {
            FUser user = users.get(i);
            FByteBuffer b = new FByteBuffer();
            /* String message = new String(bb.getBytes());
             int seed = bb.getInt();
             int gametype = bb.getByte();
             world = new World(gametype, seed);
             int players = bb.getByte();
             for (int i = 0; i < players; i++) {
             world.playerHandler[i] = new Player(
             new FColor(bb.getInt()),
             new String(bb.getBytes()),
             bb.getByte(),
             i
             );
             }*/
            b.putByte((byte) 1);
            b.putInt(mes);
            b.putBytes(message.getBytes());
            b.putInt(world.seed);
            b.putByte((byte) players);
            b.putByte((byte) users.size());
            for (int j = 0; j < users.size(); j++) {
                FUser u = users.get(j);
                b.putInt(u.color.getV());
                b.putBytes(u.name.getBytes());
                b.putByte((byte) 1);
                b.putByte((byte) j);
            }
            DatagramPacket p = new DatagramPacket(b.toByteArray(), b.size(), user.address, user.port);
            socket.send(p);
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
