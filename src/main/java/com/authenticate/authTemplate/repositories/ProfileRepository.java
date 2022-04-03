package com.authenticate.authTemplate.repositories;

import com.authenticate.authTemplate.auth.User;
import com.authenticate.authTemplate.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);

}
