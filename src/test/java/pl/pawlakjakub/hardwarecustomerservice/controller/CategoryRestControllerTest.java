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
import pl.pawlakjakub.hardwarecustomerservice.model.Category;
import pl.pawlakjakub.hardwarecustomerservice.service.CategoryService;

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
public class CategoryRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

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
        Category category = new Category();
        category.setName(Category.CategoryName.REFRIGERATOR);

        String categoryJson = OBJECT_MAPPER.writeValueAsString(category);

        mvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(categoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidJson_whenPost_thenFailWith400() throws Exception {
        String categoryInvalidJson = "{\"name\": TV}";

        mvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(categoryInvalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenExistingId_whenGet_thenSucceedWith200AndReturnValidJson() throws Exception {
        Category category = new Category();
        category.setName(Category.CategoryName.REFRIGERATOR);
        category.setId(1l);

        String categoryJson = OBJECT_MAPPER.writeValueAsString(category);

        given(categoryService.getCategoryById(1l)).willReturn(Optional.of(category));

        mvc.perform(get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryJson));
    }

    @Test
    public void givenUnexistingId_whenGet_thenFailWith404() throws Exception {
        given(categoryService.getCategoryById(1l)).willReturn(Optional.empty());

        mvc.perform(get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenCategories_whenGet_thenSucceedWith200ReturnJsonArray() throws Exception {
        Category category = new Category();
        category.setName(Category.CategoryName.REFRIGERATOR);
        category.setId(1l);

        List<Category> categories = Arrays.asList(category);

        String categoriesJson = OBJECT_MAPPER.writeValueAsString(categories);

        given(categoryService.getAllCategories()).willReturn(categories);

        mvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(categoriesJson));
    }
}
