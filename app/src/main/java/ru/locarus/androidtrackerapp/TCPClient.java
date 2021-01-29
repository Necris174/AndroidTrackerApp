package ru.locarus.androidtrackerapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import static ru.locarus.androidtrackerapp.Constants.TAG;
public class TCPClient {

    private String SERVER_IP = "91.201.40.21";
    private int SERVER_PORT = 1145;
    private Socket socket;
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток чтения в сокет

    public TCPClient() {
        try {
            socket = new Socket(SERVER_IP,SERVER_PORT);
        }catch (IOException e){
            Log.d(TAG, "TCPClient: Socket failed");
        }
        try {
            // потоки чтения из сокета / записи в сокет, и чтения с консоли
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            new ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
            new WriteMsg().start(); // нить пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
        } catch (IOException e) {
            // Сокет должен быть закрыт при любой
            // ошибке, кроме ошибки конструктора сокета:
            TCPClient.this.downService();
        }
    }
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщения с сервера
                    Log.d(TAG,"Server response" + str) ;// пишем сообщение с сервера на консоль
                }
            } catch (IOException e) {
                TCPClient.this.downService();
            }
        }
    }

    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                        out.write("#P#\r\n");
                        out.flush();// отправляем на сервер
                    }

                catch (IOException e) {
                    TCPClient.this.downService(); // в случае исключения тоже харакири
                }
            }
        }
    }


    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }


}
