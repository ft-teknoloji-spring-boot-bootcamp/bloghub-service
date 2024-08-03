package com.patika.bloghubservice.repository;

import com.patika.bloghubservice.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByTitle(String title);
}
