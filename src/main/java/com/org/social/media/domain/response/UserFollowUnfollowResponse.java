package com.org.social.media.domain.response;

import lombok.Data;

@Data
public class UserFollowUnfollowResponse<D> {
	private String status;
	private D message;
}
