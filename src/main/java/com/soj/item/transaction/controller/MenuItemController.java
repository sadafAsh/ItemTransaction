package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.MenuItem;
import com.soj.item.transaction.service.MenuItemService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static com.soj.item.transaction.model.MenuItem.OBJECT_TYPE;

@RestController
@RequestMapping("/api/v1/menuItem")
public class MenuItemController {

        @Autowired
        private MenuItemService service;

        @GetMapping
        public ResponseEntity<List<Resource<MenuItem>>> getAllMenuItem(){
            List<Resource<MenuItem>> resources=new ArrayList<>();
            if (resources.isEmpty()) {
                List<MenuItem> menuItems = service.findAll();
                menuItems.forEach(x -> {
                    Resource<MenuItem> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                    resources.add(resource);
                });
            }
                return new ResponseEntity<>(resources, HttpStatus.OK);


        }




        @GetMapping("{id}")
        public ResponseEntity<Resource<MenuItem>> getMenuItemById(@PathVariable long id){
       MenuItem menuItem=service.getMenuItem(id);
       Resource<MenuItem> resource=new Resource<>(menuItem.getId(),OBJECT_TYPE,menuItem);
                return new ResponseEntity<>(resource,HttpStatus.OK);
            }


        @PostMapping
        public ResponseEntity<Resource<Response>> addNewMenuItem(@RequestBody Resource<MenuItem> request){
            MenuItem menuItem=request.getAttribute();
            MenuItem menuItem1= service.createMenuItem(menuItem);
            Response response=new Response(menuItem1.getId(),"create successfully");
            Resource<Response> resource=new Resource<>(menuItem1.getId(),OBJECT_TYPE,response);
            return new ResponseEntity<>(resource,HttpStatus.CREATED);

        }

        @DeleteMapping("{id}")
        public ResponseEntity<Resource<Response>> deleteMenuItemById(@PathVariable long id){
             service.deleteMenuItem(id);
                Response response=new Response(id,"delete successfully");
                Resource<Response> resource=new Resource<>(id,OBJECT_TYPE,response);
                return new ResponseEntity<>(resource,HttpStatus.OK);
        }

        @PutMapping("{id}")
        public ResponseEntity<Resource<Response>> updateMenuItemById(@PathVariable long id,@RequestBody Resource<MenuItem> request){
            MenuItem menuItem=request.getAttribute();
            MenuItem menuItem1= service.updateMenuItem(id,menuItem);
            Response response=new Response(menuItem1.getId(),"update successfully");
                Resource<Response> resource=new Resource<>(menuItem1.getId(),OBJECT_TYPE,response);
                return new ResponseEntity<>(resource,HttpStatus.OK);
}
}
