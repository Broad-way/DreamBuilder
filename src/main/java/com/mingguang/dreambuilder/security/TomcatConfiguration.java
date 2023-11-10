package com.mingguang.dreambuilder.security;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration {
    @Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(8080);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8443);
        return connector;
    }
    //wss
    @Bean
    public TomcatContextCustomizer tomcatContextCustomizer() {
        return context -> context.addServletContainerInitializer(new WsSci(), null);
    }
}
