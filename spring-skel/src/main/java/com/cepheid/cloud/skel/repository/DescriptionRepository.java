package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Query("SELECT d FROM Description d WHERE d.item = :id")
    Set<Description> findByItemId(Long id);
}
