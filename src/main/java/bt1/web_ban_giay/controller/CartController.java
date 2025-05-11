package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.AddToCartRequest;
import bt1.web_ban_giay.dto.response.CartDTO;
import bt1.web_ban_giay.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable @NotNull Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable @NotNull Long userId) {
        return ResponseEntity.ok(cartService.getCartItemCount(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getVariantId(),
                request.getQuantity()));
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable @NotNull Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/item/{cartItemId}/quantity")
    public ResponseEntity<CartDTO> updateCartItemQuantity(
            @PathVariable @NotNull Long cartItemId,
            @RequestParam @NotNull @Min(1) int quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable @NotNull Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
