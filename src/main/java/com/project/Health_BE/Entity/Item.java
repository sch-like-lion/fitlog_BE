package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, unique = true)
    private Long itemId;

    private String name;
    private Integer price;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Builder
    public Item(String name, int price, String imageUrl, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
