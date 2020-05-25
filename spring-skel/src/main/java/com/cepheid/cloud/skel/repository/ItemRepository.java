package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cepheid.cloud.skel.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.name = :name")
    Item findItemByName(@Param("name") String name);

    @Query("SELECT i FROM Item i WHERE i.state = :state")
    List<Item> findItemsByState(@Param("state") State state);

}
