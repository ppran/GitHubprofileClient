package com.pranavpanage.githubprofileclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DisplayOrgProfile extends Activity {

    private class OrgImageDownloader extends AsyncTask<String, Void , Bitmap> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Bitmap doInBackground(String... strings) {

            //Request image using url
            InputStream in = null;
            try {
                in = new URL(strings[0]).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param bitmap The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //Set Image
            ImageView imageView = findViewById(R.id.imageProfileOrg);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            //dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
            dr.setCircular(true);
            imageView.setImageDrawable(dr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_org_profile);

        //Get parcelable Object from Search Activity
        UserProfile userProfile = getIntent().getParcelableExtra("com.pranavpanage.githubprofileclient org");

        TextView textView = findViewById(R.id.orgName);
        textView.setText(userProfile.getUsername());

        DisplayOrgProfile.OrgImageDownloader imageDownloader = new DisplayOrgProfile.OrgImageDownloader();
        imageDownloader.execute(userProfile.getAvatar_url());

        TextView textViewLocation = findViewById(R.id.locationOrg);
        textViewLocation.setText(userProfile.getLocation());

        TextView textViewEmail = findViewById(R.id.emailIDOrg);
        textViewEmail.setText(userProfile.getEmail());



        TextView textViewBlog = findViewById(R.id.blogOrg);
        textViewBlog.setText(userProfile.getBlog());

        TextView textViewRepository = findViewById(R.id.numberRepositoryOrg);
        textViewRepository.setText(userProfile.getPublic_repos());

        TextView textViewDate = findViewById(R.id.createdOnOrg);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        df.setTimeZone(tz);
        Date date = null;
        try {
            date=df.parse(userProfile.getCreated_on());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textViewDate.setText(date.toString());



    }
}
