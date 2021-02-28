package com.org.social.media.domain.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserFollowerRequest {
	@NotBlank(message="Follower userId cannot be Blank")
	private String followerId;
	@NotBlank(message="Followee userId cannot be Blank")
	private String followeeId;
}
