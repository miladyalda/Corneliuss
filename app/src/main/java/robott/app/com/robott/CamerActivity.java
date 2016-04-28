package robott.app.com.robott;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class CamerActivity extends AppCompatActivity implements SurfaceHolder.Callback {


    RelativeLayout layout_joystick;

    RelativeLayout layout_joystickPantilt;

    ImageView image_joystick, image_border;

    TextView textView1, textView2, textView3, textView4, textView5;

    TextView textViewPan1, textViewPan2, textViewPan3, textViewPan4, textViewPan5;
    JoyStickClass js;
    JoystickClassPanTilt jsPanTilt;

    MediaPlayer mp;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer);

        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

        final VideoView myVideoView = (VideoView)findViewById(R.id.videoView);

       // rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov
        String videoSource = "rtsp://192.168.42.1:8001";

        myVideoView.setMediaController(new MediaController(this));
        myVideoView.setVideoURI(Uri.parse(videoSource));
        myVideoView.requestFocus();
        myVideoView.start();



        //String mediaURL = "rtsp://192.168.42.1:8001";
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
        //startActivity(intent);

        textView1 = (TextView)findViewById(R.id.textView1);

        textView2 = (TextView)findViewById(R.id.textView2);

        textView3 = (TextView)findViewById(R.id.textView3);

        textView4 = (TextView)findViewById(R.id.textView4);

        textView5 = (TextView)findViewById(R.id.textView5);





        textViewPan1 = (TextView)findViewById(R.id.textViewPan1);

        textViewPan2 = (TextView)findViewById(R.id.textViewPan2);

        textViewPan3 = (TextView)findViewById(R.id.textViewPan3);

        textViewPan4 = (TextView)findViewById(R.id.textViewPan4);

        textViewPan5 = (TextView)findViewById(R.id.textViewPan5);


        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick_move);

        layout_joystickPantilt = (RelativeLayout)findViewById(R.id.layout_joystick_pantilt);


        js = new JoyStickClass(getApplicationContext()

                , layout_joystick, R.drawable.joysticksmall);

        jsPanTilt = new JoystickClassPanTilt(getApplicationContext()

                , layout_joystickPantilt, R.drawable.joysticksmall);



        js.setStickSize(150, 150);

        js.setLayoutSize(300, 300);//bra måt :D. seekbar rotation 270 == vertical seekbar :D

        js.setLayoutAlpha(200);

        js.setStickAlpha(100);

        js.setOffset(50);//skjusterara lilla cirkel

        js.setMinimumDistance(0);


        jsPanTilt.setStickSize(150, 150);

        jsPanTilt.setLayoutSize(300, 300);//bra måt :D. seekbar rotation 270 == vertical seekbar :D

        jsPanTilt.setLayoutAlpha(200);

        jsPanTilt.setStickAlpha(100);

        jsPanTilt.setOffset(50);//skjusterara lilla cirkel

        jsPanTilt.setMinimumDistance(0);




        layout_joystickPantilt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                jsPanTilt.drawStick(event);

                if (event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_MOVE) {


                    textViewPan1.setText("X : " + String.valueOf(jsPanTilt.getspeedX()));

                    textViewPan2.setText("Y : " + String.valueOf(jsPanTilt.getspeedY()));

                    textViewPan3.setText("Angle : " + String.valueOf(jsPanTilt.getAngle()));
                    textViewPan4.setText("Distance : " + String.valueOf(jsPanTilt.getDistance()));

                    // Log.d("distans",String.valueOf(js.getDistance()));
                //    new SendMessage().execute(String.valueOf(jsPanTilt.getspeedX()) + "," + String.valueOf(jsPanTilt.getspeedY()));


                    int direction = jsPanTilt.get4Direction();


                    if (direction == JoystickClassPanTilt.STICK_UP) {

                        textViewPan5.setText("Direction : Up");


                    } else if (direction == JoystickClassPanTilt.STICK_UPRIGHT) {

                        textViewPan5.setText("Direction : Up Right");

                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                        textViewPan5.setText("Direction : Right");

                    } else if (direction == JoystickClassPanTilt.STICK_DOWNRIGHT) {

                        textViewPan5.setText("Direction : Down Right");

                    } else if (direction == JoystickClassPanTilt.STICK_DOWN) {

                        textViewPan5.setText("Direction : Down");

                    } else if (direction == JoystickClassPanTilt.STICK_DOWNLEFT) {

                        textView5.setText("Direction : Down Left");

                    } else if (direction == JoystickClassPanTilt.STICK_LEFT) {

                        textViewPan5.setText("Direction : Left");
                    } else if (direction == JoystickClassPanTilt.STICK_UPLEFT) {

                        textViewPan5.setText("Direction : Up Left");

                    } else if (direction == JoystickClassPanTilt.STICK_NONE) {

                        textViewPan5.setText("Direction : Center");

                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    textViewPan1.setText("X :");

                    textViewPan2.setText("Y :");


                    textViewPan3.setText("Angle :");

                    textViewPan4.setText("Distance :");

                    textViewPan5.setText("Direction :");

                }


                return true;
            }
        });







        layout_joystick.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {

                js.drawStick(arg1);

                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    textView1.setText("X : " + String.valueOf(js.getspeedX()));

                    textView2.setText("Y : " + String.valueOf(js.getspeedY()));

                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                   // Log.d("distans",String.valueOf(js.getDistance()));
                    new SendMessage().execute(String.valueOf(js.getspeedX())+","+String.valueOf(js.getspeedY()));



                    int direction = js.get4Direction();


                    if(direction == JoyStickClass.STICK_UP) {

                        textView5.setText("Direction : Up");



                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {

                        textView5.setText("Direction : Up Right");

                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");

                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {

                        textView5.setText("Direction : Down Right");

                    } else if(direction == JoyStickClass.STICK_DOWN) {

                        textView5.setText("Direction : Down");

                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {

                        textView5.setText("Direction : Down Left");

                    } else if(direction == JoyStickClass.STICK_LEFT) {

                        textView5.setText("Direction : Left");
                    }
                    else if(direction == JoyStickClass.STICK_UPLEFT) {

                        textView5.setText("Direction : Up Left");

                    } else if(direction == JoyStickClass.STICK_NONE) {

                        textView5.setText("Direction : Center");

                    }

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {

                    textView1.setText("X :");

                    textView2.setText("Y :");


                    textView3.setText("Angle :");

                    textView4.setText("Distance :");

                    textView5.setText("Direction :");

                }

                return true;


            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("OnStart", getIpAddr());
    }

    public String getIpAddr() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        String ipString = String.format(
                "%d.%d.%d.%d",
                (ip & 0xff),
                (ip >> 8 & 0xff),
                (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));

        return ipString;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mp.setDisplay(holder);
        try {
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mp.start();
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
