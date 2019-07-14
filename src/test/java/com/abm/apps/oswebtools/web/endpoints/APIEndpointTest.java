package com.abm.apps.oswebtools.web.endpoints;

import com.abm.apps.oswebtools.BogmanApplication;
import com.abm.apps.oswebtools.web.domain.ResponseBodyDTO;
import com.abm.apps.oswebtools.web.util.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = BogmanApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIEndpointTest {

  @LocalServerPort private int port;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Value("classpath:static/get-template.json")
  private String getTemplate;

  @Value("classpath:static/post-template.json")
  private String postTemplate;

  @Value("classpath:static/put-template.json")
  private String putTemplate;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private HttpUtil httpUtil;

  private String getBasePath() {
    return "http://localhost:" + port + "/" + contextPath;
  }

  @Test
  public void test_echoGet() throws Exception {
    String text = "123";

    assertThat(
        restTemplate
            .getForObject(
                getBasePath()+ "/echo/" + text,
                ResponseBodyDTO.class)
            .toString(),
        equalTo( httpUtil.getResponseBodyDTO(String.format("Echo : %s", text)).toString() ));
  }

  @Test
  public void echoPost() {}

  @Test
  public void executePOSTMethod() {}

  @Test
  public void test_getTemplate() {
    assertThat(restTemplate.getForObject(getBasePath()+"/getTemplate", String.class), equalTo( getTemplate));
  }

  @Test
  public void test_postTemplate() {
    assertThat(restTemplate.getForObject(getBasePath()+"/postTemplate", String.class), equalTo( postTemplate));
  }

  @Test
  public void test_putTemplate() {
    assertThat(restTemplate.getForObject(getBasePath()+"/putTemplate", String.class), equalTo( putTemplate));
  }
}
