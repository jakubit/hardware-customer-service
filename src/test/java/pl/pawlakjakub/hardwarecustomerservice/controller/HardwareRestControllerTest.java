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
import pl.pawlakjakub.hardwarecustomerservice.model.Comment;
import pl.pawlakjakub.hardwarecustomerservice.model.Hardware;
import pl.pawlakjakub.hardwarecustomerservice.model.HardwareParameter;
import pl.pawlakjakub.hardwarecustomerservice.model.Parameter;
import pl.pawlakjakub.hardwarecustomerservice.service.CommentService;
import pl.pawlakjakub.hardwarecustomerservice.service.HardwareParameterService;
import pl.pawlakjakub.hardwarecustomerservice.service.HardwareService;

import java.util.Arrays;
import java.util.List;
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
public class HardwareRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HardwareService hardwareService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private HardwareParameterService hardwareParameterService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenExistingId_whenGet_thenSucceedWith200AndReturnValidJson() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setBroken(true);
        hardware.setId(1l);

        String hardwareJson = OBJECT_MAPPER.writeValueAsString(hardware);

        given(hardwareService.getHardwareById(1l)).willReturn(Optional.of(hardware));

        mvc.perform(get("/api/hardware/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(hardwareJson));
    }

    @Test
    public void givenUnexistingId_whenGet_thenFailWith404() throws Exception {
        given(hardwareService.getHardwareById(1l)).willReturn(Optional.empty());

        mvc.perform(get("/api/hardware/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenHardwareId_whenGetComments_thenSucceedWith200AndReturnCommentsJsonArray() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        Comment comment = new Comment(1l, "OK", hardware);

        hardware.getCommentSet().add(comment);

        List<Comment> comments = Arrays.asList(comment);

        String commentsJson = OBJECT_MAPPER.writeValueAsString(comments);

        given(commentService.getAllHardwareComments(1l)).willReturn(comments);

        mvc.perform(get("/api/hardware/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(commentsJson));
    }

    @Test
    public void givenCommentJson_whenPost_thenSucceedWith200() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        Comment comment = new Comment(1l, "OK", hardware);

        hardware.getCommentSet().add(comment);

        String commentJson = OBJECT_MAPPER.writeValueAsString(comment);

        mvc.perform(post("/api/hardware/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson))
                .andExpect(status().isOk());
    }


    @Test
    public void givenHardwareId_whenGetParameters_thenSucceedWith200AndReturnParametersJsonArray() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        Parameter parameter = new Parameter(2l, Parameter.ParameterName.COLOUR);

        HardwareParameter hardwareParameter = new HardwareParameter(3l, "RED", hardware, parameter);

        hardware.getHardwareParameterSet().add(hardwareParameter);

        List<HardwareParameter> hardwareParameters = Arrays.asList(hardwareParameter);

        String parametersJson = OBJECT_MAPPER.writeValueAsString(hardwareParameters);

        given(hardwareParameterService.getAllHardwareParameters(1l)).willReturn(hardwareParameters);

        mvc.perform(get("/api/hardware/1/parameters")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parametersJson));
    }

    @Test
    public void givenParameterJson_whenPost_thenSucceedWith200() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        Parameter parameter = new Parameter(2l, Parameter.ParameterName.COLOUR);

        HardwareParameter hardwareParameter = new HardwareParameter(3l, "RED", hardware, parameter);

        hardware.getHardwareParameterSet().add(hardwareParameter);

        String parameterJson = OBJECT_MAPPER.writeValueAsString(hardwareParameter);

        mvc.perform(post("/api/hardware/1/parameters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parameterJson))
                .andExpect(status().isOk());
    }

    @Test
    public void givenHardwareList_whenGet_thenSucceedWith200AndReturnJsonArray() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        List<Hardware> hardwareList = Arrays.asList(hardware);

        String hardwareListJson = OBJECT_MAPPER.writeValueAsString(hardwareList);

        given(hardwareService.getAllHardware()).willReturn(hardwareList);

        mvc.perform(get("/api/hardware")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(hardwareListJson));
    }

    @Test
    public void givenValidJson_whenPost_thenSucceedWith200() throws Exception {
        Hardware hardware = new Hardware();
        hardware.setId(1l);
        hardware.setBroken(false);

        String hardwareJson = OBJECT_MAPPER.writeValueAsString(hardware);

        mvc.perform(post("/api/hardware")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hardwareJson))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidJson_whenPost_thenFailWith400() throws Exception {
        String invalidJson = "{\"category\": TV}";

        mvc.perform(post("/api/hardware")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
