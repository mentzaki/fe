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

import java.util.ArrayList;
import java.util.Date;
import org.fe.graphics.FColor;
import org.fe.graphics.FFont;

/**
 *
 * @author fax
 */
public class Message {

    Message parent;
    public String message, name, addTime;
    public int y = 15;
    public int timer = 6000;

    public Message(Message parent, String message) {
        this.parent = parent;
        if (message.contains(":")) {
            name = message.substring(0, message.indexOf(':'));
            this.message = message.substring(message.indexOf(':'));
        } else {
            name = message;
        }
        Date d = new Date();
        this.addTime = format(d.getMinutes()) + ":" + format(d.getSeconds());
    }

    public String format(Object o) {
        String d = o.toString();
        if (d.length() == 1) {
            return "0" + d;
        } else {
            return d;
        }
    }

    public void tick(ArrayList<Message> arr) {
        timer--;
        if (timer < 0 && (parent != null ? parent.y < -20 : true)) {
            y--;
        }
        if (y < -20) {
            arr.remove(this);
        }
    }

    public int y(ArrayList<Message> arr) {
        int ry = 0;
        if (parent != null && arr.contains(parent)) {
            ry = Math.max(15, parent.y(arr) + y);
        } else {
            ry = y;
        }
        return ry;
    }

    public void render(ArrayList<Message> arr) {
        int y = y(arr);
        FFont.font.render(addTime, 15, y + 1, FColor.black);
        FFont.font.render(addTime, 15, y, FColor.gray);
        FFont.font.render(name, 70, y + 1, FColor.black);
        FFont.font.render(name, 70, y, new FColor(1f, 0.5f, 1f));
        int w = FFont.font.getWidth(name);
        FFont.font.render(message, 70 + w, y + 1, FColor.black);
        FFont.font.render(message, 70 + w, y, FColor.white);
    }
}
