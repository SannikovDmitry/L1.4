package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.java.domain.User;
import ru.mail.park.java.service.UsersService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by isopov on 29.09.16.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GameControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UsersService usersService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin() {
        when(usersService.ensureUserExists(anyString())).thenReturn(new User("foo"));

        ResponseEntity<User> loginResp = restTemplate.getForEntity("http://localhost:" + port + "/login/foo", User.class);
        assertEquals(HttpStatus.OK, loginResp.getStatusCode());
        User user = loginResp.getBody();
        assertNotNull(user);

        assertEquals("foo", user.getName());
    }


}