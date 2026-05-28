package com.jaringochi.repository;

import com.jaringochi.domain.ScheduledExpense;
import com.jaringochi.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduledExpenseRepository {
    public List<ScheduledExpense> findPendingByUserId(long userId) {
        List<ScheduledExpense> list = new ArrayList<>();
        String sql = "SELECT s.*, c.name as category_name FROM scheduled_expenses s LEFT JOIN categories c ON s.category_id = c.id WHERE s.user_id = ? AND s.status = 'PENDING' ORDER BY s.due_date ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public void save(ScheduledExpense exp) {
        String sql = "INSERT INTO scheduled_expenses (user_id, category_id, name, amount, due_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, exp.getUserId());
            if (exp.getCategoryId() > 0) pstmt.setLong(2, exp.getCategoryId());
            else pstmt.setNull(2, Types.BIGINT);
            pstmt.setString(3, exp.getName());
            pstmt.setInt(4, exp.getAmount());
            pstmt.setDate(5, Date.valueOf(exp.getDueDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(long id) {
        String sql = "DELETE FROM scheduled_expenses WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private ScheduledExpense mapRow(ResultSet rs) throws SQLException {
        ScheduledExpense s = new ScheduledExpense();
        s.setId(rs.getLong("id"));
        s.setUserId(rs.getLong("user_id"));
        s.setCategoryId(rs.getLong("category_id"));
        s.setName(rs.getString("name"));
        s.setAmount(rs.getInt("amount"));
        s.setDueDate(rs.getDate("due_date").toLocalDate());
        s.setStatus(rs.getString("status"));
        s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        s.setCategoryName(rs.getString("category_name"));
        return s;
    }
}
