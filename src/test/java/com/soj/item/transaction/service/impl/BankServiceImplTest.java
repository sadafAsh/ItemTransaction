package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.service.BankService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BankServiceImplTest {
    @Autowired
    private BankService service;
    @MockBean
    private BankRepository repository;


    @Test
    void testToGetAllBank() {
        List<Bank> list = new ArrayList<>();
        Bank bank = new Bank();
        bank.setId(1l);
        list.add(bank);
        when(repository.findAll()).thenReturn(list);
        List<Bank> bank1 = service.getAllBank();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testToGetBankById() {
        Bank bank = new Bank();
        bank.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(bank));
        Bank bank1 = service.getBank(1l);
        Assertions.assertEquals(1l, bank1.getId());
    }

    @Test
    void testToGetExceptionBankById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Bank bank = new Bank();
                bank.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(bank));
                Bank bank1 = service.getBank(1l);
            }
        });
    }

    @Test
    void testToAddNewBank() {
        Bank bank = new Bank();
        bank.setId(1l);
        when(repository.saveAndFlush(any())).thenReturn(bank);
        Bank bank1 = service.addNewBank(bank);
        Assertions.assertEquals(1l, bank1.getId());
    }

    @Test
    void testToDeleteBankById() {
        Bank bank = new Bank();
        bank.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteBank(1l);
        Assertions.assertEquals(1l, bank.getId());
    }

    @Test
    void testToDeleteExceptionBankById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Bank bank = new Bank();
                bank.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteBank(1l);
            }
        });
    }

    @Test
    void testUpdateBankById() {
        Bank bank = new Bank();
        bank.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(bank));
        when(repository.saveAndFlush(any())).thenReturn(bank);
        Bank bank1 = service.updateBank(1l, bank);
        Assertions.assertEquals(1l, bank1.getId());
    }

    @Test
    void testToThrowExceptionInUpdateBankById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Bank bank = new Bank();
                bank.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(bank));
                when(repository.saveAndFlush(any())).thenReturn(bank);
                Bank bank1 = service.updateBank(1l, bank);

            }
        });
    }


}