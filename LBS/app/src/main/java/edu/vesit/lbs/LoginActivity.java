package edu.vesit.lbs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    EditText accountNoField, passwordField;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        accountNoField = (EditText) findViewById(R.id.accountNoField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredAccountNo = accountNoField.getText().toString();
                String enteredPassword = passwordField.getText().toString();
                if (enteredAccountNo.trim().equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter a valid account no.", Toast.LENGTH_SHORT).show();
                else if (enteredPassword.trim().equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
                else
                    new VerifyLoginTask(LoginActivity.this).execute(enteredAccountNo, enteredPassword);
            }
        });
        SharedPreferences storedCredentials = LoginActivity.this.getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
        if (storedCredentials.contains("loggedIn")) {
            if (storedCredentials.getString("loggedIn", "no").equals("yes")) {
                accountNoField.setText(storedCredentials.getString("accountNo", ""));
                passwordField.setText(storedCredentials.getString("password", ""));
                loginButton.performClick();
            }
        } else {
            SharedPreferences.Editor editor = storedCredentials.edit();
            editor.putString("loggedIn", "no");
            editor.commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
