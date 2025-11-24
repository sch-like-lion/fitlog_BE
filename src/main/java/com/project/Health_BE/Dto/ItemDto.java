package com.project.Health_BE.Dto;

import com.project.Health_BE.Entity.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ItemDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuyItemRequest {
        private Long itemId;
    }
    @Getter
    @NoArgsConstructor
    public static class ItemRegisterRequest {
        private String name;
        private int price;
        private String imageUrl;
        private ItemCategory category;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ItemDetailResponse {
        private Long itemId;
        private String name;
        private Integer price;
        private String imageUrl;
        private ItemCategory category;
        private boolean isEquipped;
        private boolean isOwned;
    }
}
