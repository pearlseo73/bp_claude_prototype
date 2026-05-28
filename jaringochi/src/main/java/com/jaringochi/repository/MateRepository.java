package com.jaringochi.repository;

import com.jaringochi.domain.Mate;
import com.jaringochi.domain.MateRequest;
import com.jaringochi.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 메이트 관계/요청 Repository
 */
public class MateRepository {

    // ---------- 메이트 관계 ----------

    /** 사용자의 메이트 상대 ID 반환 (없으면 null) */
    public Long findMateUserId(long userId) {
        String sql = "SELECT user1_id, user2_id FROM mates WHERE user1_id = ? OR user2_id = ? LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    long u1 = rs.getLong("user1_id");
                    long u2 = rs.getLong("user2_id");
                    return (u1 == userId) ? u2 : u1;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public boolean hasMate(long userId) {
        return findMateUserId(userId) != null;
    }

    public void createMate(long user1Id, long user2Id) {
        String sql = "INSERT INTO mates (user1_id, user2_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, Math.min(user1Id, user2Id));
            pstmt.setLong(2, Math.max(user1Id, user2Id));
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void deleteMateOfUser(long userId) {
        String sql = "DELETE FROM mates WHERE user1_id = ? OR user2_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // ---------- 메이트 요청 ----------

    /** 내가 받은 PENDING 요청 (보낸 사람 닉네임 포함) */
    public List<MateRequest> findPendingRequestsTo(long userId) {
        List<MateRequest> list = new ArrayList<>();
        String sql = "SELECT r.*, u.nickname AS from_nickname FROM mate_requests r " +
                     "JOIN users u ON r.from_user_id = u.id " +
                     "WHERE r.to_user_id = ? AND r.status = 'PENDING' ORDER BY r.created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MateRequest r = mapRow(rs);
                    r.setFromNickname(rs.getString("from_nickname"));
                    list.add(r);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    /** 내가 보낸 PENDING 요청 (받는 사람 닉네임 포함) */
    public List<MateRequest> findSentRequestsBy(long userId) {
        List<MateRequest> list = new ArrayList<>();
        String sql = "SELECT r.*, u.nickname AS to_nickname FROM mate_requests r " +
                     "JOIN users u ON r.to_user_id = u.id " +
                     "WHERE r.from_user_id = ? AND r.status = 'PENDING' ORDER BY r.created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MateRequest r = mapRow(rs);
                    r.setToNickname(rs.getString("to_nickname"));
                    list.add(r);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public MateRequest findRequestById(long id) {
        String sql = "SELECT * FROM mate_requests WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public boolean pendingRequestExists(long fromUserId, long toUserId) {
        String sql = "SELECT COUNT(*) FROM mate_requests WHERE from_user_id = ? AND to_user_id = ? AND status = 'PENDING'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, fromUserId);
            pstmt.setLong(2, toUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return false;
    }

    public void saveRequest(long fromUserId, long toUserId) {
        String sql = "INSERT INTO mate_requests (from_user_id, to_user_id, status) VALUES (?, ?, 'PENDING')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, fromUserId);
            pstmt.setLong(2, toUserId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateRequestStatus(long id, String status) {
        String sql = "UPDATE mate_requests SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private MateRequest mapRow(ResultSet rs) throws SQLException {
        MateRequest r = new MateRequest();
        r.setId(rs.getLong("id"));
        r.setFromUserId(rs.getLong("from_user_id"));
        r.setToUserId(rs.getLong("to_user_id"));
        r.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) r.setCreatedAt(ts.toLocalDateTime());
        return r;
    }
}
