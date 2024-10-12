package com.encora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Es un mapping para nuestro request. Se routea lo que pido.
public interface ToDoApp {
    //Tendremos nuestros metodos controller

    //Get: si lo que necesitams es un string por ejemplo usamos response entity.*endpoint*
    //@RequestMapping(value = "/banner-favorites/{position}",    produces = { "application/json" },     method = RequestMethod.DELETE)
    @RequestMapping (value = "/toDoTest", produces = {"application/json"}, method = RequestMethod.GET)
     ResponseEntity<String> getString();
    //Post
    @RequestMapping (value = "/ejemploTestPost", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> postString();
    //Put

    // Delete
}
