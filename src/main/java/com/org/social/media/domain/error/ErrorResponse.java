package com.org.social.media.domain.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude
public class ErrorResponse<E> {
	private String status;
	private E errorMessage;
}
