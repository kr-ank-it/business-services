package com.ank.productmicroservice.commons;

import com.ank.productmicroservice.dtos.UserDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {
    private RestTemplate restTemplate;
    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token) {
        String url = "http://AUTHSERVICE/auth/validate/" + token;
        ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(url, UserDto.class);
        return responseEntity.getBody();
    }

}
