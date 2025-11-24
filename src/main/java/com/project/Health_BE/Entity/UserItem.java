package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_items")
public class UserItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "is_equipped")
    private boolean isEquipped;

    @Builder
    public UserItem(UserEntity user, Item item) {
        this.user = user;
        this.item = item;
        this.isEquipped = false;
    }

    public void equip() { this.isEquipped = true; }
    public void unequip() { this.isEquipped = false; }
}