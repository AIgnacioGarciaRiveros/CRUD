package com.example.RestService.repository;

import com.example.RestService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Modifying
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
    void modify(@Param("id") Integer id, @Param("name") String name);
}
