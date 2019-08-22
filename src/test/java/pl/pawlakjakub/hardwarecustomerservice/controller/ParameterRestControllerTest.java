package pl.pawlakjakub.hardwarecustomerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.pawlakjakub.hardwarecustomerservice.model.Parameter;
import pl.pawlakjakub.hardwarecustomerservice.service.ParameterService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ParameterRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParameterService parameterService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenValidJson_whenPost_thenSucceedWith200() throws Exception {
        Parameter parameter = new Parameter();
        parameter.setName(Parameter.ParameterName.COLOUR);

        String parameterJson = OBJECT_MAPPER.writeValueAsString(parameter);

        mvc.perform(post("/api/parameters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parameterJson))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidJson_whenPost_thenFailWith400() throws Exception {
        String categoryInvalidJson = "{\"name\": COLOUR}";

        mvc.perform(post("/api/parameters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryInvalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenExistingId_whenGet_thenSucceedWith200AnrReturnValidJson() throws Exception {
        Parameter parameter = new Parameter();
        parameter.setName(Parameter.ParameterName.COLOUR);
        parameter.setId(1l);

        String parameterJson = OBJECT_MAPPER.writeValueAsString(parameter);

        given(parameterService.getParameterById(1l)).willReturn(Optional.of(parameter));

        mvc.perform(get("/api/parameters/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parameterJson));
    }

    @Test
    public void givenUnexstingId_whenGet_thenFailWith404() throws Exception {
        given(parameterService.getParameterById(1l)).willReturn(Optional.empty());

        mvc.perform(get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
