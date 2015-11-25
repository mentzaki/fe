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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fax
 */
public class ClientHandler {
    Socket s;
    Thread receiver = new Thread(toString() + "_thread"){

        @Override
        public void run() {
            try{
                receive();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    
    Timer senderTimer = new Timer(toString() + "_sender");
    private DataOutputStream bos;
    private DataInputStream bis;
    
    TimerTask sender = new TimerTask() {

        @Override
        public void run() {
            try {
                send();
            } catch (Exception ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    };
    
    Server server;

    public ClientHandler(Server serv, Socket s) {
        try {
            this.server = serv;
            this.s = s;
            bis = new DataInputStream(s.getInputStream());
            receiver.start();
            bos = new DataOutputStream(s.getOutputStream());
            senderTimer.schedule(sender, 0, 100);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receive() throws Exception{
        while(true){
            System.out.println(bis.read(new byte[4]));
        }
    }
    
    public void send() throws Exception{
        System.out.println("Sending");
        FByteBuffer bb = new FByteBuffer();
        bb.putByte((byte)(server.playing()?1:0));
        bb.putInt(server.world.seed);
        String message = "Something good";
        bb.putBytes(message.getBytes());
        byte b[] = bb.toByteArray();
        System.out.println(Arrays.toString(b));
        bos.writeInt(b.length);
        bos.write(b);
    }
}
