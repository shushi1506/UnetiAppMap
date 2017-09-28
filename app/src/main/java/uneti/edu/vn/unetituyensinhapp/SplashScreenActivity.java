package uneti.edu.vn.unetituyensinhapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private static final long SPLASH_TIME=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent t=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(t);
                finish();
            }
        };
        timer.schedule(task,SPLASH_TIME);
    }
}
