package com.jaringochi.repository;

import com.jaringochi.domain.AccountBookEntry;
import com.jaringochi.util.DBUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntryRepository {
    
    public List<AccountBookEntry> findByUserId(long userId) {
        List<AccountBookEntry> list = new ArrayList<>();
        String sql = "SELECT e.*, c.name as category_name FROM account_book_entries e LEFT JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? ORDER BY e.entry_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public List<AccountBookEntry> findRecentByUserId(long userId, int limit) {
        List<AccountBookEntry> list = new ArrayList<>();
        String sql = "SELECT e.*, c.name as category_name FROM account_book_entries e LEFT JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? ORDER BY e.entry_date DESC, e.id DESC LIMIT ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public void save(AccountBookEntry entry) {
        String sql = "INSERT INTO account_book_entries (user_id, category_id, type, amount, memo, entry_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, entry.getUserId());
            if (entry.getCategoryId() > 0) pstmt.setLong(2, entry.getCategoryId());
            else pstmt.setNull(2, Types.BIGINT);
            pstmt.setString(3, entry.getType());
            pstmt.setInt(4, entry.getAmount());
            pstmt.setString(5, entry.getMemo());
            pstmt.setDate(6, Date.valueOf(entry.getEntryDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(long id) {
        String sql = "DELETE FROM account_book_entries WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public int sumByUserIdAndTypeAndDateRange(long userId, String type, LocalDate start, LocalDate end) {
        String sql = "SELECT SUM(amount) FROM account_book_entries WHERE user_id = ? AND type = ? AND entry_date >= ? AND entry_date <= ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, type);
            pstmt.setDate(3, Date.valueOf(start));
            pstmt.setDate(4, Date.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return 0;
    }

    /** 기간 내 전체 거래 조회 (캘린더/통계용) */
    public List<AccountBookEntry> findByUserIdAndDateRange(long userId, LocalDate start, LocalDate end) {
        List<AccountBookEntry> list = new ArrayList<>();
        String sql = "SELECT e.*, c.name as category_name FROM account_book_entries e LEFT JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? AND e.entry_date >= ? AND e.entry_date <= ? ORDER BY e.entry_date, e.id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setDate(2, Date.valueOf(start));
            pstmt.setDate(3, Date.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    /** 카테고리별 합계 (통계용). key=카테고리명, value=합계 */
    public Map<String, Integer> sumByCategory(long userId, String type, LocalDate start, LocalDate end) {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT COALESCE(c.name, '미분류') AS cname, SUM(e.amount) AS total " +
                     "FROM account_book_entries e LEFT JOIN categories c ON e.category_id = c.id " +
                     "WHERE e.user_id = ? AND e.type = ? AND e.entry_date >= ? AND e.entry_date <= ? " +
                     "GROUP BY cname ORDER BY total DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, type);
            pstmt.setDate(3, Date.valueOf(start));
            pstmt.setDate(4, Date.valueOf(end));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) map.put(rs.getString("cname"), rs.getInt("total"));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return map;
    }

    private AccountBookEntry mapRow(ResultSet rs) throws SQLException {
        AccountBookEntry e = new AccountBookEntry();
        e.setId(rs.getLong("id"));
        e.setUserId(rs.getLong("user_id"));
        e.setCategoryId(rs.getLong("category_id"));
        e.setType(rs.getString("type"));
        e.setAmount(rs.getInt("amount"));
        e.setMemo(rs.getString("memo"));
        e.setEntryDate(rs.getDate("entry_date").toLocalDate());
        e.setScheduled(rs.getBoolean("is_scheduled"));
        e.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        e.setCategoryName(rs.getString("category_name"));
        return e;
    }
}
