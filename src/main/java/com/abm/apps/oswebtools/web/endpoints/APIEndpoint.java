package com.abm.apps.oswebtools.web.endpoints;

import com.abm.apps.oswebtools.web.domain.RequestCallBodyDTO;
import com.abm.apps.oswebtools.web.domain.RequestCallDTO;
import com.abm.apps.oswebtools.web.domain.ResponseBodyDTO;
import com.abm.apps.oswebtools.web.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class APIEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(APIEndpoint.class);

  @Autowired private DiscoveryClient discoveryClient;

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
    return new HttpEntity<>(
        httpUtil.getResponseBodyDTO(
            String.format("Hello from POST echo Service received body: [%s]", requestBody)));
  }

  /**
   * Currently works only for GET requests
   * @param request
   * @return
   */
  @PostMapping(path = "/makeCall", consumes = "application/json", produces = "application/json")
  public HttpEntity<?> makeCall(@RequestBody RequestCallDTO request) {

    //Prepare
    HttpMethod httpMethod = httpUtil.getHttpMethod(request.getHttpMethod());
    HttpEntity<RequestCallBodyDTO> requestBody = new HttpEntity<>(request.getRequestBody());

    List<String> eurekaURLs = null;
    String URL = request.getUrl();
    if(request.getUrlIsEurekaServiceId()){
      eurekaURLs = obtainEurekaServiceURL(URL);
    }
    Map<String, String> headers = request.getHeaders();
    Map<String, String> requestParams = request.getRequestParams();

    HttpEntity<?> response =
        restTemplate.exchange(
            request.getUrlIsEurekaServiceId() ? eurekaURLs.get(0) : URL,
            httpMethod,
            requestBody,
            ResponseBodyDTO.class);

    return response;
  }

  private List<String> obtainEurekaServiceURL(String serviceId) {
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
    if (CollectionUtils.isEmpty(serviceInstances)) {
      LOGGER.error("No service instances found for service id {}", serviceId);
    }
    List<String> serviceURLs = new ArrayList<>();
    serviceInstances.forEach(i -> serviceURLs.add(i.getUri().toString()));
    return serviceURLs;
  }

  @RequestMapping(value = "/getTemplate", method = RequestMethod.GET, produces = "application/json")
  public String getTemplate() {
    return getTemplate;
  }

  @RequestMapping(
      value = "/postTemplate",
      method = RequestMethod.GET,
      produces = "application/json")
  public String postTemplate() {
    return postTemplate;
  }

  @RequestMapping(value = "/putTemplate", method = RequestMethod.GET, produces = "application/json")
  public String putTemplate() {
    return putTemplate;
  }
}
