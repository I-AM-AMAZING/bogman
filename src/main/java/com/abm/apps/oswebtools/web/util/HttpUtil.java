package com.abm.apps.oswebtools.web.util;

import com.abm.apps.oswebtools.web.domain.ResponseBodyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HttpUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

  public HttpMethod getHttpMethod(String httpMethod) {

    if ("GET".equalsIgnoreCase(httpMethod)) {
      return HttpMethod.GET;
    } else if ("POST".equalsIgnoreCase(httpMethod)) {
      return HttpMethod.POST;
    } else {
      throw new RuntimeException(String.format("Invalid arguement supplied %s", httpMethod));
    }
  }

  public ResponseBodyDTO getResponseBodyDTO(String content) {
    ResponseBodyDTO response = new ResponseBodyDTO();
    response.setResult(content);
    return response;
  }
}
