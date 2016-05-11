package robott.app.com.robott;

import android.content.Context;
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
import android.widget.RelativeLayout;

import java.io.IOException;

import android.media.MediaPlayer.OnErrorListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class CamerActivity extends AppCompatActivity implements OnErrorListener, SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnVideoSizeChangedListener,MediaPlayer.OnPreparedListener {


    RelativeLayout layout_joystick;

    RelativeLayout layout_joystickPantilt;
    ImageView image_joystick, image_border;
    private int mCurrentBufferPercentage;
    float motorX = 0;
    float motorY = 0;

    float panTiltX = 0;
    float panTiltY = 2;
    private SurfaceHolder surfaceHolder;

    int progressChange = 0;

    JoyStickClass js;
    JoystickClassPanTilt jsPanTilt;

    private MediaPlayer mp = null;
    SurfaceView mSurfaceView=null;

    TextView batteriStatus;
    SeekBar lightControll;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer);

        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));


        mp = new MediaPlayer();

        batteriStatus = (TextView)findViewById(R.id.batterStatus);

        //mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        //surfaceHolder = mSurfaceView.getHolder();
        //surfaceHolder.addCallback(this);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick_move);

        layout_joystickPantilt = (RelativeLayout)findViewById(R.id.layout_joystick_pantilt);

        batteriStatus = (TextView)findViewById(R.id.batterStatus);

        lightControll = (SeekBar)findViewById(R.id.seekBarLight);

        lightControll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressChange = progress;

                batteriStatus.setText(progress+"%");

                //0-100
                //Log.d("eeee","" + progressChange);
               // new SendMessage().execute(String.valueOf(motorX) + "," + String.valueOf(motorY) +
                 //       "," + String.valueOf(jsPanTilt.getDistance()) + "," + String.valueOf(jsPanTilt.getStepmotor_direction()) +
                   //     "," + String.valueOf(progressChange));


                new SendMessage().execute(String.valueOf(motorX)
                        + "," + String.valueOf(motorY)
                        + "," + String.valueOf(jsPanTilt.getDistance())
                        + "," + String.valueOf(jsPanTilt.getStepmotor_direction())
                        + "," + String.valueOf(progressChange));

                Log.d("distans", String.valueOf(motorX) + "," + String.valueOf(motorY) +
                        "," + String.valueOf(panTiltX) + "," + String.valueOf(panTiltY) +
                        "," + String.valueOf(progressChange));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        batteriStatus.setText("Batteri");


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
                jsPanTilt.getDistance();

                if (event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_MOVE) {


                     panTiltX = jsPanTilt.getDistance();
                     panTiltY = jsPanTilt.getStepmotor_direction();
                     Log.d("distans",String.valueOf(jsPanTilt.getDistance())
                             + "," + String.valueOf(jsPanTilt.getStepmotor_direction()));

                        new SendMessage().execute(String.valueOf(motorX)
                                + "," + String.valueOf(motorY)
                                + "," + String.valueOf(panTiltX)
                                + "," + String.valueOf(panTiltY)
                                + "," + String.valueOf(progressChange));


                    int direction = jsPanTilt.get4Direction();


                    if (direction == JoystickClassPanTilt.STICK_UP) {


                    } else if (direction == JoystickClassPanTilt.STICK_UPRIGHT) {


                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                    } else if (direction == JoystickClassPanTilt.STICK_DOWNRIGHT) {


                    } else if (direction == JoystickClassPanTilt.STICK_DOWN) {


                    } else if (direction == JoystickClassPanTilt.STICK_DOWNLEFT) {


                    } else if (direction == JoystickClassPanTilt.STICK_LEFT) {

                    } else if (direction == JoystickClassPanTilt.STICK_UPLEFT) {


                    } else if (direction == JoystickClassPanTilt.STICK_NONE) {


                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {


                    panTiltX = 0;
                    panTiltY = 2;


                    new SendMessage().execute(String.valueOf(motorX)
                            + "," + String.valueOf(motorY)
                            + "," + String.valueOf(panTiltX)
                            + "," + String.valueOf(panTiltY)
                            + "," + String.valueOf(progressChange));


                }


                return true;
            }
        });

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {

                js.drawStick(arg1);
                js.getDistance();

                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                  //  Log.d("distans",String.valueOf(jsPanTilt.getDistance())+ "," + String.valueOf(jsPanTilt.getStepmotor_direction()));
                    motorX = js.getspeedX();
                    motorY = js.getspeedY();
                    new SendMessage().execute(String.valueOf(motorX)
                            + "," + String.valueOf(motorY)
                            + "," + String.valueOf(jsPanTilt.getDistance())
                            + "," + String.valueOf(jsPanTilt.getStepmotor_direction())
                            + "," + String.valueOf(progressChange));

                    int direction = js.get4Direction();


                    if (direction == JoyStickClass.STICK_UP) {


                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {


                    } else if (direction == JoyStickClass.STICK_RIGHT) {

                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {


                    } else if (direction == JoyStickClass.STICK_DOWN) {


                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {


                    } else if (direction == JoyStickClass.STICK_LEFT) {

                    } else if (direction == JoyStickClass.STICK_UPLEFT) {


                    } else if (direction == JoyStickClass.STICK_NONE) {


                    }

                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {

                    motorX = 0;
                    motorY = 0;
                    new SendMessage().execute(String.valueOf(motorX)
                            + "," + String.valueOf(motorY)
                            + "," + String.valueOf(panTiltX)
                            + "," + String.valueOf(panTiltY)
                            + "," + String.valueOf(progressChange));


                    Log.d("distans", "fuuuuuuuuuuuuucccccccccccckkkkkkkkkkkkkkkkkkkkkkkkkk");

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


        Log.d("surfaceCreated","cccccccccccccccccccccccccreatedddddddddddddddddddddddd");


        //problem med rtsp med hhtp funkar utmärk
        //rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov
        Uri video = Uri.parse("http://192.168.42.1:8080");

        try {

            mp = new MediaPlayer();
            Log.d("surfaceCreated","cccccccccccccccccccccccccreatedddddddddddddddddddddddd");
            mp.setDisplay(surfaceHolder);
            mp.setOnVideoSizeChangedListener(this);
            mp.setOnErrorListener(this);
            mp.setOnBufferingUpdateListener(this);
            this.mCurrentBufferPercentage = 0;
            mp.setDataSource(String.valueOf(video));
            mp.prepare();


        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get the dimensions of the video
        int videoWidth = mp.getVideoWidth();
        int videoHeight = mp.getVideoHeight();

        //Get the width of the screen
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        //Get the SurfaceView layout parameters
        android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();

        //Set the width of the SurfaceView to the width of the screen
        lp.width = screenWidth;

        //Set the height of the SurfaceView to match the aspect ratio of the video
        //be sure to cast these as floats otherwise the calculation will likely be 0
        lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);

        //Commit the layout parameters
        mSurfaceView.setLayoutParams(lp);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer arg0) {
                mp.start();

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

       //new SendMessage().execute(String.valueOf(0) + "," + String.valueOf(0) +
         //    "," + String.valueOf(0) + "," + String.valueOf(2));
       /// mp.stop();
        //mp.release();

        Log.d("OnStop", "onstop methooooood calllleeeeddd");


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

        this.mCurrentBufferPercentage = percent;

    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }


    //580=3.8volt<= max 489=3.1volt <=min batteriet dor vid 3.1 volt så skillnade blir 91
    //och do tar jag medelverde under tre minuer och kolla skillande med max verde sen tar jag skillnad med 91:$.
    public double BatteryFunc(float v) {

        double temp = 580-v;
        return 91-temp;
    }
}


