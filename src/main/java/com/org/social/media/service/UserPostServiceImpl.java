package com.org.social.media.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.org.social.media.configuration.IntialDataConfiguration;
import com.org.social.media.domain.Post;
import com.org.social.media.domain.User;
import com.org.social.media.domain.response.UserFeedResponse;
import com.org.social.media.domain.response.UserFollowUnfollowResponse;
import com.org.social.media.domain.response.UserPostResponse;
import com.org.social.media.exception.UserNotFoundException;

@Service
public class UserPostServiceImpl implements UserPostService {

	@Autowired
	private IntialDataConfiguration dataConfiguration;

	/**
	 * Method to create a post in Database
	 *
	 * @param userId
	 * @param postId
	 * @param content
	 * @return UserPostResponse
	 * @throws UserNotFoundException
	 */
	@Override
	public UserPostResponse createPost(String userId, String postId, String content) throws UserNotFoundException {
		if(validateUser(userId))
		{
			User user = dataConfiguration.getUser(userId);
			Post post = new Post();
			post.setPostId(postId);
			post.setContent(content);
			post.setPostDate(new Date());
			user.getPosts().add(post);
			UserPostResponse response = new UserPostResponse();
			response.setPostId(postId);
			return response;
		}
		return null;
	}

	/**
	 * Method to get the news feed for user
	 *
	 * @param userId
	 * @return UserFeedResponse
	 * @throws UserNotFoundException
	 */
	@Override
	public UserFeedResponse getNewsFeed(String userId) throws UserNotFoundException {
		UserFeedResponse response = new UserFeedResponse();
		List<String> postIds = new ArrayList<String>();
		if(validateUser(userId)) {
			User user = dataConfiguration.getUser(userId);
			TreeSet<Post> posts = new TreeSet<Post>();
			posts.addAll(user.getPosts());
			Set<String> followers = user.getFollowers();
			followers.forEach(str->{
				User usr = dataConfiguration.getUser(str);
				if(null!=usr && null!=usr.getPosts())
					posts.addAll(usr.getPosts());
			});
			posts.stream().limit(20).forEach(pst->{
				postIds.add(pst.getPostId());
			});
			response.setListOfPostIds(postIds);
		}
		return response;
	}

	/**
	 * Method to follow a user
	 *
	 * @param followerId
	 * @param followeeId
	 * @return UserFollowUnfollowResponse
	 * @throws UserNotFoundException
	 */
	@Override
	public UserFollowUnfollowResponse follow(String followerId, String followeeId) throws UserNotFoundException {
		UserFollowUnfollowResponse<String> followUnfollowResponse = new UserFollowUnfollowResponse<String>();
		if(validateUser(followerId) && validateUser(followeeId)) {
			User userIdToFollow = dataConfiguration.getUser(followeeId);
			userIdToFollow.getFollowers().add(followerId);
			followUnfollowResponse.setMessage(followerId + " now follows " +followeeId);
			followUnfollowResponse.setStatus(HttpStatus.OK.name());
		}
		return followUnfollowResponse;
	}

	/**
	 * Method to unfollow a user
	 *
	 * @param followerId
	 * @param followeeId
	 * @return UserFollowUnfollowResponse
	 * @throws UserNotFoundException
	 */
	@Override
	public UserFollowUnfollowResponse unfollow(String followerId, String followeeId) throws UserNotFoundException {
		UserFollowUnfollowResponse<String> followUnfollowResponse = new UserFollowUnfollowResponse<String>();
		if(validateUser(followerId) && validateUser(followeeId)) {
			User userIdToFollow = dataConfiguration.getUser(followeeId);
			if(!userIdToFollow.getFollowers().contains(followerId)) {
				throw new UserNotFoundException(followerId + " Doesnot Follow "+followeeId);
			}
			userIdToFollow.getFollowers().remove(followerId);
			followUnfollowResponse.setMessage(followerId + " now unfollows " +followeeId);
			followUnfollowResponse.setStatus(HttpStatus.OK.name());
		}
		return followUnfollowResponse;
	}

	/**
	 * Method to validate a user
	 *
	 * @param followerId
	 * @return Boolean
	 * @throws UserNotFoundException
	 */
	private Boolean validateUser(String followerId) throws UserNotFoundException {
		if(null == dataConfiguration.getUser(followerId))
			throw new UserNotFoundException(followerId + " User Not Found");
		return Boolean.TRUE;
	}
}
