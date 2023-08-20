package com.example.mybookshopapp.data.repositories;

import com.example.mybookshopapp.struct.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUserByName(String username);
    UserEntity findUserEntityByContact_Email(String email);
}
