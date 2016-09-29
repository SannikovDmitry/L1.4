package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mail.park.java.domain.User;
import ru.mail.park.java.service.UsersService;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by isopov on 29.09.16.
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public class MockGameControllerTest {
    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMeRequiresLogin() throws Exception {
        mockMvc
                .perform(get("/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testMe() throws Exception {
        when(usersService.getUser(anyString())).thenReturn(new User("foo"));
        mockMvc
                .perform(get("/me").sessionAttr("username", "foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("foo"))
                .andExpect(jsonPath("wins").value(0))
                .andExpect(jsonPath("power").value(0))
        ;
    }


}