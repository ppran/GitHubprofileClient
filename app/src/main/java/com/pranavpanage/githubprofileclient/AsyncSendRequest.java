package com.pranavpanage.githubprofileclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class AsyncSendRequest extends AsyncTask<String,Void,UserProfile> {

    public AsyncResponse delegate = null;

    public interface AsyncResponse{
        void afterFinish(UserProfile userProfile);
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Constructor
    public AsyncSendRequest(AsyncResponse delegate) {
        //this.context = context;
        this.delegate = delegate;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param userProfile The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(UserProfile userProfile) {
        delegate.afterFinish(userProfile);
    }

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
    protected UserProfile doInBackground(String... strings) {
        Log.d("GitHubClient",strings[0].toString());
        HttpsURLConnection httpsURLConnection = null;
        InputStreamReader responseBodyReader ;
        UserProfile userProfile = new UserProfile();
        try {
            // Create URL object
            URL gitHubUser = new URL("https://api.github.com/users/" + strings[0].toString());

            //Create Connection
            httpsURLConnection = (HttpsURLConnection) gitHubUser.openConnection();
            httpsURLConnection.connect();

            //Reading Response
            if (httpsURLConnection.getResponseCode()==200){
                Log.w("GitHubClient","Response Received");
                responseBodyReader = new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(responseBodyReader);

                jsonReader.beginObject(); // Start processing the JSON object


                while (jsonReader.hasNext() ) { // Loop through all keys
                    String key = jsonReader.nextName(); // Fetch the next key

                    switch (key){
                        case "name":
                            String username = jsonReader.nextString();
                            //Log.d("GitHubClient","name : " + username);
                            userProfile.setUsername(username);
                            break;
                        case "avatar_url":
                            String imageUrl = jsonReader.nextString();
                            //InputStream in = new URL(imageUrl).openStream();
                           // Bitmap result = BitmapFactory.decodeStream(in);
                            userProfile.setAvatar_url(imageUrl);
                            break;
                        case "company":
                            //String company = jsonReader.nextString();
                            if (jsonReader.peek() == JsonToken.NULL){
                                jsonReader.nextNull();
                                userProfile.setCompany("Not Available");
                                break;
                            }
                            userProfile.setCompany(jsonReader.nextString());
                            break;
                        case "location":
                            if (jsonReader.peek() == JsonToken.NULL){
                                jsonReader.nextNull();
                                userProfile.setLocation("Not Available");
                                break;
                            }
                            String location = jsonReader.nextString();
                            userProfile.setLocation(location);
                            //jsonReader.skipValue();
                            break;
                        case "email":
                            if (jsonReader.peek() == JsonToken.NULL){
                                jsonReader.nextNull();
                                userProfile.setEmail("Not Available");
                                break;
                            }
                            String email = jsonReader.nextString();
                            userProfile.setEmail(email);
                            //jsonReader.skipValue();
                            break;
                        case "bio":
                            if (jsonReader.peek() == JsonToken.NULL){
                                jsonReader.nextNull();
                                userProfile.setBio("Not Available");
                                break;
                            }
                            String bio = jsonReader.nextString();
                            userProfile.setBio(bio);
                            //jsonReader.skipValue();
                            break;
                        case "created_at":
                            String created_at = jsonReader.nextString();
                            userProfile.setCreated_on(created_at);
                            //jsonReader.skipValue();
                            break;
                        case "public_repos":
                            int repos = jsonReader.nextInt();
                            userProfile.setPublic_repos(String.valueOf(repos));
                            break;
                        case "followers":
                            int followersCount = jsonReader.nextInt();
                            userProfile.setFollowers(String.valueOf(followersCount));
                            break;
                        case "blog":
                            if (jsonReader.peek()==JsonToken.NULL){
                                jsonReader.nextNull();
                                userProfile.setBlog("Not Available");
                                break;
                            }
                            userProfile.setBlog(jsonReader.nextString());
                            break;
                        case"type":
                            userProfile.setType(jsonReader.nextString());
                            break;
                        default:
                                jsonReader.skipValue(); // Skip values of other keys
                            break;
                    }
                }
                jsonReader.close();
            }
            else {
                Log.e("GitHubClient","Unable to Connect");
            }
        } catch (MalformedURLException e) {
            Log.e("GitHubClient",e.toString());
        } catch (IOException e) {
            Log.e("GitHubClient",e.toString());
        } finally {
            httpsURLConnection.disconnect();
        }
        return userProfile;
    }
}
