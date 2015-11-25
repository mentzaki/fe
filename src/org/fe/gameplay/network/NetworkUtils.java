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
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fe.main.FData;

/**
 *
 * @author fax
 */
public class NetworkUtils {

    public static void checkHosts(String subnet) {
        int timeout = 100;
        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    System.out.println(host + " is reachable");
                } else {
                    System.out.println(host + " isn't reachable");
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static FData request(String... args) {
        FData fd = null;
        try {
            String s = NetworkConnection.network.get("host").toString();
            if (args.length > 0) {
                s += '?';
                for (int i = 0; i < args.length; i += 2) {
                    if (i != 0) {
                        s += '&';
                    }
                    s += args[i] + '=' + URLEncoder.encode(args[i + 1]);

                }
            }
            URL u = new URL(s);
            HttpURLConnection c;
            c = (HttpURLConnection) u.openConnection();

            URLConnection conn;
            conn = u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String receivedString = new String();
            Scanner scanner = new Scanner(c.getInputStream());
            while (scanner.hasNextLine()) {
                receivedString += scanner.nextLine() + '\n';
            }
            fd = FData.fromString(receivedString);
            return fd;
        } catch (ProtocolException ex) {
            Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            fd = new FData(null);
            fd.add("error", ex.getClass());
            fd.add("description", Arrays.toString(ex.getStackTrace()));
            fd.add("result = error");
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            fd = new FData(null);
            fd.add("error", ex.getClass());
            fd.add("description", Arrays.toString(ex.getStackTrace()));
            fd.add("result = error");
        }
        return fd;
    }
}
