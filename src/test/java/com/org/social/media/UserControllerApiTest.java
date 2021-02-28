package com.org.social.media;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.org.social.media.domain.response.UserFeedResponse;
import com.org.social.media.domain.response.UserFollowUnfollowResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.org.social.media.controller.UserPostController;
import com.org.social.media.domain.response.UserPostResponse;
import com.org.social.media.exception.UserNotFoundException;
import com.org.social.media.service.UserPostServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserPostController.class)
public class UserControllerApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserPostServiceImpl userPost;

  private UserPostResponse userPostResponse;

  @Before
  public void setUp() throws UserNotFoundException {
    userPostResponse = new UserPostResponse();
    userPostResponse.setPostId("123");
    given(userPost.createPost(anyString(), anyString(), anyString()))
        .willReturn(userPostResponse);
    given(userPost.follow("follower", "followee"))
        .willReturn(new UserFollowUnfollowResponse());

    given(userPost.unfollow("follower", "followee"))
        .willReturn(new UserFollowUnfollowResponse());
    given(userPost.getNewsFeed("123"))
        .willReturn(new UserFeedResponse());
  }

  @Test
  public void shouldReturn200_GivenallThingsCorrect() throws Exception {
    mockMvc.perform(post("/users/post")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"userId\" : \"nikhil\", \"content\": \"hello\" }"))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void shouldReturn400_InvalidUserId() throws Exception {

    mockMvc.perform(post("/users/post")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"userId\" : \"\", \"content\": \"hello\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"errorMessage\":[\"UserId cannot be Blank\"]}"));
  }

  @Test
  public void shouldReturn400_InvalidContent() throws Exception {
    mockMvc.perform(post("/users/post")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"userId\" : \"nikhil\", \"content\": \"\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"errorMessage\":[\"Post Content cannot be Blank\"]}"));
  }

  @Test
  public void shouldReturn200_followRequest() throws Exception {
    mockMvc.perform(put("/users/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"follower\", \"followeeId\": \"followee\" }"))
        .andExpect(status().is2xxSuccessful());
  }


  @Test
  public void shouldReturn400_followRequest_InvalidBody() throws Exception {
    mockMvc.perform(put("/users/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"\", \"followeeId\": \"\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("\"status\":\"BAD_REQUEST\"")));
  }

  @Test
  public void shouldReturn400_followRequest_InvalidFollowee() throws Exception {
    mockMvc.perform(put("/users/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"abce\", \"followeeId\": \"\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"errorMessage\":[\"Followee userId cannot be Blank\"]}"));
  }

  @Test
  public void shouldReturn200_UnFollowfollowRequest() throws Exception {
    mockMvc.perform(put("/users/unfollow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"follower\", \"followeeId\": \"followee\" }"))
        .andExpect(status().is2xxSuccessful());
  }


  @Test
  public void shouldReturn400_UnfollowRequest_InvalidBody() throws Exception {
    mockMvc.perform(put("/users/unfollow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"\", \"followeeId\": \"\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("\"status\":\"BAD_REQUEST\"")));
  }

  @Test
  public void shouldReturn400_UnfollowRequest_InvalidFollowee() throws Exception {
    mockMvc.perform(put("/users/unfollow")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"followerId\" : \"abce\", \"followeeId\": \"\" }"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("{\"status\":\"BAD_REQUEST\",\"errorMessage\":[\"Followee userId cannot be Blank\"]}"));
  }

  @Test
  public void shouldReturn200_GetNewsFeedRequest() throws Exception {
    mockMvc.perform(get("/users/feeds/123")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void shouldReturn400_GetNewsFeedRequest() throws Exception {
    mockMvc.perform(get("/users/feeds")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }
}
