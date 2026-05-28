package com.jaringochi.repository;

import com.jaringochi.domain.CommunityComment;
import com.jaringochi.domain.CommunityPost;
import com.jaringochi.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 커뮤니티 게시글/댓글/좋아요 Repository
 */
public class CommunityRepository {

    // ---------- 게시글 ----------

    public List<CommunityPost> findAllPosts() {
        return queryPosts("ORDER BY p.created_at DESC", 0);
    }

    /** 좋아요 순 베스트 게시글 */
    public List<CommunityPost> findBestPosts(int limit) {
        return queryPosts("ORDER BY like_count DESC, p.created_at DESC", limit);
    }

    private List<CommunityPost> queryPosts(String orderLimit, int limit) {
        List<CommunityPost> list = new ArrayList<>();
        String sql = "SELECT p.*, u.nickname AS nickname, " +
                "(SELECT COUNT(*) FROM community_likes l WHERE l.post_id = p.id) AS like_count, " +
                "(SELECT COUNT(*) FROM community_comments c WHERE c.post_id = p.id) AS comment_count " +
                "FROM community_posts p JOIN users u ON p.user_id = u.id " + orderLimit +
                (limit > 0 ? " LIMIT ?" : "");
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (limit > 0) pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapPost(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public CommunityPost findPostById(long id) {
        String sql = "SELECT p.*, u.nickname AS nickname, " +
                "(SELECT COUNT(*) FROM community_likes l WHERE l.post_id = p.id) AS like_count, " +
                "(SELECT COUNT(*) FROM community_comments c WHERE c.post_id = p.id) AS comment_count " +
                "FROM community_posts p JOIN users u ON p.user_id = u.id WHERE p.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapPost(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public long savePost(CommunityPost post) {
        String sql = "INSERT INTO community_posts (user_id, title, content) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, post.getUserId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.executeUpdate();
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return -1;
    }

    // ---------- 댓글 ----------

    public List<CommunityComment> findCommentsByPostId(long postId) {
        List<CommunityComment> list = new ArrayList<>();
        String sql = "SELECT c.*, u.nickname AS nickname FROM community_comments c " +
                     "JOIN users u ON c.user_id = u.id WHERE c.post_id = ? ORDER BY c.created_at";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CommunityComment c = new CommunityComment();
                    c.setId(rs.getLong("id"));
                    c.setPostId(rs.getLong("post_id"));
                    c.setUserId(rs.getLong("user_id"));
                    c.setContent(rs.getString("content"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) c.setCreatedAt(ts.toLocalDateTime());
                    c.setNickname(rs.getString("nickname"));
                    list.add(c);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public void saveComment(CommunityComment comment) {
        String sql = "INSERT INTO community_comments (post_id, user_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, comment.getPostId());
            pstmt.setLong(2, comment.getUserId());
            pstmt.setString(3, comment.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    // ---------- 좋아요 ----------

    public boolean likeExists(long postId, long userId) {
        String sql = "SELECT COUNT(*) FROM community_likes WHERE post_id = ? AND user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, postId);
            pstmt.setLong(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return false;
    }

    public void addLike(long postId, long userId) {
        String sql = "INSERT IGNORE INTO community_likes (post_id, user_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, postId);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void removeLike(long postId, long userId) {
        String sql = "DELETE FROM community_likes WHERE post_id = ? AND user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, postId);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private CommunityPost mapPost(ResultSet rs) throws SQLException {
        CommunityPost p = new CommunityPost();
        p.setId(rs.getLong("id"));
        p.setUserId(rs.getLong("user_id"));
        p.setTitle(rs.getString("title"));
        p.setContent(rs.getString("content"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) p.setCreatedAt(ts.toLocalDateTime());
        p.setNickname(rs.getString("nickname"));
        p.setLikeCount(rs.getInt("like_count"));
        p.setCommentCount(rs.getInt("comment_count"));
        return p;
    }
}
