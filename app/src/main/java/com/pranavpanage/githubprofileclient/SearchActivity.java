package com.pranavpanage.githubprofileclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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
                public void afterfinish(UserProfile userProfile) {
                    dataReceived(userProfile);
                }
            }).execute(username.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void dataReceived(UserProfile userProfile)  {
        if (userProfile.getUsername() == null) {
            Toast.makeText(this, "User Not Found", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, DisplayUserProfile.class);
            //intent.putExtra("com.pranavpanage.githubprofileclient1", userProfile.getUsername());
            //intent.putExtra("com.pranavpanage.githubprofileclient2", userProfile.getAvatar_url());
            intent.putExtra("com.pranavpanage.githubprofileclient",userProfile);
            startActivity(intent);
        }
    }
}
