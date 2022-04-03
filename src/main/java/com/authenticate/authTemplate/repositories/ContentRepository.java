package com.authenticate.authTemplate.repositories;

import com.authenticate.authTemplate.models.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
