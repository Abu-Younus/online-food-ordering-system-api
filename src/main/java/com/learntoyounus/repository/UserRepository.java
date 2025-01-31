package com.learntoyounus.repository;

import com.learntoyounus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String userName);
}
