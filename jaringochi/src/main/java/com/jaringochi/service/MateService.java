package com.jaringochi.service;

import com.jaringochi.domain.*;
import com.jaringochi.repository.GulbiRepository;
import com.jaringochi.repository.MateRepository;
import com.jaringochi.repository.UserRepository;

import java.util.List;

/**
 * 메이트(1:1 친구) 기능 서비스.
 * 요청/수락/해제 및 옆집 굴비 비교 데이터를 제공한다.
 */
public class MateService {
    private final MateRepository mateRepository = new MateRepository();
    private final UserRepository userRepository = new UserRepository();
    private final GulbiRepository gulbiRepository = new GulbiRepository();

    public boolean hasMate(long userId) {
        return mateRepository.hasMate(userId);
    }

    public Long getMateUserId(long userId) {
        return mateRepository.findMateUserId(userId);
    }

    public User getMateUser(long userId) {
        Long mateId = mateRepository.findMateUserId(userId);
        return (mateId != null) ? userRepository.findById(mateId) : null;
    }

    public List<MateRequest> getReceivedRequests(long userId) {
        return mateRepository.findPendingRequestsTo(userId);
    }

    public List<MateRequest> getSentRequests(long userId) {
        return mateRepository.findSentRequestsBy(userId);
    }

    /**
     * 아이디(username)로 메이트 요청.
     * @return 성공 시 null, 실패 시 오류 메시지
     */
    public String requestByUsername(long fromUserId, String username) {
        if (username == null || username.trim().isEmpty()) return "상대방 아이디를 입력해주세요.";
        User target = userRepository.findByUsername(username.trim());
        if (target == null) return "존재하지 않는 사용자예요.";
        if (target.getId() == fromUserId) return "자기 자신에게는 요청할 수 없어요.";
        if (mateRepository.hasMate(fromUserId)) return "이미 메이트가 있어요. 먼저 해제해주세요.";
        if (mateRepository.hasMate(target.getId())) return "상대방은 이미 다른 메이트가 있어요.";
        if (mateRepository.pendingRequestExists(fromUserId, target.getId())) return "이미 요청을 보냈어요.";
        mateRepository.saveRequest(fromUserId, target.getId());
        return null;
    }

    /** 요청 수락: 메이트 관계 생성 + 요청 상태 변경 */
    public void acceptRequest(long requestId, long userId) {
        MateRequest req = mateRepository.findRequestById(requestId);
        if (req == null || req.getToUserId() != userId || !"PENDING".equals(req.getStatus())) return;
        if (mateRepository.hasMate(userId) || mateRepository.hasMate(req.getFromUserId())) {
            mateRepository.updateRequestStatus(requestId, "REJECTED");
            return;
        }
        mateRepository.createMate(req.getFromUserId(), req.getToUserId());
        mateRepository.updateRequestStatus(requestId, "ACCEPTED");
    }

    public void rejectRequest(long requestId, long userId) {
        MateRequest req = mateRepository.findRequestById(requestId);
        if (req == null || req.getToUserId() != userId) return;
        mateRepository.updateRequestStatus(requestId, "REJECTED");
    }

    public void removeMate(long userId) {
        mateRepository.deleteMateOfUser(userId);
    }

    // ---------- 옆집 굴비 비교 ----------
    public Gulbi getMateGulbi(long mateUserId) {
        return gulbiRepository.findByUserId(mateUserId);
    }

    public List<GulbiChild> getMateChildren(long mateUserId) {
        return gulbiRepository.findChildrenByUserId(mateUserId);
    }

    public List<GulbiClothes> getMateClothes(long mateUserId) {
        return gulbiRepository.findClothesByUserId(mateUserId);
    }
}
