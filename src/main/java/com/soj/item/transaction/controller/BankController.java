package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.service.BankService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Bank.OBJECT_TYPE;

@RestController
@RequestMapping("api/v1")
public class BankController {

    @Autowired
    private BankService service;

    @GetMapping("/bank")
    public ResponseEntity<List<Resource<Bank>>> findTheListOfBank() {
        List<Resource<Bank>> resources = new ArrayList<>();
        if (resources.isEmpty()) {
            List<Bank> banks = service.getAllBank();
            banks.forEach(x -> {
                Resource<Bank> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);


    }


    @GetMapping("/bank/{id}")
    public ResponseEntity<Resource<Bank>> findById(@PathVariable long id) {
        Bank bank = service.getBank(id);
        Resource<Bank> resource = new Resource<>(id, OBJECT_TYPE, bank);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @PostMapping("/bank")
    public ResponseEntity<Resource<Response>> createNewBank(@RequestBody Resource<Bank> request) {
        Bank bank = request.getAttribute();
        Bank bank1 = service.addNewBank(bank);
        Response response = new Response(bank1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(bank1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/bank/{id}")
    public ResponseEntity<Resource<Response>> deleteBankById(@PathVariable long id) {
        service.deleteBank(id);
        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/bank/{id}")
    public ResponseEntity<Resource<Response>> updateBankById(@PathVariable long id, @RequestBody Resource<Bank> request) {
        Bank bank = request.getAttribute();
        service.updateBank(id, bank);
        Response response = new Response(id, "update successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
