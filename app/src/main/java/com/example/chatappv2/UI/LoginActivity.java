package com.example.chatappv2.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chatappv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText editPass, editEmail;
    Button btnLogin;
    RelativeLayout loginPage;
    TextView txt_forgetPass;
    private ProgressDialog dialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPage = findViewById(R.id.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        initView();
    }

    //<-----------------------------------init the layout------------------------------------------>
    private void initView() {
        editEmail = findViewById(R.id.edit_login_email);
        editPass = findViewById(R.id.edit_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        txt_forgetPass = findViewById(R.id.txt_forgetPass);

        txt_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPassActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPass.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Snackbar.make(loginPage, "Please fill in all text fields first"
                            , Snackbar.LENGTH_SHORT).show();
                } else {
                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setTitle("Processing Login");
                    dialog.setMessage("Please Wait");
                    dialog.show();
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                dialog.dismiss();
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(loginPage, "Authentication failed"
                                        , Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
