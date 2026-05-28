package com.jaringochi.repository;

import com.jaringochi.domain.Budget;
import com.jaringochi.util.DBUtil;
import java.sql.*;
import java.time.LocalDate;

public class BudgetRepository {
    public Budget findActiveByUserId(long userId) {
        String sql = "SELECT * FROM budgets WHERE user_id = ? ORDER BY start_date DESC LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void save(Budget budget) {
        String sql = "INSERT INTO budgets (user_id, budget_type, weekly_amount, monthly_amount, start_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, budget.getUserId());
            pstmt.setString(2, budget.getBudgetType());
            pstmt.setInt(3, budget.getWeeklyAmount());
            pstmt.setInt(4, budget.getMonthlyAmount());
            pstmt.setDate(5, Date.valueOf(budget.getStartDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private Budget mapRow(ResultSet rs) throws SQLException {
        Budget b = new Budget();
        b.setId(rs.getLong("id"));
        b.setUserId(rs.getLong("user_id"));
        b.setBudgetType(rs.getString("budget_type"));
        b.setWeeklyAmount(rs.getInt("weekly_amount"));
        b.setMonthlyAmount(rs.getInt("monthly_amount"));
        b.setStartDate(rs.getDate("start_date").toLocalDate());
        return b;
    }
}
