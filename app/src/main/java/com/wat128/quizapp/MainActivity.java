package com.wat128.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answarBtn1, answarBtn2, answarBtn3, answarBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"北海道", "札幌市", "長崎市", "福島市", "前橋市"},
            {"青森県", "青森市", "広島市", "甲府市", "岡山市"},
            {"岩手県", "盛岡市","大分市", "秋田市", "福岡市"},
            {"宮城県", "仙台市", "水戸市", "岐阜市", "福井市"},
            {"秋田県", "秋田市","横浜市", "鳥取市", "仙台市"},
            {"山形県", "山形市","青森市", "山口市", "奈良市"},
            {"福島県", "福島市", "盛岡市", "新宿区", "京都市"},
            {"茨城県", "水戸市", "金沢市", "名古屋市", "奈良市"},
            {"栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市"},
            {"群馬県", "前橋市", "福岡市", "松江市", "福井市"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLavel);
        answarBtn1 = findViewById(R.id.answerBtn1);
        answarBtn2 = findViewById(R.id.answerBtn2);
        answarBtn3 = findViewById(R.id.answerBtn3);
        answarBtn4 = findViewById(R.id.answerBtn4);

        for(int i = 0; i < quizData.length; i++) {
            ArrayList<String> tmpArray = new ArrayList<>();

            tmpArray.add(quizData[i][0]);   // 都道府県
            tmpArray.add(quizData[i][1]);   // 正解
            tmpArray.add(quizData[i][2]);   // 選択肢１
            tmpArray.add(quizData[i][3]);   // 選択肢２
            tmpArray.add(quizData[i][4]);   // 選択肢３

            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }

    public void showNextQuiz() {
        countLabel.setText("Q" + quizCount);

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);

        questionLabel.setText(quiz.get(0));

        rightAnswer = quiz.get(1);

        quiz.remove(0);

        Collections.shuffle(quiz);

        answarBtn1.setText(quiz.get(0));
        answarBtn2.setText(quiz.get(1));
        answarBtn3.setText(quiz.get(2));
        answarBtn4.setText(quiz.get(3));

        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if(btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            ++rightAnswerCount;
        } else {
            alertTitle = "不正解...";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(quizCount == QUIZ_COUNT) {
                    // 結果画面へ移動
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    ++quizCount;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
