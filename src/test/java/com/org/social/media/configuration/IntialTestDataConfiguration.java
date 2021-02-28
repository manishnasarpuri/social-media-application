package com.org.social.media.configuration;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.org.social.media.domain.Post;
import com.org.social.media.domain.User;

@RunWith(SpringRunner.class)
public class IntialTestDataConfiguration {
	
	private Map<String, User> userFeed = new ConcurrentHashMap<String, User>();
	
	public Map<String, User> getUserFeed() {
		return userFeed;
	}
	public IntialTestDataConfiguration() {
		createData();
	}
	
	private void createData() {
		Date date = new Date();
		String userId = "123455";
		User user = new User();
		user.setUserId(userId);
		Set<String> followers = new HashSet<String>();
		followers.add("123456");
		followers.add("765777");
		followers.add("182973");
		followers.add("975644");
		followers.add("1234554");
		followers.add("12345547");
		user.setFollowers(followers);
		Set<Post> set = new HashSet<Post>();
		Post post = new Post();
		post.setPostId("0917122121");
		post.setPostDate(new Date(date.getTime()- (24 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122122");
		post.setPostDate(new Date(date.getTime()- (48 * 3600000)));
		post.setContent("This is the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122123");
		post.setPostDate(new Date(date.getTime()- (18 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122124");
		post.setPostDate(new Date(date.getTime()- (11 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122125");
		post.setPostDate(new Date(date.getTime()- (12 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122126");
		post.setPostDate(new Date(date.getTime()- (22 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122127");
		post.setPostDate(new Date(date.getTime()- (13 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122128");
		post.setPostDate(new Date(date.getTime()- (33 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122129");
		post.setPostDate(new Date(date.getTime()- (34 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122130");
		post.setPostDate(new Date(date.getTime()- (35 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122131");
		post.setPostDate(new Date(date.getTime()- (36 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122132");
		post.setPostDate(new Date(date.getTime()- (37 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		post = new Post();
		post.setPostId("0917122133");
		post.setPostDate(new Date(date.getTime()- (38 * 3600000)));
		post.setContent("This the real good news");
		set.add(post);
		user.setPosts(set);
		userFeed.put(userId, user);

		String userId2 = "1234554";
		User user2 = new User();
		user2.setUserId(userId2);
		Set<String> followers2 = new HashSet<String>();
		followers2.add("123456");
		followers2.add("765777");
		followers2.add("182973");
		followers2.add("975644");
		user2.setFollowers(followers2);
		Post post2 = new Post();
		post2.setPostId("0917122113");
		post2.setPostDate(new Date(date.getTime()- (48 * 3600000)));
		post2.setContent("This the real good news");
		Set<Post> set2 = new HashSet<Post>();
		set2.add(post2);
		user2.setPosts(set2);
		userFeed.put(userId2, user2);

		String userId3 = "12345547";
		User user3 = new User();
		user3.setUserId(userId3);
		Set<String> followers3 = new HashSet<String>();
		followers3.add("123456");
		followers3.add("765777");
		followers3.add("182973");
		followers3.add("975644");
		user3.setFollowers(followers3);
		Post post3 = new Post();
		post3.setPostId("0917122134");
		post3.setPostDate(new Date(date.getTime()- (72 * 3600000)));
		post3.setContent("This the real good news");
		Set<Post> set3 = new HashSet<Post>();
		set3.add(post3);
		user3.setPosts(set3);
		userFeed.put(userId3, user3);
	}
	
	public User getUser(String userId) {
		return userFeed.get(userId);
	}
	
}
