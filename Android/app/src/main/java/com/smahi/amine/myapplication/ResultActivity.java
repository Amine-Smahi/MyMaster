package com.smahi.amine.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    TextView pourcentageResult, masterResult, second, third, forth, secondPors, thirdPors, forthPors;
    ImageButton share;
    Button tryagain;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        pourcentageResult = findViewById(R.id.pourcentageResult);
        masterResult = findViewById(R.id.masterResult);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        forth = findViewById(R.id.forth);
        tryagain = findViewById(R.id.tryagain);
        secondPors = findViewById(R.id.secondPors);
        thirdPors = findViewById(R.id.thirdPors);
        forthPors = findViewById(R.id.forthPors);
        share = findViewById(R.id.share);
        logo = findViewById(R.id.logo);

        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, QuestionsActivity.class);
                startActivity(i);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logo.setVisibility(View.VISIBLE);
                share.setVisibility(View.INVISIBLE);
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                shareImage(store(getScreenShot(rootView), "MyMaster.png"));
                logo.setVisibility(View.INVISIBLE);
                share.setVisibility(View.VISIBLE);
            }
        });


        String result = getIntent().getStringExtra("result");
        masterResult.setText(result);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        Masters[] list = new Masters[4];
        String masterArray[] = {"ADSI", "Embarquer", "SITW", "RES"};
        TextView textViewArray[] = {pourcentageResult, second, third, forth};
        TextView textViewArray2[] = {secondPors, thirdPors, forthPors};

        float score1 = getIntent().getFloatExtra("score1", 0.0f);
        list[0] = new Masters((int) (Float.valueOf(df.format(score1)) * 100f), "Aide a la decision et systems intelligents");


        float score2 = getIntent().getFloatExtra("score2", 0.0f);
        list[1] = new Masters((int) (Float.valueOf(df.format(score2)) * 100f), "Information industrielle, parallele et embarquée");


        float score3 = getIntent().getFloatExtra("score3", 0.0f);
        list[2] = new Masters((int) (Float.valueOf(df.format(score3)) * 100f), "System d'information et technologie web");

        float score4 = getIntent().getFloatExtra("score4", 0.0f);
        list[3] = new Masters((int) (Float.valueOf(df.format(score4)) * 100f), "Réseaux et systems distribueés");

        Arrays.sort(list);
        int i = 0;
        for (Masters temp : list) {
            if (i == 0) {
                textViewArray[i].setText(list[0].score + "%");
                Toast.makeText(getApplicationContext(), "oo" + i, Toast.LENGTH_SHORT);
                i++;
            } else {
                textViewArray[i].setText(list[i].name);
                textViewArray2[i - 1].setText(list[i].score + "%");
                System.out.println("\n\n" + list[i].name + "\n\n");
                System.out.println("\n\n" + list[i].score + "\n\n");
                i++;
            }
        }
    }

    public  Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static File store(Bitmap bm, String fileName) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}

class Masters implements Comparable<Masters> {
    int score;
    String name;

    Masters(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(Masters masters) {
        int scores = ((Masters) masters).score;
        return scores - this.score;
    }
}