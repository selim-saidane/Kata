package com.bank.account.services;

import com.bank.account.domain.User;
import com.bank.account.services.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void accountShouldBeCreated() {
        //Given
        User user = new User();
        user.setUserName("Selim");

        //When
        User userSaved = userService.createUser(user);

        //Then
        assertEquals(userSaved, user);
    }

    @Test
    public void accountShouldBeFetched() {
        //Given
        User user = new User();
        user.setUserName("Selim");
        User userSaved = userService.createUser(user);

        //When
        User userFound = userService.getUser(userSaved.getId());

        //Then
        assertNotNull(userFound);
        assertEquals(userSaved, userFound);
    }
}
