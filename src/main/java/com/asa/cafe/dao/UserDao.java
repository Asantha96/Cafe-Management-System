package com.asa.cafe.dao;

import com.asa.cafe.entity.User;
import com.asa.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
}
