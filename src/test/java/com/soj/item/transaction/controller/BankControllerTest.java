package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.service.BankService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Bank.OBJECT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BankControllerTest {
    @Autowired
    private BankController controller;
    @MockBean
    private BankService service;

    @Test
    void findTheListOfBank() {
        List<Bank> banks = new ArrayList<>();
        Bank bank = new Bank();
        bank.setId(1l);
        banks.add(bank);
        when(service.getAllBank()).thenReturn(banks);
        ResponseEntity<List<Resource<Bank>>> resource = controller.findTheListOfBank();
        Assertions.assertEquals(1, banks.size());
    }

    @Test
    void findById() {
        Bank bank = new Bank();
        bank.setId(1l);
        when(service.getBank(1l)).thenReturn(bank);
        ResponseEntity<Resource<Bank>> resources = controller.findById(1l);
        Assertions.assertEquals(1l, bank.getId());
    }

    @Test
    void testToCreateNewBank() {
        Bank bank = new Bank();
        bank.setId(1l);
        Resource<Bank> request = new Resource<>(1l, OBJECT_TYPE, bank);
        when(service.addNewBank(any())).thenReturn(bank);
        ResponseEntity<Resource<Response>> resource = controller.createNewBank(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteBankById() {
        Bank bank = new Bank();
        bank.setId(1l);
        doNothing().when(service).deleteBank(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteBankById(1l);
        Assertions.assertEquals(1l, bank.getId());
    }

    @Test
    void updateBankById() {
        Bank bank = new Bank();
        bank.setId(1l);
        Resource<Bank> request = new Resource<>(1l, OBJECT_TYPE, bank);
        when(service.updateBank(anyLong(), any())).thenReturn(bank);
        ResponseEntity<Resource<Response>> resources = controller.updateBankById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }

    @Test
    void testToGetExceptionBankById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {

                Bank bank = new Bank();
                bank.setId(1l);
                when(service.getBank(1l)).thenThrow(IllegalArgumentException.class);
                controller.findById(1l);
            }
        });
    }
}