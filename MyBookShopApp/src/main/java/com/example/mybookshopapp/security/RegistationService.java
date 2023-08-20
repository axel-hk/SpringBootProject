package com.example.mybookshopapp.security;

import com.example.mybookshopapp.data.repositories.UsersRepository;
import com.example.mybookshopapp.struct.user.UserContactEntity;
import com.example.mybookshopapp.struct.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void regNewUser(RegisterForm registerForm) {
        if(usersRepository.findUserEntityByContact_Email(registerForm.getEmail()) == null) {
            UserEntity user = new UserEntity();
            UserContactEntity contact = new UserContactEntity();
            user.setName(registerForm.getName());
            user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
            contact.setEmail(registerForm.getEmail());
            contact.setPhone(registerForm.getPhone());
            user.setContact(contact);
            user.setRegTime(LocalDateTime.now());
            contact.setUser(user);
            usersRepository.save(user);

        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                payload.getContact(), payload.getCode()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }
}
