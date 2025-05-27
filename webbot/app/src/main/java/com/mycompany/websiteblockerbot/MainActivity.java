package com.mycompany.websiteblockerbot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends Activity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName deviceAdminComponent;
    private Handler handler = new Handler();
    private final String CORRECT_PASSWORD = "mypass@0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceAdminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        

        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        layout.setPadding(40, 40, 40, 40);
        
        // Add a clickable round logo at the top-left
        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.logo);
        int size = 120;
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(size, size);
        logoParams.gravity = Gravity.START;
        logoParams.setMargins(0, 0, 0, 40);
        logo.setLayoutParams(logoParams);

        // Make it round (for API 21+)
        if (Build.VERSION.SDK_INT >= 21) {
            logo.setClipToOutline(true);
            logo.setBackgroundResource(R.drawable.round_background); // round shape background
        }

        // Handle click
        logo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showAboutDialog();
                }
            });

        layout.addView(logo);
       
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF1E1E1E);
        scrollView.setBackgroundDrawable(bg);
        scrollView.addView(layout);

        int buttonColor = 0xFF3F51B5;
        int buttonTextColor = Color.WHITE;
        int buttonMargin = 30;
        int buttonPadding = 35;
        float cornerRadius = 30f;

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );

        Button[] buttons = new Button[6];
        String[] labels = {
            "🔒 Enable Device Admin",
            "♿ Enable Accessibility",
            "🔋 Disable Battery Optimization",
            "🚀 Enable Autostart (MIUI)",
            "🛡️ Start Blocker Service",
            "💀 Kill App (Reset)"
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(this);
            buttons[i].setLayoutParams(params);
            buttons[i].setText(labels[i]);
            buttons[i].setTextColor(buttonTextColor);
            buttons[i].setPadding(buttonPadding, buttonPadding, buttonPadding, buttonPadding);

            GradientDrawable buttonBg = new GradientDrawable();
            buttonBg.setColor(buttonColor);
            buttonBg.setCornerRadius(cornerRadius);
            buttons[i].setBackgroundDrawable(buttonBg);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            
            if (i == 0) {
                lp.setMargins(0, 200, 0, 0); // More space at top
            } else {
                lp.setMargins(0, buttonMargin, 0, 0); // Normal spacing
            }
            layout.addView(buttons[i], lp);
        }

        buttons[0].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!devicePolicyManager.isAdminActive(deviceAdminComponent)) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminComponent);
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Enable Device Admin to prevent uninstalling this app.");
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Already Device Admin", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        buttons[1].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    } catch (Exception e) {
                        showError("Failed to open accessibility settings");
                    }
                }
            });

        buttons[2].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        startActivity(intent);
                    } catch (Exception e) {
                        showError("Cannot open battery optimization settings");
                    }
                }
            });

        buttons[3].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                        startActivity(intent);
                        
                    } catch (Exception e) {
                        showError("This setting is only available on MIUI devices.");
                    }
                }
            });
            
        //start blocker service button
        buttons[4].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!devicePolicyManager.isAdminActive(deviceAdminComponent)) {
                        Toast.makeText(MainActivity.this, "Please enable Device Admin first", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SharedPreferences prefs = getSharedPreferences("blocker_prefs", MODE_PRIVATE);
                    prefs.edit()
                        .putBoolean("block_admin", true)
                        .putBoolean("block_access", true)
                        .apply();

                    try {
                        Intent serviceIntent = new Intent(MainActivity.this, BlockerService.class);
                        startService(serviceIntent);
                        Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        showError("Failed to start service");
                    }
                }
            });

        buttons[5].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showPasswordDialog();
                }
            });

        setContentView(scrollView);
    }
    
    private void showAboutDialog() {
        // Create a vertical layout for the dialog content
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);
        layout.setBackgroundColor(Color.parseColor("#424242"));// Full black background

        // Title
        TextView title = new TextView(this);
        title.setText("About This App");
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        title.setPadding(0, 0, 0, 30);
        layout.addView(title);

        // Message
        TextView message = new TextView(this);
        message.setText("📱 Website Blocker App\n🔒 Prevent access to blocked websites\n\nDeveloped by Raja Roy.");
        message.setTextSize(16);
        message.setTextColor(Color.WHITE);
        layout.addView(message);

        // Build dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setView(layout)
            .setPositiveButton("OK", null)
            .create();

        dialog.show();

        // Style the button
        Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        
        if (okButton != null) {
            //okButton.setBackgroundColor(Color.BLACK);
            okButton.setTextColor(Color.BLACK);
        }
    }
    
    
    

    private void showError(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        TextView msg = new TextView(this);
        msg.setText(message);
        msg.setTextColor(Color.WHITE);
        msg.setPadding(50, 40, 50, 40);
        msg.setTextSize(16);
        msg.setBackgroundColor(Color.parseColor("#424242"));
        dialog.setView(msg);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (DialogInterface.OnClickListener) null);
        dialog.show();

        Button ok = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (ok != null) {
            ok.setTextColor(Color.BLACK);
        }
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        builder.setTitle("🔐 Enter Password");

        // Create layout wrapper
        LinearLayout wrapper = new LinearLayout(this);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setPadding(50, 40, 50, 10);
        wrapper.setBackgroundColor(Color.parseColor("#424242")); // black background

        // Create EditText
        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint("Password");
        input.setHintTextColor(0xFF888888);
        input.setTextColor(Color.WHITE);
        input.setPadding(30, 20, 30, 20);
        input.setBackgroundColor(0xFF222222);

        wrapper.addView(input);
        builder.setView(wrapper);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String enteredPassword = input.getText().toString();
                    if (enteredPassword.equals(CORRECT_PASSWORD)) {
                        resetApp();
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();

        // Modify dialog after it shows
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface d) {
                    // Title color and background
                    TextView titleView = dialog.findViewById(
                        getResources().getIdentifier("alertTitle", "id", "android")
                    );
                    if (titleView != null) {
                        titleView.setTextColor(Color.WHITE);
                        titleView.setBackgroundColor(Color.parseColor("#424242"));
                    }

                    // Dialog background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#424242")));

                    // Dialog width (90% of screen width)
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
                    dialog.getWindow().setAttributes(lp);

                    // Buttons
                    Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    Button negative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    positive.setTextColor(Color.WHITE);
                    negative.setTextColor(Color.WHITE);
                 
                }
            });

        dialog.show();
    }
    

    private void resetApp() {

        if (devicePolicyManager.isAdminActive(deviceAdminComponent)) {
            devicePolicyManager.removeActiveAdmin(deviceAdminComponent);
        }

        stopService(new Intent(MainActivity.this, BlockerService.class));

        SharedPreferences prefs = getSharedPreferences("blocker_prefs", MODE_PRIVATE);
        prefs.edit()
            .putBoolean("block_admin", false)
            .putBoolean("block_access", false)
            .apply();

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed(new Runnable() {
                public void run() {
                    String current = getTopPackageName();
                    if (current != null && current.contains("settings")) {
                        // Optional: Detect if user opens Settings
                    }
                }
            }, 500);
    }

    private String getTopPackageName() {
        try {
            android.app.ActivityManager am = (android.app.ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            return am.getRunningTasks(1).get(0).topActivity.getPackageName();
        } catch (Exception e) {
            return null;
        }
    }
}

