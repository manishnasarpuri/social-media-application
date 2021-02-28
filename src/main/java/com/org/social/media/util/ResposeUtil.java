package com.org.social.media.util;

import com.org.social.media.domain.error.ErrorResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResposeUtil {

	public static <E> ErrorResponse createResponse(String status,E errorMessage) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorMessage(errorMessage);
		errorResponse.setStatus(status);
		return errorResponse;
	}
}
