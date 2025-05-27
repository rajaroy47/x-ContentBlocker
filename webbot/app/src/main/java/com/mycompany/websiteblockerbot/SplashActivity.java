package com.mycompany.websiteblockerbot;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get screen dimensions for responsiveness
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        // Root layout
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setGravity(Gravity.CENTER);
        root.setPadding(40, 40, 40, 40);

        // ImageView for logo
        ImageView imageView = new ImageView(this);
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.myimg);

        if (original != null) {
            Bitmap rounded = getCircularBitmap(original);
            imageView.setImageBitmap(rounded);
        } else {
            TextView error = new TextView(this);
            error.setText("Image not found");
            error.setTextColor(Color.RED);
            root.addView(error);
        }

        int imageSize = screenWidth / 2; // responsive size
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageSize, imageSize);
        imageParams.setMargins(0, screenHeight / 8, 0, 40);
        imageView.setLayoutParams(imageParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAlpha(0f);
        imageView.animate().alpha(1f).setDuration(1000).start();
        root.addView(imageView);

        // App name
        TextView title = new TextView(this);
        title.setText("WebBlocker");
        title.setTextSize(32);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        // Subtext
        TextView powered = new TextView(this);
        powered.setText("Powered by Roy Apps Hub");
        powered.setTextSize(16);
        powered.setTextColor(Color.LTGRAY);
        powered.setGravity(Gravity.CENTER);
        powered.setPadding(0, 10, 0, 10);
        root.addView(powered);

        // Version info with fade-in animation
        TextView version = new TextView(this);
        version.setText("Version 1.1");
        version.setTextSize(14);
        version.setTextColor(Color.GRAY);
        version.setGravity(Gravity.CENTER);
        version.setPadding(0, 20, 0, 0);
        root.addView(version);

        version.setAlpha(0f);
        version.animate().alpha(1f).setDuration(1500).start();

        setContentView(root);

        // Delayed transition to MainActivity
        new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, 2500);
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());

        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, size, size);

        paint.setAntiAlias(true);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);

        return output;
    }
}

