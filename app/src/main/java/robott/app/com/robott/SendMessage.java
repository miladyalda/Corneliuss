package robott.app.com.robott;

/**
 * Created by my on 2016-04-07.
 */
import android.app.Application;
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
    int BatteryLevel;
    static float BatteryPercentage;

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

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[4];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

                //skicka -1 efter batteri status så kanske den funkar
                bytesRead = inputStream.read(buffer);
                if(bytesRead == 4) {
                    BatteryLevel = 0;
                    BatteryLevel |= ( (int)buffer[3] & 0x000000FF ) << 24;
                    BatteryLevel |= ( (int)buffer[2] & 0x000000FF ) << 16;
                    BatteryLevel |= ( (int)buffer[1] & 0x000000FF ) << 8;
                    BatteryLevel |= ( (int)buffer[0] & 0x000000FF ) << 0;

                    Log.d("Cornelius", "Battery is: " + BatteryLevel );

                    BatteryPercentage = 100.0f * ( BatteryLevel - 496.0f ) / ( 573.0f - 496.0f );
                    if( BatteryPercentage > 100.0f )
                        BatteryPercentage = 100.0f;
                    else if( BatteryPercentage < 0.0f )
                        BatteryPercentage = 0.0f;

                    Log.d("Cornelius", "Battery percentage is: " + BatteryPercentage);

                }



            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);

            }

        } catch (Exception e) {
            this.exception = e;
            return null;
        }
        return null;
    }
    public static float getBatteryPercentage(){
        return BatteryPercentage;
    }

}
