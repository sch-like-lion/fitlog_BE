package com.project.Health_BE.Controller;

import com.project.Health_BE.Dto.ItemDto;
import com.project.Health_BE.Entity.UserEntity;
import com.project.Health_BE.Security.JwtTokenProvider;
import com.project.Health_BE.Service.ShopService;
import com.project.Health_BE.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/items")
    public ResponseEntity<String> registerItem(@RequestBody ItemDto.ItemRegisterRequest request) {
        shopService.registerItem(request);
        return ResponseEntity.ok("아이템 등록 완료");
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDto.ItemDetailResponse>> getShopItems(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtTokenProvider.getUserId(authorization);
        return ResponseEntity.ok(shopService.getShopItems(userId));
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<ItemDto.ItemDetailResponse>> getMyInventory(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtTokenProvider.getUserId(authorization);
        return ResponseEntity.ok(shopService.getMyInventory(userId));
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyItem(@RequestHeader("Authorization") String authorization,
                                          @RequestBody ItemDto.BuyItemRequest request) {
        Long userId = jwtTokenProvider.getUserId(authorization);
        shopService.buyItem(userId, request.getItemId());
        return ResponseEntity.ok("구매 성공!");
    }

    @PostMapping("/equip/{itemId}")
    public ResponseEntity<String> equipItem(@RequestHeader("Authorization") String authorization,
                                            @PathVariable Long itemId) {
        Long userId = jwtTokenProvider.getUserId(authorization);
        shopService.equipItem(userId, itemId);
        return ResponseEntity.ok("착용 완료!");
    }

    @PostMapping("/unequip/{itemId}")
    public ResponseEntity<String> unequipItem(@RequestHeader("Authorization") String authorization,
                                              @PathVariable Long itemId) {
        Long userId = jwtTokenProvider.getUserId(authorization);
        shopService.unequipItem(userId, itemId);
        return ResponseEntity.ok("해제 완료!");
    }
}