package com.developol.polchatex.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Properties {
    //private final String serverUrl = "http://localhost:8080";
    private final String serverUrl = "https://polchatex-server.herokuapp.com";

    private final String serverDomain = "polchatex-server.herokuapp.com";
    //private final String clientUrl = "http://localhost:4200";
    private final String clientUrl = "https://polchatex.herokuapp.com/";

    public String getServerUrl() {
        return serverUrl;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public String getServerDomain() {
        return serverDomain;
    }
}
