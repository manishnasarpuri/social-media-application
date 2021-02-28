package com.org.social.media.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class GenerationUtil {
	public static String createPostId() {
		Random random = new Random();
		return new Integer(random.nextInt(100000000)).toString();
	}
}
