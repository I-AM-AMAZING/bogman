package com.abm.apps.oswebtools;

import com.abm.apps.oswebtools.web.endpoints.APIEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OsWebToolsApplicationTests {

    @Autowired
    private APIEndpoint apiEndpoint;

    @Test
    public void contextLoads() {
        assertThat(apiEndpoint).isNotNull();
    }

}
