package com.asa.cafe.serviceImpl;

import com.asa.cafe.constants.CafeConstants;
import com.asa.cafe.dao.UserDao;
import com.asa.cafe.entity.User;
import com.asa.cafe.jwt.CustomerUsersDetailsService;
import com.asa.cafe.jwt.JwtFilter;
import com.asa.cafe.jwt.JwtUtil;
import com.asa.cafe.service.UserService;
import com.asa.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service//actual business logic
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup{}", requestMap);
        try{
            if(validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){//if email cannot find, then save new one
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered!", HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("Email already exists!.", HttpStatus.BAD_REQUEST);
                }
            }
            else{
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

    }



    private boolean validateSignUpMap(Map<String, String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User(
                0,
                requestMap.get("name"),
                requestMap.get("contactNumber"),
                requestMap.get("email"),
                requestMap.get("password"),
                "false",
                "user"
        );
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"), requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(), customerUsersDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(
                            "{\"message\":\"" + "Wait for admin approval!" + "\"}",
                            HttpStatus.BAD_REQUEST
                    );
                }
            }
        }catch (Exception ex){
            log.error("{}", ex);
        }
        return new ResponseEntity<String>(
                "{\"message\":\"" + "Bad Credentials!" + "\"}",
                HttpStatus.BAD_REQUEST
        );
    }
}
