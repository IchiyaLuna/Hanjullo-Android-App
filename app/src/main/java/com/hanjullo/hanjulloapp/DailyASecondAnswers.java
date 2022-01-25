package com.hanjullo.hanjulloapp;

import java.util.ArrayList;

public class DailyASecondAnswers {
    static public String getAnswerText(int stepASelect, int currentBtn) {
        ArrayList<ArrayList<String>> answers = new ArrayList<>();

        for (int i = 0; i < 5; i++) answers.add(new ArrayList<>());

        answers.get(0).add("영감이 떠올라");
        answers.get(0).add("희망이 넘쳐");
        answers.get(0).add("용감해");
        answers.get(0).add("감사해");
        answers.get(0).add("행복해");
        answers.get(0).add("존중 받는 느낌이야");
        answers.get(0).add("자신감 넘쳐");
        answers.get(0).add("자랑스러워");
        answers.get(0).add("재밌어");
        answers.get(0).add("기뻐");
        answers.get(0).add("에너지 넘쳐");
        answers.get(0).add("사랑받는 느낌이야");

        answers.get(1).add("영감이 떠올라");
        answers.get(1).add("희망이 넘쳐");
        answers.get(1).add("편안해");
        answers.get(1).add("감사해");
        answers.get(1).add("해냈어");
        answers.get(1).add("열정이 넘쳐");
        answers.get(1).add("끝내기 아쉬워");
        answers.get(1).add("긍정적이야");
        answers.get(1).add("흥분돼");
        answers.get(1).add("행복해");
        answers.get(1).add("자신감 있어");
        answers.get(1).add("성취감 있어");

        answers.get(2).add("어색했어");
        answers.get(2).add("침착해");
        answers.get(2).add("걱정이 돼");
        answers.get(2).add("혼란스러워");
        answers.get(2).add("바빠");
        answers.get(2).add("피곤해");
        answers.get(2).add("텅 빈 것 같아");
        answers.get(2).add("스트레스 받아");
        answers.get(2).add("의미를 찾고 싶어");
        answers.get(2).add("나쁘지 않아");
        answers.get(2).add("집중이 안 돼");
        answers.get(2).add("좋게 보려고 해");

        answers.get(3).add("짜증났어");
        answers.get(3).add("지루했어");
        answers.get(3).add("질투났어");
        answers.get(3).add("긴장했어");
        answers.get(3).add("피곤했어");
        answers.get(3).add("외로웠어");
        answers.get(3).add("혼란스러웠어");
        answers.get(3).add("실망스러웠어");
        answers.get(3).add("참지 못 했어");
        answers.get(3).add("슬펐어");
        answers.get(3).add("불안정했어");
        answers.get(3).add("죄책감이 들었어");
        answers.get(3).add("스트레스 받았어");

        answers.get(4).add("화났어");
        answers.get(4).add("불안했어");
        answers.get(4).add("짜증났어");
        answers.get(4).add("좌절했어");
        answers.get(4).add("역겨웠어");
        answers.get(4).add("외로웠어");
        answers.get(4).add("길을 잃었어");
        answers.get(4).add("의욕이 없었어");
        answers.get(4).add("상처 받았어");
        answers.get(4).add("슬펐어");
        answers.get(4).add("두려웠어");
        answers.get(4).add("죄책감이 들었어");

        return answers.get(stepASelect - 1).get(currentBtn - 1);
    }
}
