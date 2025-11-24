package com.project.Health_BE.Service;

import com.project.Health_BE.Dto.ItemDto;
import com.project.Health_BE.Entity.*;
import com.project.Health_BE.Entity.ItemCategory;
import com.project.Health_BE.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository; // 변수명 소문자 확인
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    // 1. [관리자용] 아이템 등록
    public void registerItem(ItemDto.ItemRegisterRequest request) { // DTO 이름 수정됨
        Item item = Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .build();
        itemRepository.save(item);
    }

    // 2. [유저용] 상점 아이템 목록 조회 (내가 가진 건지 표시)
    @Transactional(readOnly = true)
    public List<ItemDto.ItemDetailResponse> getShopItems(Long userId) { // DTO 이름 수정됨
        List<Item> allItems = itemRepository.findAll();

        // 내가 가진 아이템 ID 목록 조회
        List<Long> myItemIds = userItemRepository.findAllByUser_UserId(userId).stream()
                .map(userItem -> userItem.getItem().getItemId())
                .toList();

        return allItems.stream()
                .map(item -> ItemDto.ItemDetailResponse.builder()
                        .itemId(item.getItemId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .imageUrl(item.getImageUrl())
                        .category(item.getCategory())
                        .isOwned(myItemIds.contains(item.getItemId()))
                        .isEquipped(false)
                        .build())
                .collect(Collectors.toList());
    }

    // 3. [유저용] 내 인벤토리 조회
    @Transactional(readOnly = true)
    public List<ItemDto.ItemDetailResponse> getMyInventory(Long userId) {
        return userItemRepository.findAllByUser_UserId(userId).stream()
                .map(userItem -> ItemDto.ItemDetailResponse.builder()
                        .itemId(userItem.getItem().getItemId())
                        .name(userItem.getItem().getName())
                        .price(userItem.getItem().getPrice())
                        .imageUrl(userItem.getItem().getImageUrl())
                        .category(userItem.getItem().getCategory())
                        .isOwned(true)
                        .isEquipped(userItem.isEquipped())
                        .build())
                .collect(Collectors.toList());
    }

    // 4. [유저용] 아이템 구매
    public void buyItem(Long userId, Long itemId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        // 이미 보유 중인지 확인
        if (userItemRepository.findByUser_UserIdAndItem_ItemId(userId, itemId).isPresent()) {
            throw new IllegalStateException("이미 보유하고 있는 아이템입니다.");
        }

        // 포인트 부족 확인
        if (user.getTotalPoint() < item.getPrice()) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        // 결제 진행 (포인트 차감)
        user.deductPoints(item.getPrice());

        // 인벤토리 추가 (UserItem 생성)
        UserItem userItem = UserItem.builder()
                .user(user)
                .item(item)
                .build();
        userItemRepository.save(userItem);

        // 구매 내역 저장
        PurchaseHistory history = PurchaseHistory.builder()
                .user(user)
                .item(item)
                .purchasePrice(item.getPrice())
                .build();
        purchaseHistoryRepository.save(history);
    }

    // 5. [유저용] 아이템 착용
    public void equipItem(Long userId, Long itemId) {
        UserItem targetItem = userItemRepository.findByUser_UserIdAndItem_ItemId(userId, itemId)
                .orElseThrow(() -> new IllegalArgumentException("보유하지 않은 아이템입니다."));

        // 같은 카테고리의 이미 착용 중인 아이템 해제
        ItemCategory category = targetItem.getItem().getCategory();
        userItemRepository.findByUser_UserIdAndItem_CategoryAndIsEquippedTrue(userId, category)
                .ifPresent(UserItem::unequip);

        // 새 아이템 착용
        targetItem.equip();
    }

    // 6. [유저용] 아이템 해제 (벗기)
    public void unequipItem(Long userId, Long itemId) {
        UserItem targetItem = userItemRepository.findByUser_UserIdAndItem_ItemId(userId, itemId)
                .orElseThrow(() -> new IllegalArgumentException("보유하지 않은 아이템입니다."));
        targetItem.unequip();
    }
}