package ma.ensa.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ma.ensa.scanner.MainActivity;
import ma.ensa.scanner.R;

public class SplashScreen extends AppCompatActivity {
    Animation topAnimation, bottomAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_up);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        final ImageView image1 = findViewById(R.id.image1);
        final ImageView image2 = findViewById(R.id.image2);
        final ImageView image3 = findViewById(R.id.image3);
        final ImageView image4 = findViewById(R.id.image4);
        final ImageView image5 = findViewById(R.id.image5);
        final ImageView image6 = findViewById(R.id.image6);






        image1.setAnimation(topAnimation);
        image2.setAnimation(topAnimation);
        image3.setAnimation(topAnimation);

        image4.setAnimation(bottomAnimation);
        image5.setAnimation(bottomAnimation);
        image6.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 8000);
    }
}