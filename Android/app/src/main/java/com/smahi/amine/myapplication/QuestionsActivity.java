package com.smahi.amine.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QuestionsActivity extends AppCompatActivity {
    int i = 1;
    TextView textView2;
    TextSwitcher mSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        textView2 = findViewById(R.id.textView2);
        textView2.setText(i + "/7");
        ChangeTextAnimator();
    }

    public void quiz(View view) {
        switchQuestions();
    }

    void ChangeTextAnimator() {

        mSwitcher = findViewById(R.id.textSwitcher2);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(QuestionsActivity.this);
                myText.setTextSize(26);
                myText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);
        switchQuestions();

    }

    void switchQuestions() {
        switch (i) {
            case 1:
                mSwitcher.setText("Quelles sont vos compétences en Design");
                textView2.setText(i + "/7");
                i++;
                break;
            case 2:
                mSwitcher.setText("Quelles sont vos compétences en Mathématique?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 3:
                mSwitcher.setText("Quelles sont vos compétences en Algorithmique?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 4:
                mSwitcher.setText("Quelles sont vos compétences en Programmation?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 5:
                mSwitcher.setText("Quelles sont vos compétences dans l'utilisation du terminal (ligne de commande)?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 6:
                mSwitcher.setText("Quelles sont vos compétences en Réseaux?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 7:
                mSwitcher.setText("Quelles sont vos compétences en Hardware?");
                textView2.setText(i + "/7");
                i++;
                break;
            case 8:
                sendJson();
                break;
        }
    }
    protected void sendJson() {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpPost post = new HttpPost("http://2f04054d.ngrok.io/api/Master");
                    json.put("label", (float) 0);
                    json.put("designScore", (float)0);
                    json.put("mathScore", (float)0);
                    json.put("algoScore",(float) 0);
                    json.put("codingScore", (float)0);
                    json.put("cliScore",(float) 0);
                    json.put("networkingScore", (float)0);
                    json.put("hardwareScore", (float)0);
                    json.put("type", (float)0);

                    StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        try {
                            InputStream in = response.getEntity().getContent(); //Get the data in the entity
                            String a = getStringFromInputStream(in);
                            Gson gson = new Gson(); // Or use new GsonBuilder().create();
                            MasterResult target2 = gson.fromJson(a, MasterResult.class);
                            Intent i = new Intent(QuestionsActivity.this, ResultActivity.class);
                            i.putExtra("result", target2.result);
                            i.putExtra("score1", target2.score1);
                            i.putExtra("score2", target2.score2);
                            i.putExtra("score3", target2.score3);
                            i.putExtra("score4", target2.score4);
                            startActivity(i);
                        }
                        catch (Exception e){ }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}