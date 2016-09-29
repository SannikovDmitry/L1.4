package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mail.park.java.domain.User;
import ru.mail.park.java.service.UsersService;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by isopov on 29.09.16.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GameControllerTest {
    @MockBean
    private UsersService usersService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin() {
        login();
    }

    private List<String> login() {
        when(usersService.ensureUserExists(anyString())).thenReturn(new User("foo"));

        ResponseEntity<User> loginResp = restTemplate.getForEntity("/login/foo", User.class);
        assertEquals(HttpStatus.OK, loginResp.getStatusCode());
        List<String> coockies = loginResp.getHeaders().get("Set-Cookie");
        assertNotNull(coockies);
        assertFalse(coockies.isEmpty());

        User user = loginResp.getBody();
        assertNotNull(user);

        assertEquals("foo", user.getName());

        return coockies;
    }


    @Test
    public void testMe() {
        List<String> coockies = login();
        System.out.println(coockies);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, coockies);
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        when(usersService.getUser(anyString())).thenReturn(new User("foo"));
        ResponseEntity<User> meResp = restTemplate.exchange("/me", HttpMethod.GET, requestEntity, User.class);

        assertEquals(HttpStatus.OK, meResp.getStatusCode());
        User user = meResp.getBody();
        assertNotNull(user);
        assertEquals("foo", user.getName());
    }
}