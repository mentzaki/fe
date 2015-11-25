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

import com.sun.stun.StunClient;
import static org.fe.gameplay.network.NetworkConnection.agent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.gameplay.world.World;
import org.ice4j.Transport;
import org.ice4j.ice.IceMediaStream;

/**
 *
 * @author fax
 */
public class Server {

    public static Server currentServer;
    
    public World world;

    private boolean open = true;
    
    public boolean playing(){
        return !isOpened();
    }

    public void close() {
        open = false;
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isOpened() {
        return open && players < maxPlayers;
    }

    public int port() {
        return port;
    }

    int maxPlayers, players;

    private int port;
    ServerSocket ss;

    ArrayList<ClientHandler> ch = new ArrayList<ClientHandler>();

    public Server(int players) {
        world = new World(players);
        if (currentServer != null) {
            currentServer.close();
        }
        currentServer = this;
        maxPlayers = players;
        port = 21115;
        Thread t = new Thread() {

            @Override
            public void run() {

                try {
                    init();
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        t.start();
    }

    public void init() throws Exception {
        System.out.println("Opened server...");
        ss = new ServerSocket(port);
        while (isOpened()) {
            System.out.println("Waiting for new player.");
            Socket user = ss.accept();
            players++;
            System.out.println(user.getInetAddress() + " is connected!");
            ch.add(new ClientHandler(this, user));
        }
        System.out.println("Server is full!");
    }

}
