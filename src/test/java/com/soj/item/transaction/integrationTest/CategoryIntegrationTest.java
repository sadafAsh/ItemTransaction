package com.soj.item.transaction.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soj.item.transaction.model.Category;

import com.soj.item.transaction.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryIntegrationTest {

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void findTheListOfCategoryIntegrationTesting() throws Exception {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setId(1l);
        categories.add(category);
        given(repository.findAll()).willReturn(categories);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l));

    }

    @Test
    void findByIdByIntegrationTesting() throws Exception {
        Category category = new Category();
        category.setId(1l);
        given(repository.findById(1l)).willReturn(java.util.Optional.of(category));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));
    }

    @Test
    void testToDeleteCategoryByIdIntegrationTesting() throws Exception {
        Category category = new Category();
        category.setId(1l);
        doNothing().when(repository).deleteById(1l);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void testToCreateNewCategoryIntegrationTesting() throws Exception {
        Category category = new Category();
        category.setId(1l);
        ObjectMapper mapper = new ObjectMapper();
        String file = mapper.writeValueAsString(category);
        given(repository.saveAndFlush(any())).willReturn(category);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void updatCategoryByIdIntegrationTesting() throws Exception {

        Category category = new Category();
        category.setId(1l);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/Category/Category.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(repository.findById(1l)).willReturn(java.util.Optional.of(category));
        given(repository.saveAndFlush(any())).willReturn(category);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

}
