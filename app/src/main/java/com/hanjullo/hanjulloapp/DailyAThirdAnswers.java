package com.hanjullo.hanjulloapp;

import java.util.ArrayList;

public class DailyAThirdAnswers {
    static public String getAnswerText(int currentBtn) {
        ArrayList<String> answers = new ArrayList<>();
        answers.add("직업");
        answers.add("학업");
        answers.add("친구");
        answers.add("가족");
        answers.add("취미");
        answers.add("운동");
        answers.add("음식");
        answers.add("수면");
        answers.add("쇼핑");
        answers.add("돈");
        answers.add("휴식");
        answers.add("여행");
        answers.add("인간관계");
        answers.add("SNS");
        return answers.get(currentBtn - 1);
    }
}
