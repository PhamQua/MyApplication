package model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.joker.henry.molefromhole.MainActivity;
import com.joker.henry.molefromhole.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Henry on 4/10/2018.
 */

public class Hole {
    public ImageView imgHole;
    public RadioButton isMole;

    private int mSumTimer, mInterval;
    private int mWidth, mHeight;

    public boolean isSoil,isExist;
    private Context context;

    public Hole() {
    }

    public Hole(int mSumTimer, int mInterval, int mWidth, int mHeight, boolean isSoil, Context context) {
        this.mSumTimer = mSumTimer;
        this.mInterval = mInterval;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.isSoil = isSoil;
        this.context = context;

        isMole = new RadioButton(context);
        isMole.setChecked(false);
        isExist = true;
    }

    int count = 0;
    boolean revert = false;
    CountDownTimer timer1, timer2;
    
    public void getWidget(final Resources resources, final String packageName){

        if(isSoil == true) {
            Bitmap bmp= BitmapFactory.decodeResource(resources, R.mipmap.is_grass);
            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, mWidth, mHeight, true);
            imgHole.setImageBitmap(resizedbitmap);
        }
        else {
            Bitmap bmp= BitmapFactory.decodeResource(resources, R.mipmap.hole_empty);
            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, mWidth, mHeight, true);
            imgHole.setImageBitmap(resizedbitmap);
        }

        imgHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSoil == false && isMole.isChecked() == true){
                    timer1.cancel();
                    count = 0;
                    imgHole.setEnabled(false);
                    String[] listHit = resources.getStringArray(R.array.list_hit);
                    final ArrayList<String> arrayHitImage = new ArrayList<String>(Arrays.asList(listHit));

                    MainActivity.addScoreInLevel();
                    MainActivity.setInf();

                    timer2 = new CountDownTimer(mInterval+mInterval/4,mInterval/3) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int res = resources.getIdentifier(arrayHitImage.get(count),"mipmap",packageName);

//                            imgHole.setImageResource(res);
                            Bitmap bmp= BitmapFactory.decodeResource(resources, res);
                            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, mWidth, mHeight, true);
                            imgHole.setImageBitmap(resizedbitmap);


                            count ++;
                        }
                        @Override
                        public void onFinish() {
                            isMole.setChecked(false);
                            imgHole.setEnabled(true);
                        }
                    }.start();
                }
            }
        });
        
        isMole.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    count = 0;
                    imgHole.setEnabled(true);

                    String[] listAnimation = resources.getStringArray(R.array.list_animation);
                    final ArrayList<String> arrayAnimationImage = new ArrayList<String>(Arrays.asList(listAnimation));

                    timer1 = new CountDownTimer(mSumTimer,mInterval){
                        @Override
                        public void onTick(long millisUntilFinished) {

                            int res = resources.getIdentifier(arrayAnimationImage.get(count),"mipmap",packageName);

                            Bitmap bmp= BitmapFactory.decodeResource(resources, res);
                            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, mWidth, mHeight, true);
                            imgHole.setImageBitmap(resizedbitmap);

                            if(revert == false)
                                count++;
                            else
                                count --;
                            if(count == 4 && imgHole.getParent() != null) {
                                imgHole.setEnabled(false);


                                count = 1;
                                revert = true;
                            }
                        }

                        @Override
                        public void onFinish() {
                            isMole.setChecked(false);
                            revert = false;
                            if(isExist == true)
                                MainActivity.mLife --;
                            Bitmap bmp= BitmapFactory.decodeResource(resources, R.mipmap.hole_empty);
                            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, mWidth, mHeight, true);
                            imgHole.setImageBitmap(resizedbitmap);
                            if(MainActivity.mLife <=0)
                                MainActivity.mLife =0;
                            MainActivity.setInf();
                        }
                    }.start();
                }
            }
        });
//        isMole.setChecked(true);
    }
}
