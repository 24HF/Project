package com.example.sqlitetest.exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sqlitetest.R;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    private int count;
    private int current;
    private boolean wrongMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        DBService dbService = new DBService();
        final List<Question> list = dbService.getQuestion();

        count = list.size();
        current = 0;
        wrongMode = false;


        final TextView tv_question = findViewById(R.id.question);
        final RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = findViewById(R.id.answerA);
        radioButtons[1] = findViewById(R.id.answerB);
        radioButtons[2] = findViewById(R.id.answerC);
        radioButtons[3] = findViewById(R.id.answerD);
        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);
        final TextView tv_explanation = findViewById(R.id.explanation);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);

        //控件赋值
        Question q = list.get(0);
        tv_question.setText(q.question);
        tv_explanation.setText(q.explanation);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < count -1){
                    current++;

                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    tv_explanation.setText(q.explanation);

                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);

                    //如果之前已经做过选择，则记录选择
                    radioGroup.clearCheck();
                    if (q.selectAnswer != -1){
                        radioButtons[q.selectAnswer].setChecked(true);
                    }
                }
                else if(current == count -1 && wrongMode == true){
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("已经到达最后一题，是否退出?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
                else {
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("已经到达最后一题，是否提交?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final List<Integer> wrongList = checkAnswer(list);
                                    if (wrongList.size() == 0){
                                        new android.app.AlertDialog.Builder(ExamActivity.this)
                                                .setTitle("提示")
                                                .setMessage("恭喜你全部回答正确！")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ExamActivity.this.finish();
                                                    }
                                                }).show();
                                    }else{
                                        new android.app.AlertDialog.Builder(ExamActivity.this)
                                                .setTitle("提示")
                                                .setMessage("你答对了"+(list.size()-wrongList.size()) + "道题目；答错了"+wrongList.size()+"道题目。是否查看错题？")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //判断进入错题模式
                                                        wrongMode = true;
                                                        List<Question> newList = new ArrayList<Question>();
                                                        //将错题复制到newList中
                                                        for(int i = 0; i < wrongList.size();i++){
                                                            newList.add(list.get(wrongList.get(i)));
                                                        }
                                                        //将原来的list清空
                                                        list.clear();
                                                        //将错题添加到原来的list中
                                                        for (int i = 0; i < newList.size(); i++){
                                                            list.add(newList.get(i));
                                                        }
                                                        current = 0;
                                                        count = list.size();
                                                        //更新显示内容
                                                        Question q = list.get(current);
                                                        tv_question.setText(q.question);
                                                        radioButtons[0].setText(q.answerA);
                                                        radioButtons[1].setText(q.answerB);
                                                        radioButtons[2].setText(q.answerC);
                                                        radioButtons[3].setText(q.answerD);
                                                        tv_explanation.setText(q.explanation);
                                                        //显示解析
                                                        tv_explanation.setVisibility(View.VISIBLE);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ExamActivity.this.finish();
                                                    }
                                                }).show();
                                    }
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                    //
                    //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
//                    final List<Integer> wrongList = checkAnswer(list);
//                    if (wrongList.size() == 0){
//                        new android.app.AlertDialog.Builder(ExamActivity.this)
//                                .setTitle("提示")
//                                .setMessage("恭喜你全部回答正确！")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ExamActivity.this.finish();
//                                    }
//                                }).show();
//                    }else{
//                        new android.app.AlertDialog.Builder(ExamActivity.this)
//                                .setTitle("提示")
//                                .setMessage("你答对了"+(list.size()-wrongList.size()) + "道题目；答错了"+wrongList.size()+"道题目。是否查看错题？")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //判断进入错题模式
//                                        wrongMode = true;
//                                        List<Question> newList = new ArrayList<Question>();
//                                        //将错题复制到newList中
//                                        for(int i = 0; i < wrongList.size();i++){
//                                            newList.add(list.get(wrongList.get(i)));
//                                        }
//                                        //将原来的list清空
//                                        list.clear();
//                                        //将错题添加到原来的list中
//                                        for (int i = 0; i < newList.size(); i++){
//                                            list.add(newList.get(i));
//                                        }
//                                        current = 0;
//                                        count = list.size();
//                                        //更新显示内容
//                                        Question q = list.get(current);
//                                        tv_question.setText(q.question);
//                                        radioButtons[0].setText(q.answerA);
//                                        radioButtons[1].setText(q.answerB);
//                                        radioButtons[2].setText(q.answerC);
//                                        radioButtons[3].setText(q.answerD);
//                                        tv_explanation.setText(q.explanation);
//                                        //显示解析
//                                        tv_explanation.setVisibility(View.VISIBLE);
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ExamActivity.this.finish();
//                                    }
//                                }).show();
//                    }
                }
            }

        });


        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
                if (current > 0){
                    current--;
                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    tv_explanation.setText(q.explanation);


                    //若之前已经选择过，则应记录选择
                    radioGroup.clearCheck();
                    if (q.selectAnswer != -1){
                        radioButtons[q.selectAnswer].setChecked(true);
                    }
                }
            }
        });
//选择选项时更新选择
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++){
                    if (radioButtons[i].isChecked() == true) {
                        list.get(current).selectAnswer = i;
                        break;
                    }
                }
            }
        });



    }

    public List<Integer> checkAnswer(List<Question> list){
        List<Integer> wrongList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).answerE != list.get(i).selectAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;

    }
}