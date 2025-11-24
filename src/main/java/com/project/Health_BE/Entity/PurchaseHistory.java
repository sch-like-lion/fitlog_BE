package com.project.Health_BE.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int purchasePrice; // 구매 당시 가격 박제
    private LocalDateTime purchaseDate;

    @Builder
    public PurchaseHistory(UserEntity user, Item item, int purchasePrice) {
        this.user = user;
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = LocalDateTime.now();
    }
}