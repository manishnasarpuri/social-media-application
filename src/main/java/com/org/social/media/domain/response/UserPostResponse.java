package com.org.social.media.domain.response;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserPostResponse {
	@JsonProperty("PostId")
	@NotNull
	private String postId;
}
