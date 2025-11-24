package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Item;
import com.project.Health_BE.Entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategory(ItemCategory category);
}