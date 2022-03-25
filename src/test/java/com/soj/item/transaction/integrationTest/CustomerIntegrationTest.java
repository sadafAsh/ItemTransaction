package com.soj.item.transaction.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.CustomerRepository;
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
class CustomerIntegrationTest {

    @MockBean
    private CustomerRepository repository;
    @MockBean
    private BankRepository bankRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    void findTheListOfCustomerIntegrationTesting() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1l);
        customers.add(customer);
        given(repository.findAll()).willReturn(customers);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l));

    }

    @Test
    void findByIdByIntegrationTesting() throws Exception {
        Customer customer = new Customer();
        customer.setId(1l);
        given(repository.findById(1l)).willReturn(java.util.Optional.of(customer));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));
    }

    @Test
    void testToDeleteCustomerByIdIntegrationTesting() throws Exception {
        Customer customer = new Customer();
        customer.setId(1l);
        doNothing().when(repository).deleteById(1l);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void testToCreateNewCustomerIntegrationTesting() throws Exception {

        Customer customer = new Customer();
        Bank bank = new Bank();
        List<Bank> banks = new ArrayList<>();
        bank.setAccNo(12);
        customer.setBank(bank);
        customer.setId(1l);
        banks.add(bank);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/Customer/Customer.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(bankRepository.findByAccNo(12)).willReturn(banks);
        given(repository.saveAndFlush(any())).willReturn(customer);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void updateCustomerByIdIntegrationTesting() throws Exception {

        Customer customer = new Customer();
        customer.setId(1l);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/Customer/Customer.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(repository.findById(1l)).willReturn(java.util.Optional.of(customer));
        given(repository.saveAndFlush(any())).willReturn(customer);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

}
