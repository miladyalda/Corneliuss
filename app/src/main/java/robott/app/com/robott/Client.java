package robott.app.com.robott;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by my on 2016-04-13.
 */
public class Client extends AsyncTask<String, Void, Void> {


    /**
     * Maximum size of buffer
     */
    public static final int BUFFER_SIZE = 1024;
    private Exception exception;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    String response = "";

    private String host = null;
    private int port = 8888;
    TextView textResponse;


    Client(String host, int port,TextView textResponse) {
        this.host = host;
        this.port = port;
        this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            try {


                Socket socket = new Socket(this.host, this.port);

                PrintWriter outToServer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
                outToServer.print(params[0]);
                outToServer.flush();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

         /*
          * notice: inputStream.read() will block if no data return
          */
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");

                    Log.d("sssss",response);

                }
                }catch(IOException e){
                    e.printStackTrace();
                }

            } catch (Exception e) {
                this.exception = e;
                return null;
            }
            return null;
        }
    }

