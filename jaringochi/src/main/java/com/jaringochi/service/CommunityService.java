package com.jaringochi.service;

import com.jaringochi.domain.CommunityComment;
import com.jaringochi.domain.CommunityPost;
import com.jaringochi.repository.CommunityRepository;

import java.util.List;

/**
 * 커뮤니티 서비스: 게시글/댓글/좋아요/베스트 고치.
 */
public class CommunityService {
    private final CommunityRepository communityRepository = new CommunityRepository();

    public List<CommunityPost> getAllPosts() {
        return communityRepository.findAllPosts();
    }

    public List<CommunityPost> getBestPosts(int limit) {
        return communityRepository.findBestPosts(limit);
    }

    public CommunityPost getPost(long postId) {
        return communityRepository.findPostById(postId);
    }

    public long createPost(long userId, String title, String content) {
        CommunityPost post = new CommunityPost();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);
        return communityRepository.savePost(post);
    }

    public List<CommunityComment> getComments(long postId) {
        return communityRepository.findCommentsByPostId(postId);
    }

    public void addComment(long postId, long userId, String content) {
        CommunityComment c = new CommunityComment();
        c.setPostId(postId);
        c.setUserId(userId);
        c.setContent(content);
        communityRepository.saveComment(c);
    }

    /** 좋아요 토글: 이미 눌렀으면 취소, 아니면 추가 */
    public void toggleLike(long postId, long userId) {
        if (communityRepository.likeExists(postId, userId)) {
            communityRepository.removeLike(postId, userId);
        } else {
            communityRepository.addLike(postId, userId);
        }
    }

    public boolean hasLiked(long postId, long userId) {
        return communityRepository.likeExists(postId, userId);
    }
}
