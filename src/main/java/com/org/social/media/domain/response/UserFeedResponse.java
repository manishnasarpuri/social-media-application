package com.org.social.media.domain.response;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserFeedResponse {
	@JsonProperty("postIds")
	@NotNull
	private List<String> listOfPostIds;
}
