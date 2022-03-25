package com.soj.item.transaction.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.MenuItem;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.MenuItemRepository;
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
class MenuItemIntegrationTest {

    @MockBean
    private MenuItemRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void findTheListOfMenuItemIntegrationTesting() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        menuItems.add(menuItem);
        given(repository.findAll()).willReturn(menuItems);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/menuItem")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l));

    }

    @Test
    void findByIdByIntegrationTesting() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        given(repository.findById(1l)).willReturn(java.util.Optional.of(menuItem));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/menuItem/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));
    }

    @Test
    void testToDeleteMenuItemByIdIntegrationTesting() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        doNothing().when(repository).deleteById(1l);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/menuItem/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void testToCreateNewMenuItemIntegrationTesting() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        ObjectMapper mapper = new ObjectMapper();
        String file = mapper.writeValueAsString(menuItem);
        given(repository.saveAndFlush(any())).willReturn(menuItem);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/menuItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void updateMenuItemByIdIntegrationTesting() throws Exception {

        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/MenuItem/MenuItem.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(repository.findById(1l)).willReturn(java.util.Optional.of(menuItem));
        given(repository.saveAndFlush(any())).willReturn(menuItem);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/menuItem/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

}
