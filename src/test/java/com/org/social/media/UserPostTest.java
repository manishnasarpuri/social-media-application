package com.org.social.media;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.org.social.media.configuration.IntialDataConfiguration;
import com.org.social.media.domain.Post;
import com.org.social.media.domain.User;
import com.org.social.media.service.UserPostServiceImpl;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserPostTest {

	@LocalServerPort
	private int port;

	@Autowired
	private UserPostServiceImpl postService;

	@Autowired
	private IntialDataConfiguration configuration;

	private Map<String, User> userFeed = new ConcurrentHashMap<String, User>();

	@Before
	public void setUp(){
		createTestData();
		ReflectionTestUtils.setField(configuration, "userFeed", userFeed);
		ReflectionTestUtils.setField(postService, "dataConfiguration", configuration);
	}

	@Test
	public void shouldReturn200_CreatePost() {
		given()
				.contentType(ContentType.JSON).body("{ \"userId\" : \"123455\", \"content\": \"hello\" }").when()
				.post(String.format("http://localhost:%s/users/post", port))
				.then()
				.statusCode(is(200));
	}

	@Test
	public void shouldReturn404_CreatePost() {
		given()
				.contentType(ContentType.JSON).body("{ \"userId\" : \"12345\", \"content\": \"hello\" }").when()
				.post(String.format("http://localhost:%s/users/post", port))
				.then()
				.statusCode(is(404))
				.body("status", Matchers.is(HttpStatus.NOT_FOUND.name()));
	}

	@Test
	public void shouldReturn200_UnFollowPost() {
		given()
				.contentType(ContentType.JSON).body("{ \"followerId\" : \"123455\", " +
				"\"followeeId\": \"123456\" }").when()
				.put(String.format("http://localhost:%s/users/unfollow", port))
				.then()
				.statusCode(is(200))
				.body("message", Matchers.is("123455 now unfollows 123456"));;
	}

	@Test
	public void shouldReturn404_UnFollowPost() {
		given()
				.contentType(ContentType.JSON).body("{ \"followerId\" : \"1234\", \"followeeId\": \"12345\" }").when()
				.put(String.format("http://localhost:%s/users/unfollow", port))
				.then()
				.statusCode(is(404))
				.body("status", Matchers.is(HttpStatus.NOT_FOUND.name()));
	}

	@Test
	public void shouldReturn404_UnFollowPost_With_____() {
		given()
				.contentType(ContentType.JSON).body("{ \"followerId\" : \"12345547890\", " +
				"\"followeeId\": \"123455\" }").when()
				.put(String.format("http://localhost:%s/users/unfollow", port))
				.then()
				.statusCode(is(404))
				.body("status", Matchers.is(HttpStatus.NOT_FOUND.name()));
	}


	@Test
	public void shouldReturn200_FollowPost() {
		given()
				.contentType(ContentType.JSON).body("{ \"followerId\" : \"123455\", \"followeeId\": " +
				"\"12345547\" }").when()
				.put(String.format("http://localhost:%s/users/follow", port))
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("message", Matchers.is("123455 now follows 12345547"));
	}

	@Test
	public void shouldReturn404_FollowPost() {
		given()
				.contentType(ContentType.JSON).body("{ \"followerId\" : \"123455\", \"followeeId\": " +
				"\"12345\" }").when()
				.put(String.format("http://localhost:%s/users/follow", port))
				.then()
				.statusCode(is(404))
				.body("status", Matchers.is(HttpStatus.NOT_FOUND.name()));
	}

	@Test
	public void shouldReturn200_GetNewsFeed() {
		given()
				.pathParam("userId", "123455")
				.when()
				.get(String.format("http://localhost:%s/users/feeds/{userId}", port))
				.then()
				.statusCode(is(200))
				.body("postIds", Matchers.hasItem("0917122124"));
	}

	@Test
	public void shouldReturn404_GetNewsFeed() {
		given()
				.pathParam("userId", "123")
				.when()
				.get(String.format("http://localhost:%s/users/feeds/{userId}", port))
				.then()
				.statusCode(is(404))
				.body("status", Matchers.is(HttpStatus.NOT_FOUND.name()));
	}




	private void createTestData() {
		Date date = new Date();
		String userId = "123455";
		User user = new User();
		user.setUserId(userId);
		Set<String> followers = new HashSet<String>();
		followers.add("123456");
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

		String userId4 = "12345547890";
		User user4 = new User();
		user4.setUserId(userId4);
		Set<String> followers4 = new HashSet<String>();
		user4.setFollowers(followers4);
		Post post4 = new Post();
		post4.setPostId("0911347878");
		post4.setPostDate(new Date(date.getTime()- (72 * 3600000)));
		post4.setContent("This the real good news");
		Set<Post> set4 = new HashSet<Post>();
		set4.add(post4);
		user4.setPosts(set4);
		userFeed.put(userId4, user4);

		String userId5 = "123456";
		User user5 = new User();
		user5.setUserId(userId4);
		Set<String> followers5 = new HashSet<String>();
		user5.setFollowers(followers5);
		followers5.add("123455");
		Post post5 = new Post();
		post5.setPostId("091134");
		post5.setPostDate(new Date(date.getTime()- (72 * 3600000)));
		post5.setContent("This the real good news");
		Set<Post> set5 = new HashSet<Post>();
		set5.add(post4);
		user5.setPosts(set5);
		userFeed.put(userId5, user5);
	}

}
