package com.org.social.media.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.social.media.domain.response.UserFeedResponse;
import com.org.social.media.domain.response.UserFollowUnfollowResponse;
import com.org.social.media.domain.request.UserFollowerRequest;
import com.org.social.media.domain.request.UserPostRequest;
import com.org.social.media.domain.response.UserPostResponse;
import com.org.social.media.exception.UserNotFoundException;
import com.org.social.media.service.UserPostService;
import com.org.social.media.util.GenerationUtil;

@RestController
@RequestMapping("/users")
@Validated
public class UserPostController {
	@Autowired
	private UserPostService postService;

	/**
	 * Api to create a post
	 *
	 * @param request
	 * @return UserPostResponse object
	 * @throws UserNotFoundException
	 */
	@PostMapping("/post")
	public ResponseEntity<UserPostResponse> createPost(@Valid @RequestBody UserPostRequest request) throws UserNotFoundException {
		return ResponseEntity.ok(postService.createPost(request.getUserId(), GenerationUtil.createPostId(),request.getContent()));
	}

	/**
	 *  Api to get the feeds
	 *
	 * @param userId
	 * @return UserFeedResponse object
	 * @throws UserNotFoundException
	 */
	@GetMapping("/feeds/{userId}")
	public ResponseEntity<UserFeedResponse> getFeeds(@PathVariable @NotBlank(message="UserId is mandatory") String userId) throws UserNotFoundException {
		return ResponseEntity.ok(postService.getNewsFeed(userId));
	}

	/**
	 * Api to follow a user
	 *
	 * @param request
	 * @return UserFollowUnfollowResponse object
	 * @throws UserNotFoundException
	 */
	@PutMapping("/follow")
	public ResponseEntity<UserFollowUnfollowResponse> follow(@RequestBody @Valid UserFollowerRequest request) throws UserNotFoundException {
		return ResponseEntity.ok(postService.follow(request.getFollowerId(), request.getFolloweeId()));
	}

	/**
	 * Api to unfollow a user
	 *
	 * @param request
	 * @return UserFollowUnfollowResponse object
	 * @throws UserNotFoundException
	 */
	@PutMapping("/unfollow")
	public ResponseEntity<UserFollowUnfollowResponse> unfollow(@Valid @RequestBody UserFollowerRequest request) throws UserNotFoundException {
		return ResponseEntity.ok(postService.unfollow(request.getFollowerId(), request.getFolloweeId()));
	}
}
