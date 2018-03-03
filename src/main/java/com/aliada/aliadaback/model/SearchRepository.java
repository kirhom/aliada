package com.aliada.aliadaback.model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("SELECT s FROM Search s WHERE s.creator = ?1 AND s.status = ?2")
    public Set<Search> findByStatusAndUser(User user, int status);
}