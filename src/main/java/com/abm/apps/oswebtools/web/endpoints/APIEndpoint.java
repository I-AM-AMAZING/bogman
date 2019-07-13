package com.abm.apps.oswebtools.web.endpoints;

import com.abm.apps.oswebtools.web.domain.RequestCallBodyDTO;
import com.abm.apps.oswebtools.web.domain.RequestCallDTO;
import com.abm.apps.oswebtools.web.domain.ResponseBodyDTO;
import com.abm.apps.oswebtools.web.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class APIEndpoint {

  @Value("classpath:static/get-template.json")
  private String getTemplate;

  @Value("classpath:static/post-template.json")
  private String postTemplate;

  @Value("classpath:static/put-template.json")
  private String putTemplate;

  @Autowired private HttpUtil httpUtil;
  @Autowired private RestTemplate restTemplate;

  @GetMapping(path = "/echo/{text}")
  public HttpEntity<?> echoGet(@PathVariable(required = false) String text) {
    return new HttpEntity<>(httpUtil.getResponseBodyDTO(String.format("Echo : %s", text)));
  }

  @PostMapping(path = "/echo", produces = "application/json")
  public HttpEntity<?> echoPost(@RequestBody(required = false) String requestBody) {

    return new HttpEntity<>(httpUtil.getResponseBodyDTO(
        String.format("Hello from POST echo Service received body: [%s]", requestBody)));
  }

  @PostMapping(path = "/executePOST", consumes = "application/json", produces = "application/json")
  public HttpEntity<?> executePOSTMethod(@RequestBody RequestCallDTO request) {

    HttpMethod httpMethod = httpUtil.getHttpMethod(request.getHttpMethod());
    HttpEntity<RequestCallBodyDTO> requestBody =
        new HttpEntity<RequestCallBodyDTO>(request.getRequestBody());
    String URL = request.getUrl();

    HttpEntity<?> response =
        restTemplate.exchange(
            URL,
            httpMethod,
            HttpMethod.GET.equals(httpMethod) ? null : requestBody,
            ResponseBodyDTO.class);

    return response;
  }



  @RequestMapping(value ="/getTemplate", method = RequestMethod.GET, produces = "application/json" )
  public String getTemplate() {
    return getTemplate;
  }

  @RequestMapping(value ="/postTemplate", method = RequestMethod.GET, produces = "application/json" )
  public String postTemplate() {
    return postTemplate;
  }

  @RequestMapping(value ="/putTemplate", method = RequestMethod.GET, produces = "application/json" )
  public String putTemplate() {
    return putTemplate;
  }
}
