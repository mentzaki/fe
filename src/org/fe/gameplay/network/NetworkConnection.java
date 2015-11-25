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

import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FData;
import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.ice4j.ice.Agent;
import org.ice4j.ice.harvest.StunCandidateHarvester;

/**
 *
 * @author fax
 */
public class NetworkConnection {

    public static boolean initialized;

    static FData network;
    static int port;
    static Agent agent;

    public static String getLocalIp() {
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address) {

                    if (ip.isSiteLocalAddress()) {

                        ipAddress = ip.getHostAddress();
                    }

                }

            }
        }
        return ipAddress;
    }

    public static void init() {
        Thread t = new Thread("Background network initialization thread") {

            @Override
            public void run() {
                initNetworkConnection();
            }

        };
        t.start();
    }

    public static void initNetworkConnection() {
        
    }

}
