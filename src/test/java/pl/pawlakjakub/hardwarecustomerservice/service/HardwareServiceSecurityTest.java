package pl.pawlakjakub.hardwarecustomerservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HardwareServiceSecurityTest {

    @Autowired
    private HardwareService hardwareService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void givenUnauthenticated_whenCallService_thenThrowException() {
        hardwareService.getAllHardware();
    }

    @Test
    @WithMockUser
    public void givenAuthenticated_whenCallService_thenOk() {
        assertThat(hardwareService.getAllHardware()).isNotNull();
    }
}
