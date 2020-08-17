package com.example.chatappv2.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.chatappv2.Models.User;
import com.example.chatappv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {

    private TextView txtSize;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    SwitchCompat switchNotify;
    int numsize=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        txtSize = findViewById(R.id.txt_textSize);
        switchNotify = findViewById(R.id.switch_Notification);
        
        switchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setNotify(true);
                }else{
                    setNotify(false);
                }
            }
        });
        getCurrentSetting();
    }

    private void setNotify(boolean notifySetting) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("notify",notifySetting);

        reference.updateChildren(hashMap);
    }

    public void changeTextSize(View view) {
        registerForContextMenu(view);
        openContextMenu(view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.textsize, menu);

        switch(numsize){
            case 18 :
                menu.findItem(R.id.check18).setChecked(true);
                break;
            case 24:
                menu.findItem(R.id.check24).setChecked(true);
                break;
            case 30:
                menu.findItem(R.id.check30).setChecked(true);
                break;
            case 36 :
                menu.findItem(R.id.check36).setChecked(true);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check18:
                setTextSize(18);
                return true;
            case R.id.check24:
                setTextSize(24);
                return true;
            case R.id.check30:
                setTextSize(30);
                return true;
            case R.id.check36:
                setTextSize(36);
                return true;
        }
        return super.onContextItemSelected(item);
    }
    private void setTextSize(int size) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("txtSize",size);

        reference.updateChildren(hashMap);
    }

    private int getCurrentSetting(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setDefault(user.isNotify());
                numsize = user.getTxtSize();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return numsize;
    }

    private void setDefault(boolean notify){
        switchNotify.setChecked(notify);
        findViewById(R.id.check30);
    }
}
