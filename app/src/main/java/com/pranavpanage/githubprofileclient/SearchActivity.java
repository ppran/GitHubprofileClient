package com.pranavpanage.githubprofileclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void getString(View view){
        TextView username = findViewById(R.id.usernameText);
        try {
            new AsyncSendRequest(new AsyncSendRequest.AsyncResponse() {
                @Override
                public void afterFinish(UserProfile userProfile) {
                    if(userProfile.getType().equals("User"))
                    dataReceived(userProfile);
                    else
                        orgDataReceive(userProfile);
                }
            }).execute(username.getText().toString().toLowerCase());
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }


    }


    private void orgDataReceive(UserProfile orgProfile) {
        if(orgProfile.getUsername()== null){
            Toast.makeText(this, "Organisation Not Found", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this,DisplayOrgProfile.class);
            intent.putExtra("com.pranavpanage.githubprofileclient org", orgProfile);
            startActivity(intent);
        }
    }

    private void dataReceived(UserProfile userProfile)  {
        if (userProfile.getUsername() == null) {
            Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, DisplayUserProfile.class);
            intent.putExtra("com.pranavpanage.githubprofileclient",userProfile);
            startActivity(intent);
        }
    }
}
