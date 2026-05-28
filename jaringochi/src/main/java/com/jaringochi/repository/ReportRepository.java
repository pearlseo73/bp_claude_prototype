package com.jaringochi.repository;

import com.jaringochi.domain.MonthlyReport;
import com.jaringochi.domain.WeeklyReport;
import com.jaringochi.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 주간/월간 레포트 Repository
 */
public class ReportRepository {

    // ---------- 주간 ----------
    public WeeklyReport findWeeklyByStart(long userId, LocalDate weekStart) {
        String sql = "SELECT * FROM weekly_reports WHERE user_id = ? AND week_start = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setDate(2, Date.valueOf(weekStart));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapWeekly(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public List<WeeklyReport> findWeeklyHistory(long userId) {
        List<WeeklyReport> list = new ArrayList<>();
        String sql = "SELECT * FROM weekly_reports WHERE user_id = ? ORDER BY week_start DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapWeekly(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    // ---------- 월간 ----------
    public MonthlyReport findMonthly(long userId, int year, int month) {
        String sql = "SELECT * FROM monthly_reports WHERE user_id = ? AND year = ? AND month = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapMonthly(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    private WeeklyReport mapWeekly(ResultSet rs) throws SQLException {
        WeeklyReport r = new WeeklyReport();
        r.setId(rs.getLong("id"));
        r.setUserId(rs.getLong("user_id"));
        r.setWeekStart(rs.getDate("week_start").toLocalDate());
        r.setWeekEnd(rs.getDate("week_end").toLocalDate());
        r.setTotalIncome(rs.getInt("total_income"));
        r.setTotalExpense(rs.getInt("total_expense"));
        r.setBudgetAmount(rs.getInt("budget_amount"));
        r.setBudgetSuccess(rs.getBoolean("budget_success"));
        r.setFeedbackMessage(rs.getString("feedback_message"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) r.setCreatedAt(ts.toLocalDateTime());
        return r;
    }

    private MonthlyReport mapMonthly(ResultSet rs) throws SQLException {
        MonthlyReport r = new MonthlyReport();
        r.setId(rs.getLong("id"));
        r.setUserId(rs.getLong("user_id"));
        r.setYear(rs.getInt("year"));
        r.setMonth(rs.getInt("month"));
        r.setTotalIncome(rs.getInt("total_income"));
        r.setTotalExpense(rs.getInt("total_expense"));
        r.setFeedbackMessage(rs.getString("feedback_message"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) r.setCreatedAt(ts.toLocalDateTime());
        return r;
    }
}
