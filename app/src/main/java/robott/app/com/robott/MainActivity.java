package robott.app.com.robott;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button wifi;
    Button info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            wifi = (Button)findViewById(R.id.Wifi);
            info = (Button)findViewById(R.id.INFO);


        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("WIFI", "wifi klicked");

                Intent intent = new Intent(MainActivity.this, CamerActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });



        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("INFO", "info klicked");
            }
        });

    }


    public AlertDialog.Builder buildphotoDialog(Context c,String location) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Your location is.");
        builder.setMessage(location);
        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();

                    }
                });

        return builder;
    }


}
