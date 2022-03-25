package com.soj.item.transaction.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.ProductRepository;
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
class ProductIntegrationTest {


    @MockBean
    private ProductRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    void findTheListOfProductIntegrationTesting() throws Exception {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1l);
        products.add(product);
        given(repository.findAll()).willReturn(products);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l));

    }

    @Test
    void findByIdByIntegrationTesting() throws Exception {
        Product product = new Product();
        product.setId(1l);
        given(repository.findById(1l)).willReturn(java.util.Optional.of(product));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));
    }

    @Test
    void testToDeleteProductByIdIntegrationTesting() throws Exception {
        Product product = new Product();
        product.setId(1l);
        doNothing().when(repository).deleteById(1l);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void testToCreateNewProductIntegrationTesting() throws Exception {
        Product product = new Product();
        product.setId(1l);
        ObjectMapper mapper = new ObjectMapper();
        String file = mapper.writeValueAsString(product);
        given(repository.saveAndFlush(any())).willReturn(product);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void updateProductByIdIntegrationTesting() throws Exception {

        Product product = new Product();
        product.setId(1l);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/Product/Product.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(repository.findById(1l)).willReturn(java.util.Optional.of(product));
        given(repository.saveAndFlush(any())).willReturn(product);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

}
