package com.farmer.labour.collabaration.farmerlabour.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmer.labour.collabaration.farmerlabour.model.FarmerLabourUser;
import com.farmer.labour.collabaration.farmerlabour.service.UserServiceInf;
import com.farmer.labour.collabaration.farmerlabour.utility.JwtUtil;
import  com.farmer.labour.collabaration.farmerlabour.model.UserRequest;
import  com.farmer.labour.collabaration.farmerlabour.model.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserServiceInf userServiceInf;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
	private JwtUtil jwtUtil;
    




    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody FarmerLabourUser user){
        Integer userId= userServiceInf.save(user);
        String body="user : "+userId+" saved !!";
        return ResponseEntity.ok(body);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest){
        // for this authentication amangeer already we told to get userr from data base ( loaduserby username)
        // compare passed user details with security userr details
        //in security config class we told this info
        System.out.println("user name is: "+userRequest.getUsername());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    userRequest.getUsername(), 
                    userRequest.getPassword()
                    )
            );
    String token=jwtUtil.generateToken(userRequest.getUsername());
    return ResponseEntity.ok(new UserResponse(token,"GENERATED BY Maldanna"));
    }


    @GetMapping("/welcome")
	public ResponseEntity<String> accessUserData() {
		return ResponseEntity.ok("Hello user");
	}
    
}
