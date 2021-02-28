package com.org.social.media.domain.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude
public class UserPostRequest {
	@NotBlank(message="UserId cannot be Blank")
	private String userId;
	@NotBlank(message="Post Content cannot be Blank")
	private String content;
}
