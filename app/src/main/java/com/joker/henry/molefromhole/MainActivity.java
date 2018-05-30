package com.joker.henry.molefromhole;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import model.Hole;
import model.LevelGame;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    static TextView tvLife, tvScore;
    int displayWidth, displayHeight;
    int levelBegin;
    SharedPreferences sharedPreferences;

    TextView tvPlay, tvCustomGame, tvHighScore,tvExit;
    
    public static int mScore,mLife;
    ArrayList<LevelGame> arrayLevelGame;
    static LevelGame lvNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);

        sharedPreferences = getSharedPreferences("dataHighScore",MODE_PRIVATE);

        arrayLevelGame = new ArrayList<>();
        arrayLevelGame.add(new LevelGame(3,2,4,1000,20,2));//1
        arrayLevelGame.add(new LevelGame(3,2,4,800,60,3));//2
        arrayLevelGame.add(new LevelGame(3,3,6,700,90,4));//3
        arrayLevelGame.add(new LevelGame(3,3,6,600,150,4));//4
        arrayLevelGame.add(new LevelGame(4,3,8,600,180,5));//5
        arrayLevelGame.add(new LevelGame(4,3,8,500,240,6));//6
        arrayLevelGame.add(new LevelGame(4,3,10,400,350,8));//7
        arrayLevelGame.add(new LevelGame(5,3,10,400,400,9));//8
        arrayLevelGame.add(new LevelGame(5,3,10,300,600,10));//9
        arrayLevelGame.add(new LevelGame(5,4,14,300,1000000000,15));//10

        CreateMenu();
    }

    CountDownTimer timerMain;
    private void CreateGame(int mWidthOfkGame, int mHeightOfGame, int mNumberOfHole,int mInterval){

        setInf();

        final ArrayList<Hole> arrayHoles = new ArrayList<>();
        for (int i = 0; i < mHeightOfGame; i++) {
            TableRow tableRow = new TableRow(tableLayout.getContext());
            for (int j = 0; j < mWidthOfkGame; j++) {
                Hole hole = new Hole(mInterval*6+200,mInterval,displayWidth/mWidthOfkGame,displayHeight/mHeightOfGame,true,tableRow.getContext());
                //mSumTimer,mInterval,mWidth,mHeight,isSoil

                hole.imgHole = new ImageView(tableRow.getContext());
                hole.getWidget(getResources(),getPackageName());
                arrayHoles.add(hole);
                tableRow.addView(hole.imgHole);
            }
            tableLayout.addView(tableRow);
        }
        int count = 0;

        final ArrayList<Integer> arrayPlaceHole = new ArrayList<>();
        final Random random = new Random();
        while (count < mNumberOfHole){
            int choose = random.nextInt(arrayHoles.size());
            if(arrayHoles.get(choose).isSoil == true){
                arrayHoles.get(choose).isSoil = false;
//                arrayHoles.get(choose).isMole.setChecked(true);
                arrayPlaceHole.add(choose);
                count++;
            }
        }

        timerMain = new CountDownTimer(2147483647,mInterval/ 3 * 2 ){
            @Override
            public void onTick(long millisUntilFinished) {
                if (mLife > 0) {
                    if(mScore < lvNow.getmScoreNextLv()) {
                        int rand = random.nextInt(arrayPlaceHole.size());
                        if (arrayHoles.get(arrayPlaceHole.get(rand)).isMole.isChecked() == false)
                            arrayHoles.get(arrayPlaceHole.get(rand)).isMole.setChecked(true);
                    }
                    else {
                        for (int i = 0; i < arrayPlaceHole.size(); i++) {
                            if(arrayHoles.get(arrayPlaceHole.get(i)).isExist == true)
                                arrayHoles.get(arrayPlaceHole.get(i)).isExist = false;
                        }
                        timerMain.cancel();
                        levelBegin++;
                        PlayGame(levelBegin);
                    }
                }
                else {
                    this.cancel();

                    final Dialog dialog = new Dialog(MainActivity.this);

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_game_over);
                    dialog.setCanceledOnTouchOutside(false);

                    TextView tvHighScore = (TextView) dialog.findViewById(R.id.tv_game_over_highscore);
                    TextView tvGOScore = (TextView) dialog.findViewById(R.id.tv_game_over_score);
                    Button btnPlayAgain = (Button) dialog.findViewById(R.id.btn_play_again);


                    //lấy giá trị
                    int mHighScore = sharedPreferences.getInt("high_score",0);
                    // lưu giá trị
                    if(mHighScore < mScore) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("high_score", mScore);
                        editor.commit();

                        tvHighScore.setText("High score : "+mScore);
                        tvGOScore.setText("Score "+mScore);
                    }
                    else {
                        tvHighScore.setText("High score : "+mHighScore);
                        tvGOScore.setText("Score "+mScore);
                    }


                    btnPlayAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mLife = 3;
                            mScore = 0;
                            PlayGame(0);
                        }
                    });
                    dialog.show();
                }
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }

    public static void setInf(){
        tvScore.setText("Score "+mScore);
        tvLife.setText("Life "+mLife);
    }
    public static void addScoreInLevel(){
        mScore += lvNow.getmAddScore();
    }

    private void CreateMenu(){
        tvPlay = (TextView) findViewById(R.id.tv_play);
        tvCustomGame = (TextView) findViewById(R.id.tv_custom_game);
        tvHighScore = (TextView) findViewById(R.id.tv_high_score);
        tvExit = (TextView) findViewById(R.id.tv_exit);

        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.main_activity);
                levelBegin = 0;

                mLife = 3;
                mScore = 0;
                PlayGame(levelBegin);
            }
        });

        tvCustomGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_custom_game);
                dialog.setCanceledOnTouchOutside(false);

                final EditText edtWidthCG = (EditText) dialog.findViewById(R.id.edt_width_cg);
                final EditText edtHeightCG = (EditText) dialog.findViewById(R.id.edt_height_cg);
                final EditText edtSumMoleCG = (EditText) dialog.findViewById(R.id.edt_smole_cg);
                final EditText edtIntervalCG = (EditText) dialog.findViewById(R.id.edt_interval_cg);
                Button btnCreateGame = (Button) dialog.findViewById(R.id.btn_create_cg);

                btnCreateGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String wcg = edtWidthCG.getText().toString().trim();
                        String hcg = edtHeightCG.getText().toString().trim();
                        String scg = edtSumMoleCG.getText().toString().trim();
                        String icg = edtIntervalCG.getText().toString().trim();
                        if(wcg.equals("")== false && hcg.equals("")==false && scg.equals("")==false && icg.equals("")==false) {

                            dialog.dismiss();
                            setContentView(R.layout.main_activity);

                            mLife = 3;
                            mScore = 0;

                            tableLayout = (TableLayout) findViewById(R.id.table_map);
                            tvLife = (TextView) findViewById(R.id.tv_life);
                            tvScore = (TextView) findViewById(R.id.tv_score);

                            Display display = getWindowManager().getDefaultDisplay();
                            displayWidth = display.getWidth(); // ((display.getWidth()*20)/100)
                            displayHeight = display.getHeight();// ((display.getHeight()*30)/100)
                            tableLayout.removeAllViews();

                            lvNow = new LevelGame();
                            lvNow.setmScoreNextLv(1000000000);
                            lvNow.setmAddScore(10);
                            int w = Integer.parseInt(wcg);
                            int h = Integer.parseInt(hcg);
                            int s = Integer.parseInt(scg);
                            int i = Integer.parseInt(icg);

                            CreateGame(w, h, s, i);//w,h,s,i
                        }
                        else
                            Toast.makeText(MainActivity.this, "Vui lòng nhập thêm thông tin !!!", Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();
            }
        });

        tvHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHighScore = sharedPreferences.getInt("high_score",0);

                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_high_score);
                dialog.setCanceledOnTouchOutside(false);


                TextView tvShowHS = (TextView) dialog.findViewById(R.id.tv_show_highscore);
                Button btnOk = (Button) dialog.findViewById(R.id.btn_show_ok);

                tvShowHS.setText(""+mHighScore);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void PlayGame(int lvBegin){
        tableLayout = (TableLayout) findViewById(R.id.table_map);
        tvLife = (TextView) findViewById(R.id.tv_life);
        tvScore = (TextView) findViewById(R.id.tv_score);

        Display display = getWindowManager().getDefaultDisplay();
        displayWidth = display.getWidth(); // ((display.getWidth()*20)/100)
        displayHeight = display.getHeight();// ((display.getHeight()*30)/100)
        tableLayout.removeAllViews();

        lvNow = arrayLevelGame.get(lvBegin);
        CreateGame(lvNow.getmWidthOfGame(),lvNow.getmHeightOfGame(),lvNow.getmNumerOfHoles(),lvNow.getmIntervalGame());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timerMain != null)
            timerMain.cancel();
    }
}
