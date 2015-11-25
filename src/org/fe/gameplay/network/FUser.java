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

import java.net.InetAddress;
import org.fe.graphics.FColor;

/**
 *
 * @author fax
 */
public class FUser {

    InetAddress address;
    int deviceIndex, port;
    String name;
    FColor color;

    public FUser(InetAddress address, int port, int deviceIndex, String name, FColor color) {
        this.address = address;
        this.port = port;
        this.deviceIndex = deviceIndex;
        this.name = name;
        this.color = color;
        System.out.println("Connected new user:");
        System.out.println("    " + address.getHostAddress() + ":" + port);
        System.out.println("    " + deviceIndex);
        System.out.println("    " + name);
        System.out.println("    " + color);
    }
}
