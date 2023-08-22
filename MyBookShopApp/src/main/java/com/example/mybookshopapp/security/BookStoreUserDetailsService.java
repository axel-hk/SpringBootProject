package com.example.mybookshopapp.security;

import com.example.mybookshopapp.data.repositories.UsersRepository;
import com.example.mybookshopapp.struct.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookStoreUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findUserEntityByContact_Email(email);
        if (userEntity != null) {
            return new BookStoreUserDetails(userEntity);
        }
        else {
            throw new UsernameNotFoundException("User not found by email: " + email);
        }
    }
}
