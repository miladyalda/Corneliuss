package robott.app.com.robott;

/**
 * Created by my on 2016-04-07.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class JoyStickClass {
    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;
    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;

    private int STICK_ALPHA = 200;
    private int LAYOUT_ALPHA = 200;
    private int OFFSET = 0;

    private Context mContext;
    private ViewGroup mLayout;
    private LayoutParams params;
    private int stick_width, stick_height;
    CamerActivity Cam = new CamerActivity();
    private int position_x = 0, position_y = 0, min_distance = 0, max_distance = 127, neg_distance = -127;
    private float distance = 0, angle = 0;

    float rightEngineSpeed;
    float leftEngineSpeed;

    private DrawCanvas draw;
    private Paint paint;
    private Bitmap stick;

    private boolean touch_state = false;

    public JoyStickClass(Context context, ViewGroup layout, int stick_res_id) {
        mContext = context;

        stick = BitmapFactory.decodeResource(mContext.getResources(),
                stick_res_id);

        stick_width = stick.getWidth();
        stick_height = stick.getHeight();

        draw = new DrawCanvas(mContext);
        paint = new Paint();
        mLayout = layout;
        params = mLayout.getLayoutParams();
    }

    public void drawStick(MotionEvent arg1) {
        position_x = (int) (arg1.getX() - (params.width / 2));
        position_y = (int) (arg1.getY() - (params.height / 2));
        distance = (float) Math.sqrt(Math.pow(position_x, 2) + Math.pow(position_y, 2));
        angle = (float) cal_angle(position_x, position_y);


        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            if (distance <= (params.width / 2) - OFFSET) {
                draw.position(arg1.getX(), arg1.getY());
                draw();
                touch_state = true;
            }
        } else if (arg1.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
            if (distance <= (params.width / 2) - OFFSET) {
                draw.position(arg1.getX(), arg1.getY());



                draw();
            } else if (distance > (params.width / 2) - OFFSET) {
                float x = (float) (Math.cos(Math.toRadians(cal_angle(position_x, position_y))) * ((params.width / 2) - OFFSET));
                float y = (float) (Math.sin(Math.toRadians(cal_angle(position_x, position_y))) * ((params.height / 2) - OFFSET));
                x += (params.width / 2);
                y += (params.height / 2);

                draw.position(x, y);
               draw();
            }
        else {
                mLayout.removeView(draw);

            }
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
            mLayout.removeView(draw);
            leftEngineSpeed = 0;
            rightEngineSpeed = 0;
            new SendMessage().execute(String.valueOf(leftEngineSpeed)
                    + "," + String.valueOf(rightEngineSpeed)
                    + "," + String.valueOf(Cam.panTiltX)
                    + "," + String.valueOf(Cam.panTiltY)
                    + "," + String.valueOf(Cam.progressChange));
            touch_state = false;


        }
    }

    public int[] getPosition() {
        if (distance > min_distance && touch_state) {
            return new int[]{position_x, position_y};
        }
        return new int[]{0, 0};
    }

    public int getX() {
        if (distance > min_distance && touch_state) {
            return position_x;
        }
        return 0;
    }

    public int getY() {
        if (distance > min_distance && touch_state) {
            return position_y;
        }
        return 0;
    }

    public float getAngle() {
        if (distance>  min_distance && touch_state) {
            return angle;
        }
        return 0;
    }

    public float getDistance() {
        if (distance > max_distance){
            distance = max_distance;
        }
        if (distance <= max_distance && touch_state) {
            calcspeed();

            return distance;
        }
        return 0;
    }

    public void setMinimumDistance(int minDistance) {
        min_distance = minDistance;
    }

    public int get4Direction() {
        if (distance > min_distance && touch_state) {
            if (angle >= 255 && angle < 285) {
                return STICK_UP;
            } else if (angle >= 350 || angle < 10) {
                return STICK_RIGHT;
            } else if (angle >= 75 && angle < 105) {
                return STICK_DOWN;
            } else if (angle >= 170 && angle < 190) {
                return STICK_LEFT;
            }
        } else if (distance <= min_distance && touch_state) {
            return STICK_NONE;
        }
        return 0;
    }

    public void setOffset(int offset) {
        OFFSET = offset;
    }

    public void setStickAlpha(int alpha) {
        STICK_ALPHA = alpha;
        paint.setAlpha(alpha);
    }

    public void setLayoutAlpha(int alpha) {
        LAYOUT_ALPHA = alpha;
        mLayout.getBackground().setAlpha(alpha);
    }

    public void setStickSize(int width, int height) {
        stick = Bitmap.createScaledBitmap(stick, width, height, false);
        stick_width = stick.getWidth();
        stick_height = stick.getHeight();
    }

    public void setLayoutSize(int width, int height) {
        params.width = width;
        params.height = height;
    }

    private double cal_angle(float x, float y) {
        if (x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if (x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    private void draw() {
        try {
            mLayout.removeView(draw);
        } catch (Exception e) {
        }
        mLayout.addView(draw);
    }

    private class DrawCanvas extends View {
        float x, y;

        private DrawCanvas(Context mContext) {
            super(mContext);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawBitmap(stick, x, y, paint);
        }

        private void position(float pos_x, float pos_y) {
            x = pos_x - (stick_width / 2);
            y = pos_y - (stick_height / 2);
        }
    }


    public void calcspeed(){

        float xMultiplier = -1.0f;
        float yMultiplier = -1.0f;


        rightEngineSpeed = xMultiplier * getX() + yMultiplier * getY();
        leftEngineSpeed = yMultiplier * getY() - xMultiplier * getX();



       Log.d("x,y",""+leftEngineSpeed+" "+rightEngineSpeed);


    }

    public float getspeedX(){


        if (leftEngineSpeed > max_distance) {
            leftEngineSpeed = max_distance;

        }else if (leftEngineSpeed < neg_distance) {
            leftEngineSpeed = neg_distance;

        }
         else if (get4Direction()==STICK_UP) {
            return leftEngineSpeed;

        } else if (get4Direction()==STICK_DOWN) {
            return leftEngineSpeed;

        } else if (get4Direction()==STICK_LEFT) {
            return -1*rightEngineSpeed;


        }else if (get4Direction()==STICK_RIGHT) {
            return leftEngineSpeed;

        }


        return leftEngineSpeed;
    }


    public float getspeedY(){


        if(rightEngineSpeed > max_distance){
            rightEngineSpeed = max_distance;

        }else if (rightEngineSpeed < neg_distance) {
            rightEngineSpeed = neg_distance;
        }
         else if (get4Direction()==STICK_UP) {
            return leftEngineSpeed;

        } else if (get4Direction()==STICK_DOWN) {
            return leftEngineSpeed;

        } else if (get4Direction()==STICK_LEFT) {
            return rightEngineSpeed;


        }else if (get4Direction()==STICK_RIGHT) {
            return -1*leftEngineSpeed;

        }

        return rightEngineSpeed;
    }
}