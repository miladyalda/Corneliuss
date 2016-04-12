package robott.app.com.robott;

/**
 * Created by my on 2016-04-07.
 */
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mina on 1/23/16.
 */
public class SendMessage extends AsyncTask<String, Void, Void> {
    private Exception exception;
    Socket socket;
    @Override
    protected Void doInBackground(String... params) {
        try {
            try {


                 socket = new Socket("192.168.42.1",8888);
                PrintWriter outToServer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                outToServer.print(params[0]);
                outToServer.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            this.exception = e;
            return null;
        }
        return null;
    }

    public void close(){

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
