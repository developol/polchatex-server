package com.developol.polchatex.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(
            MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/socket/**").authenticated()
                .simpSubscribeDestMatchers("/user/**").authenticated()
                .simpMessageDestMatchers("app/send-message").authenticated()
                .simpMessageDestMatchers("app/send-message/**").authenticated()
                .anyMessage().authenticated();
    }
}
