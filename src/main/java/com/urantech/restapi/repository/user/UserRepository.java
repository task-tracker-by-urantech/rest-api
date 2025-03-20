package com.urantech.restapi.repository.user;

import com.urantech.restapi.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u join fetch u.authorities where u.email = :email")
    Optional<User> findByEmailWithAuthorities(@Param("email") String email);
}
