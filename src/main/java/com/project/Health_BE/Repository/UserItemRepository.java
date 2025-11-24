package com.project.Health_BE.Repository;

import com.project.Health_BE.Entity.Item;
import com.project.Health_BE.Entity.ItemCategory;
import com.project.Health_BE.Entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 2. 유저 아이템 리포지토리
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    Optional<UserItem> findByUser_UserIdAndItem_ItemId(Long userId, Long itemId);

    Optional<UserItem> findByUser_UserIdAndItem_CategoryAndIsEquippedTrue(Long userId, ItemCategory category);

    List<UserItem> findAllByUser_UserId(Long userId);
}