package com.camilo.database.repositories;

import com.camilo.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{
}
