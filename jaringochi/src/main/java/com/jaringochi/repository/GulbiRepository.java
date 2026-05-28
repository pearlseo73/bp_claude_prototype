package com.jaringochi.repository;

import com.jaringochi.domain.Gulbi;
import com.jaringochi.domain.GulbiChild;
import com.jaringochi.domain.GulbiClothes;
import com.jaringochi.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GulbiRepository {
    public Gulbi findByUserId(long userId) {
        String sql = "SELECT * FROM gulbis WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void save(Gulbi gulbi) {
        String sql = "INSERT INTO gulbis (user_id, gulbi_type, personality, weight, streak_record, streak_budget) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gulbi.getUserId());
            pstmt.setString(2, gulbi.getGulbiType());
            pstmt.setString(3, gulbi.getPersonality());
            pstmt.setInt(4, gulbi.getWeight());
            pstmt.setInt(5, gulbi.getStreakRecord());
            pstmt.setInt(6, gulbi.getStreakBudget());
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** 사용자가 보유한 의상 목록 조회 */
    public List<GulbiClothes> findClothesByUserId(long userId) {
        String sql = "SELECT gc.id, gc.name, gc.image_path " +
                     "FROM gulbi_clothes gc " +
                     "INNER JOIN user_gulbi_clothes ugc ON gc.id = ugc.clothes_id " +
                     "WHERE ugc.user_id = ? ORDER BY ugc.acquired_at";
        List<GulbiClothes> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GulbiClothes c = new GulbiClothes();
                    c.setId(rs.getLong("id"));
                    c.setName(rs.getString("name"));
                    c.setImagePath(rs.getString("image_path"));
                    list.add(c);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    /** 사용자의 새끼 굴비 목록 조회 */
    public List<GulbiChild> findChildrenByUserId(long userId) {
        String sql = "SELECT * FROM gulbi_children WHERE user_id = ? ORDER BY child_order";
        List<GulbiChild> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GulbiChild c = new GulbiChild();
                    c.setId(rs.getLong("id"));
                    c.setUserId(rs.getLong("user_id"));
                    c.setChildOrder(rs.getInt("child_order"));
                    c.setWeight(rs.getInt("weight"));
                    Timestamp ts = rs.getTimestamp("acquired_at");
                    if (ts != null) c.setAcquiredAt(ts.toLocalDateTime());
                    list.add(c);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    /** 착용 의상 변경 (null 이면 탈의) */
    public void updateActiveClothes(long userId, Long clothesId) {
        String sql = "UPDATE gulbis SET active_clothes_id = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (clothesId == null) {
                pstmt.setNull(1, Types.BIGINT);
            } else {
                pstmt.setLong(1, clothesId);
            }
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void updateWeight(long userId, int weight) {
        String sql = "UPDATE gulbis SET weight = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, weight);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** 굴비 성격 설정 */
    public void updatePersonality(long userId, String personality) {
        String sql = "UPDATE gulbis SET personality = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, personality);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** 연속 기록/예산 성공 카운트 갱신 */
    public void updateStreaks(long userId, int streakRecord, int streakBudget) {
        String sql = "UPDATE gulbis SET streak_record = ?, streak_budget = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, streakRecord);
            pstmt.setInt(2, streakBudget);
            pstmt.setLong(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** 새끼 굴비 추가 */
    public void saveChild(long userId, int childOrder, int weight) {
        String sql = "INSERT INTO gulbi_children (user_id, child_order, weight) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setInt(2, childOrder);
            pstmt.setInt(3, weight);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    /** 의상 지급 (보유) */
    public void grantClothes(long userId, long clothesId) {
        String sql = "INSERT IGNORE INTO user_gulbi_clothes (user_id, clothes_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, clothesId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Gulbi mapRow(ResultSet rs) throws SQLException {
        Gulbi g = new Gulbi();
        g.setId(rs.getLong("id"));
        g.setUserId(rs.getLong("user_id"));
        g.setGulbiType(rs.getString("gulbi_type"));
        g.setPersonality(rs.getString("personality"));
        g.setWeight(rs.getInt("weight"));
        g.setActiveClothesId(rs.getLong("active_clothes_id"));
        if (rs.wasNull()) g.setActiveClothesId(null);
        g.setStreakRecord(rs.getInt("streak_record"));
        g.setStreakBudget(rs.getInt("streak_budget"));
        Date lrd = rs.getDate("last_record_date");
        if (lrd != null) g.setLastRecordDate(lrd.toLocalDate());
        return g;
    }
}
