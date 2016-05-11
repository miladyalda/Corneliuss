package robott.app.com.robott;

/**
 * Created by my on 2016-04-07.
 */
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mina on 1/23/16.
 */
public class SendMessage extends AsyncTask<String, Void, Void> {
    private Exception exception;
    String response = "";

    @Override
    protected Void doInBackground(String... params) {
        try {
            try {


                Socket socket = new Socket("192.168.42.1",8888);
                PrintWriter outToServer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                outToServer.print(params[0]);
                outToServer.flush();

                //*********************************************************************************************\\
/*
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

                //skicka -1 efter batteri status så kanske den funkar
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");

                    Log.d("sssssssssssssssssssssssssssssssssssssssssssssssss", response);


                }
            */
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            this.exception = e;
            return null;
        }
        return null;
    }

}
