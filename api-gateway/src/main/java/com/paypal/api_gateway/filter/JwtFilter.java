package com.paypal.api_gateway.filter;

import com.paypal.api_gateway.JwtUtil;
import io.jsonwebtoken.Claims;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtFilter implements GlobalFilter, Ordered {


    public static  final List<String> PUBLIC_PATHS= List.of(
            "/auth/signup",
            "/auth/login"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path= exchange.getRequest().getPath().value();
        String normalization=path.replaceAll("/+$","");

        if(PUBLIC_PATHS.contains(normalization)){
            return chain.filter(exchange)
                    .doOnSubscribe(s-> System.out.println("Proceeding without check"))
                    .doOnSuccess(v-> System.err.println("successfully passed"))
                    .doOnError(e-> System.err.println(" eror occured"));
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if ( authHeader==null || !authHeader.startsWith("Bearer")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

       try {
            String  token= authHeader.substring(7);
            Claims claims= JwtUtil.validateToken(token);
            exchange.getRequest().mutate()
                    .header("X-User-Email",claims.getSubject())
                    .build();

            return chain.filter(exchange)
                    .doOnSubscribe(s-> System.out.println("Proceeding without check"))
                    .doOnSuccess(v-> System.err.println("successfully passed"))
                    .doOnError(e-> System.err.println(" eror occured"));

        }catch (Exception e){

           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
           return exchange.getResponse().setComplete();
       }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
