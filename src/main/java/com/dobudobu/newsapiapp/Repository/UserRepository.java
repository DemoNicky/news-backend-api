package com.dobudobu.newsapiapp.Repository;

import com.dobudobu.newsapiapp.Entity.Enum.Role;
import com.dobudobu.newsapiapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String username);

    User findByRole(Role role);

}
