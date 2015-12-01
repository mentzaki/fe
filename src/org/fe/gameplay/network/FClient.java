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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.fe.Main;
import org.fe.declaration.Scenes;
import org.fe.gameplay.world.Player;
import org.fe.gameplay.world.World;
import org.fe.graphics.FColor;

/**
 *
 * @author fax
 */
public class FClient {

    ChatRoom chatRoom = new ChatRoom(this);

    World world;

    DatagramSocket socket;

    InetAddress ip;
    int port;

    String message = "";

    int timer;

    FColor color;

    int bufferSize = 2048;
    byte[] sendBuffer = new byte[bufferSize],
            receiveBuffer = new byte[bufferSize];

    public FClient(FColor c, String address) {
        color = c;
        String t[] = address.split(":");
        address = address = t[0];
        int port = Integer.parseInt(t[1]);
        try {
            init(address, port);
        } catch (Exception ex) {
            Logger.getLogger(FClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FClient(FColor c, String ip, int port) {
        color = c;
        try {
            init(ip, port);
        } catch (Exception ex) {
            Logger.getLogger(FClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    Timer thread2 = new Timer(25, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            try {
                send();
            } catch (Exception ex) {
                Logger.getLogger(FServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

    private void init(String ip, int port) throws Exception {
        this.ip = InetAddress.getByName(ip);
        this.port = port;
        socket = new DatagramSocket();
        for (int i = 0; i < 5; i++) {
            FByteBuffer bb = new FByteBuffer();
            bb.putByte((byte) 1);
            bb.putInt(Main.DEVICE_INDEX);
            bb.putBytes(System.getProperty("user.name").getBytes());
            bb.putInt(color.getV());
            DatagramPacket dp = new DatagramPacket(bb.toByteArray(), bb.size(), this.ip, port);
            socket.send(dp);
        }
        thread.start();
        thread2.start();
        Scenes.WINDOW.setScene(chatRoom);
    }

    public void send() throws Exception {
        FByteBuffer bb = new FByteBuffer();
        bb.putByte((byte) 2);
        bb.putInt(Main.DEVICE_INDEX);
        bb.putBytes(message.getBytes());
        DatagramPacket p = new DatagramPacket(bb.toByteArray(), bb.size(), ip, port);
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                message = "";
            }
        }

        socket.send(p);
    }

    public void receiveDatagram() throws Exception {
        DatagramPacket p = new DatagramPacket(receiveBuffer, 2048);
        socket.receive(p);
        byte[] b = new byte[p.getLength()];
        System.arraycopy(receiveBuffer, 0, b, 0, p.getLength());
        FByteBuffer bb = new FByteBuffer(b);
        int type = bb.getByte();
        if (type == 1) {
            int mes = bb.getInt();
            String message = new String(bb.getBytes());
            if (message.length() > 0) {
                chatRoom.addMessage(mes, message);
            }
            int seed = bb.getInt();
            int gametype = bb.getByte();
            if (world == null) {
                world = new World(gametype, seed);
            }
            int players = bb.getByte();
            for (int i = 0; i < players; i++) {
                world.playerHandler[i] = new Player(
                        new FColor(bb.getInt()),
                        new String(bb.getBytes()),
                        bb.getByte(),
                        i
                );
            }
        }
    }

}
