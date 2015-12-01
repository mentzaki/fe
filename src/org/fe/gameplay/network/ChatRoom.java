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
import org.fe.Main;
import static org.fe.Main.GRAPHICS;
import static org.fe.declaration.Scenes.MAIN_MENU;
import org.fe.graphics.FColor;
import org.fe.graphics.FFont;
import org.fe.graphics.FKeyboard;
import org.fe.gui.FScene;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

/**
 *
 * @author fax
 */
public class ChatRoom extends FScene {

    ArrayList<Message> message = new ArrayList<Message>();
    int clr = 0;
    double d = 0;
    int caret = 0;
    int mes = 0;

    FClient fc;

    public ChatRoom(FClient fc) {
        this.fc = fc;
    }

    @Override
    public void tick() {
        if (clr > 0) {
            clr--;
        }
        if (FKeyboard.isKeyDown(Keyboard.KEY_BACK) && yourMessage.length() > 0 && clr <= 0) {
            yourMessage = yourMessage.substring(0, caret - 1) + yourMessage.substring(caret);
            caret--;
            clr = 3;
        }
        if (FKeyboard.isKeyDown(Keyboard.KEY_DELETE) && yourMessage.length() - 1 > caret && clr <= 0) {
            yourMessage = yourMessage.substring(0, caret) + yourMessage.substring(caret + 1);
            clr = 3;
        }
        for (int i = 0; i < message.size(); i++) {
            message.get(i).tick(message);
        }
        d += 0.08;
    }

    public void addMessage(int i, Object s) {
        if (i > mes) {
            mes = i;
            addMessage(s);
        }
    }

    public void addMessage(Object s) {
        if (message.isEmpty()) {
            message.add(new Message(null, s.toString()));
        } else {
            message.add(new Message(message.get(message.size() - 1), s.toString()));
        }
        if (message.size() > (int) ((height - 40) / 15)) {
            for (int i = 0; i < message.size() - (int) ((height - 40) / 15); i++) {
                message.get(i).timer = -1;
                message.get(i).y--;
            }
        }
    }

    String yourMessage = "";

    @Override
    public void render() {
        MAIN_MENU.width = width;
        MAIN_MENU.height = height;
        MAIN_MENU.render();
        GRAPHICS.setColor(new FColor(0, 0, 0, 200).slickColor());
        GRAPHICS.fillRect(0, 0, (int) width, (int) height);
        super.render();
        for (int i = 0; i < message.size(); i++) {
            message.get(i).render(message);
        }
        GRAPHICS.setColor(Color.black);
        GRAPHICS.fillRect(0, (int) height - 20, (int) width, 20);
        FColor color = FColor.white;
        if (yourMessage.length() > 0 && yourMessage.charAt(0) == '/') {
            color = FColor.pink;
        }
        if (yourMessage.length() > 0) {
            if (Math.cos(d) < 0) {
                FFont.font.render(yourMessage, 10, (int) height - 19, color);
            } else {
                FFont.font.render(yourMessage.substring(0, caret) + '|' + yourMessage.substring(caret), 10, (int) height - 19, color);
            }
        }

        if (Keyboard.next()) {
            d = 0;
            if (Keyboard.getEventKey() == Keyboard.KEY_BACK && yourMessage.length() > 0) {
                yourMessage = yourMessage.substring(0, caret - 1) + yourMessage.substring(caret);
                caret--;
                clr = 12;

            } else if (Keyboard.getEventKey() == Keyboard.KEY_DELETE && yourMessage.length() - 1 > caret) {
                yourMessage = yourMessage.substring(0, caret) + yourMessage.substring(caret + 1);
                clr = 12;

            } else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                if (caret > 0) {
                    caret--;
                }
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                if (caret < yourMessage.length() - 2) {
                    caret++;
                }
            } else if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                caret = 0;
            } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                caret = yourMessage.length() - 1;
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                String yourMessage = this.yourMessage;
                if (yourMessage.trim().length() > 0) {
                    fc.message = "[" + Main.SETTINGS.get("nickname").toString() + "]: " + yourMessage;
                    fc.timer = 5;
                    this.yourMessage = "";
                    caret = 0;
                }
            } else if (yourMessage.length() < 120) {
                yourMessage = yourMessage.substring(0, caret) + Keyboard.getEventCharacter() + yourMessage.substring(caret);
                caret++;
            }
        }
    }
}
