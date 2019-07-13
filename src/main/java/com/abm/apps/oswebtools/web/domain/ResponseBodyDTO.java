package com.abm.apps.oswebtools.web.domain;

import lombok.Data;

@Data
public class ResponseBodyDTO {
    private String result;

    @Override
    public String toString() {
        return result;
    }
}
