package bsep.tim9.controllers;

import bsep.tim9.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @GetMapping()
    public ResponseEntity<Object> getUser(){
	    try{
	    	User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(u, HttpStatus.OK);
		}catch(ClassCastException e){
	    	return new ResponseEntity<>("No User Logged In!", HttpStatus.BAD_REQUEST);
		}

    }
}