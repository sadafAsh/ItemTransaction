package com.soj.item.transaction.integrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.soj.item.transaction.model.*;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.CustomerRepository;
import com.soj.item.transaction.repository.MenuItemRepository;
import com.soj.item.transaction.repository.OrderRepository;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.service.MenuItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderIntegrationTest {


    @MockBean
    private OrderRepository repository;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private MenuItemService menuItemService;

    @Autowired
    private MockMvc mvc;

    @Test
    void findTheListOfOrderIntegrationTesting() throws Exception {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1l);
        orders.add(order);
        given(repository.findAll()).willReturn(orders);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1l));

    }

    @Test
    void findByIdByIntegrationTesting() throws Exception {
        Order order = new Order();
        order.setId(1l);
        given(repository.findById(1l)).willReturn(java.util.Optional.of(order));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));
    }

    @Test
    void testToDeleteOrderByIdIntegrationTesting() throws Exception {
        Order order = new Order();
        order.setId(1l);
        doNothing().when(repository).deleteById(1l);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

    @Test
    void testToCreateNewOrderIntegrationTesting() throws Exception {

        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(3l);
        order.setId(1l);
        order.setCustomer(customer);
        List<OrderDetail> details = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(15l);
        Product product = new Product();
        product.setId(18l);
        menuItem.setProduct(product);
        orderDetail.setMenuItem(menuItem);
        details.add(orderDetail);
        order.setOrderDetailList(details);


        Stream<String> data = Files.lines(Paths.get("src/test/resources/Order/Order.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(customerService.getCustomer(3l)).willReturn(customer);
        given(menuItemService.getMenuItem(15l)).willReturn(menuItem);
        given(repository.saveAndFlush(any())).willReturn(order);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }


    @Test
    void updateBankByIdIntegrationTesting() throws Exception {

        Order order = new Order();
        order.setId(1l);

        Stream<String> data = Files.lines(Paths.get("src/test/resources/Order/Order.json"));
        String file = data.collect(Collectors.joining("\n"));
        given(repository.findById(1l)).willReturn(java.util.Optional.of(order));
        given(repository.saveAndFlush(any())).willReturn(order);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1l));

    }

}
