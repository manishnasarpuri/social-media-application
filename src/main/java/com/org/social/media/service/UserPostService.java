package com.org.social.media.service;

import com.org.social.media.domain.response.UserFeedResponse;
import com.org.social.media.domain.response.UserFollowUnfollowResponse;
import com.org.social.media.domain.response.UserPostResponse;
import com.org.social.media.exception.UserNotFoundException;

public interface UserPostService {
	public UserPostResponse createPost(String userId, String postId, String content) throws UserNotFoundException;
	
	public UserFeedResponse getNewsFeed(String userId) throws UserNotFoundException;
	
	public UserFollowUnfollowResponse follow(String followerId, String followeeId) throws UserNotFoundException;
	
	public UserFollowUnfollowResponse unfollow(String followerId, String followeeId) throws UserNotFoundException;
}
