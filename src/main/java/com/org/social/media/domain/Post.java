package com.org.social.media.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Post implements Comparable<Post>{
	private String postId;
	private String content;
	private Date postDate;
	@Override
	public int compareTo(Post o) {
		return o.getPostDate().compareTo(this.getPostDate());
	}
}
