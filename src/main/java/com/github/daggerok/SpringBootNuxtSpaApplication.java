package com.github.daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Collections.singletonMap;

/*
@Component
class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setErrorPages(new HashSet<>(asList(
                new ErrorPage(NOT_FOUND, "/404"),
                new ErrorPage(BAD_REQUEST, "/err"),
                new ErrorPage(INTERNAL_SERVER_ERROR, "/err")
        )));
    }
}
*/

@Controller
class SPA {

    @GetMapping("")
    String redirect() {
        return "forward:/";
    }

    @GetMapping({ "/", "/inspire", "/404", "/err" })
    String entry() {
        return "index";
    }
}

@RestController
class REST {

    //@CrossOrigin // see CORSConfig
    @GetMapping("/api/data")
    Map props() {
        return singletonMap("data", LocalDateTime.now());
    }
}

@Configuration
class CORSConfig {

    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "3600";

    @Bean
    WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            final ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
}

@SpringBootApplication
public class SpringBootNuxtSpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNuxtSpaApplication.class, args);
    }
}
