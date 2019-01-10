package com.example.anilreddy.youtubeplayer.downloaderapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anilreddy.youtubeplayer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class DownloadActivity extends AppCompatActivity {

    private final String TAG = DownloadActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }

    public class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String rssFeed = downloadXml(strings[0]);
            if (rssFeed == null)
                Log.e(TAG, "Do in Background: Error Downloading data");
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        private String downloadXml(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int response = urlConnection.getResponseCode();
                Log.d(TAG, "Download XML: Downloaded xml response is " + response);
//                InputStream inputStream = urlConnection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                int charRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charRead = reader.read(inputBuffer);
                    if (charRead < 0) {
                        break;
                    }
                    if (charRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charRead));
                    }
                }
                reader.close();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXml: invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXml: Io Exception Reading Data " + e.getMessage());
            }
            return null;
        }
    }

}
