package com.example.chatappv2.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.chatappv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText editUserName, editPass, editEmail;
    Button btnRegister;
    RelativeLayout registerPage;

    //firebase value
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerPage = findViewById(R.id.activity_register);
        auth = FirebaseAuth.getInstance();
        initView();
    }

    //<-----------------------------------init the layout------------------------------------------>
    private void initView() {
        editUserName = findViewById(R.id.edit_reg_username);
        editEmail = findViewById(R.id.edit_reg_email);
        editPass = findViewById(R.id.edit_reg_password);
        btnRegister = findViewById(R.id.btn_reg_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUserName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPass.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Snackbar.make(registerPage, "Please fill in all text fieds first",
                            Snackbar.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Snackbar.make(registerPage, "Password must be at least 6 characters",
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    register(username, password, email);
                }
            }
        });
    }

    //<---------------------------------create new account----------------------------------------->
    private void register(final String username, String password, String email) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userId = firebaseUser.getUid();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", userId);
                hashMap.put("username", username);
                hashMap.put("imageURL", "default");
                hashMap.put("status","offline");
                hashMap.put("txtSize",18);
                hashMap.put("notify",false);

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar.make(registerPage, "Your cannot register with this emial or password"
                                    , Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(registerPage, "Error With Register : "
                        + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                Toast.makeText(RegisterActivity.this, "Error With sign in",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
