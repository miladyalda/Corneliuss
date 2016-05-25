package robott.app.com.robott;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by my on 2016-05-12.
 */

//denna class används inte.
    public class ReceiveMessage extends AsyncTask<String, Void, Void> {
    private Exception exception;
    String response = "";


    @Override
    protected Void doInBackground(String... params) {
        Log.d("recenmasseagagasgs","1");
        try {
            Log.d("recenmasseagagasgs","2");
            try {


                Log.d("recenmasseagagasgs","3");



                Socket socket = new Socket("192.168.42.1",8888);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[4];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

                //skicka -1 efter batteri status så kanske den funkar
                bytesRead = inputStream.read(buffer);
                if(bytesRead == 4) {
                    int BatteryLevel = 0;
                    BatteryLevel |= ( (int)buffer[3] & 0x000000FF ) << 24;
                    BatteryLevel |= ( (int)buffer[2] & 0x000000FF ) << 16;
                    BatteryLevel |= ( (int)buffer[1] & 0x000000FF ) << 8;
                    BatteryLevel |= ( (int)buffer[0] & 0x000000FF ) << 0;

                    Log.d("Cornelius", "data is: " + buffer[0] + ", "+ buffer[1] + ", "+ buffer[2] + ", "+ buffer[3] );
                    Log.d("Cornelius", "Jonte was here");
                    Log.d("Cornelius", "Battery is: " + BatteryLevel );
                }





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

