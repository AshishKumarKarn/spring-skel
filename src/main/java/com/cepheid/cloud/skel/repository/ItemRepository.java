package com.cepheid.cloud.skel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cepheid.cloud.skel.model.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ItemRepository extends JpaRepository<Item, Long> {

}
