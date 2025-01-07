package org.rest;

import org.rest.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String cookies;
    private String result = "";


    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUser() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        List<String> setCookiesHeaders = responseEntity.getHeaders().get("Set-Cookie");
        if (setCookiesHeaders != null){
            cookies = setCookiesHeaders.stream().collect(Collectors.joining(";"));
        }
        List<User> allUser = responseEntity.getBody();
        return allUser;
    }
    public void saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookies);

        if (user.getId() != null) {
            HttpEntity<User> userHttpEntity = new HttpEntity<>(user, headers);

            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(URL, userHttpEntity, String.class);
            result += responseEntity.getBody();
        } else {
            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
            restTemplate.put(URL, requestEntity);
        }

    }
    public void deleteUser(int id) {
        HttpHeaders headers = new HttpHeaders();
        if (cookies != null) {
            headers.set("Cookie", cookies);
        }
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, httpEntity, String.class);
        result += responseEntity.getBody();
        System.out.println(result);
    }
}
