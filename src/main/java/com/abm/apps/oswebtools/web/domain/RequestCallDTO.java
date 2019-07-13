package com.abm.apps.oswebtools.web.domain;

import lombok.Data;

import java.util.Map;

/*
    Object that maps to the Request Call JSON
 */
@Data
public class RequestCallDTO {

    private String httpMethod;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> requestParams;
    private Boolean requestParamsAreQueryParams = Boolean.FALSE;
    private RequestCallBodyDTO requestBody;

}
