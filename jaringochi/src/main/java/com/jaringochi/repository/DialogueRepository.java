package com.jaringochi.repository;

import com.jaringochi.domain.DialogueLog;
import com.jaringochi.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 굴비 대화 로그 Repository
 */
public class DialogueRepository {

    public List<DialogueLog> findByUserId(long userId) {
        List<DialogueLog> list = new ArrayList<>();
        String sql = "SELECT * FROM dialogue_logs WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public List<DialogueLog> findByMonthlyReportId(long monthlyReportId) {
        List<DialogueLog> list = new ArrayList<>();
        String sql = "SELECT * FROM dialogue_logs WHERE monthly_report_id = ? ORDER BY created_at";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, monthlyReportId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public void save(DialogueLog log) {
        String sql = "INSERT INTO dialogue_logs (user_id, monthly_report_id, user_message, gulbi_reply) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, log.getUserId());
            if (log.getMonthlyReportId() != null) pstmt.setLong(2, log.getMonthlyReportId());
            else pstmt.setNull(2, Types.BIGINT);
            pstmt.setString(3, log.getUserMessage());
            pstmt.setString(4, log.getGulbiReply());
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private DialogueLog mapRow(ResultSet rs) throws SQLException {
        DialogueLog d = new DialogueLog();
        d.setId(rs.getLong("id"));
        d.setUserId(rs.getLong("user_id"));
        long mrid = rs.getLong("monthly_report_id");
        d.setMonthlyReportId(rs.wasNull() ? null : mrid);
        d.setUserMessage(rs.getString("user_message"));
        d.setGulbiReply(rs.getString("gulbi_reply"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) d.setCreatedAt(ts.toLocalDateTime());
        return d;
    }
}
