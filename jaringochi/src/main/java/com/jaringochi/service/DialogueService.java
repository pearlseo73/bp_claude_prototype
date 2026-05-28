package com.jaringochi.service;

import com.jaringochi.domain.DialogueLog;
import com.jaringochi.domain.Gulbi;
import com.jaringochi.repository.DialogueRepository;
import com.jaringochi.repository.GulbiRepository;

import java.util.List;
import java.util.Random;

/**
 * 굴비와의 대화 서비스.
 * 사용자의 다짐 메시지에 대해 굴비의 성격(굴비 종류) 기반 응답을 생성한다.
 * (실제 AI 호출 대신 템플릿 응답. 향후 외부 API 연동 확장 가능 구조)
 */
public class DialogueService {
    private final DialogueRepository dialogueRepository = new DialogueRepository();
    private final GulbiRepository gulbiRepository = new GulbiRepository();
    private final Random random = new Random();

    // 성격(굴비 종류)별 응답 템플릿
    private static final String[] REPLY_TYPE_1 = { // 다정/응원형
            "%s 정말 멋진 다짐이야! 내가 항상 응원할게~ 🐟✨",
            "%s 너라면 충분히 해낼 수 있어! 우리 함께 아껴보자 💚",
            "%s 그 마음 너무 예뻐! 오늘도 조금씩, 천천히 가보자 🌿",
            "%s 잘하고 있어! 이번 달도 내가 곁에서 지켜볼게 😊"
    };
    private static final String[] REPLY_TYPE_2 = { // 까칠/츤데레형
            "%s 흥, 그 정도는 당연히 해야지. ...그래도 응원은 해줄게.",
            "%s 말로만 하지 말고 행동으로 보여줘! ...기대할게, 조금.",
            "%s 또 흐지부지하면 안 돼. 이번엔 진짜 지켜본다?",
            "%s 뭐... 나쁘지 않은 다짐이네. 끝까지 가보든가."
    };
    private static final String[] REPLY_TYPE_3 = { // 넉넉/푸근형
            "%s 허허, 너무 무리하진 말고. 꾸준함이 제일이란다 🍵",
            "%s 좋구나 좋아. 천천히 가도 괜찮으니 마음 편히 하렴 😌",
            "%s 그 마음이면 충분해. 오늘도 수고했다, 우리 함께 가자 🐟",
            "%s 조급해하지 말렴. 한 걸음씩이면 다 잘 될 거야 🌷"
    };

    public List<DialogueLog> getDialogues(long userId) {
        return dialogueRepository.findByUserId(userId);
    }

    public List<DialogueLog> getDialoguesByReport(long monthlyReportId) {
        return dialogueRepository.findByMonthlyReportId(monthlyReportId);
    }

    /** 사용자 다짐 저장 + 굴비 응답 생성 */
    public DialogueLog createDialogue(long userId, Long monthlyReportId, String userMessage) {
        Gulbi gulbi = gulbiRepository.findByUserId(userId);
        String reply = generateReply(gulbi, userMessage);

        DialogueLog log = new DialogueLog();
        log.setUserId(userId);
        log.setMonthlyReportId(monthlyReportId);
        log.setUserMessage(userMessage);
        log.setGulbiReply(reply);
        dialogueRepository.save(log);
        return log;
    }

    /** 굴비 종류 기반 응답 생성 */
    public String generateReply(Gulbi gulbi, String userMessage) {
        String snippet = "";
        if (userMessage != null) {
            String trimmed = userMessage.trim();
            if (trimmed.length() > 0) {
                String head = trimmed.length() > 20 ? trimmed.substring(0, 20) + "…" : trimmed;
                snippet = "\"" + head + "\"라니,";
            }
        }
        String[] pool = REPLY_TYPE_1;
        if (gulbi != null && gulbi.getGulbiType() != null) {
            switch (gulbi.getGulbiType()) {
                case "TYPE_2": pool = REPLY_TYPE_2; break;
                case "TYPE_3": pool = REPLY_TYPE_3; break;
                default: pool = REPLY_TYPE_1;
            }
        }
        String template = pool[random.nextInt(pool.length)];
        return String.format(template, snippet).trim();
    }
}
