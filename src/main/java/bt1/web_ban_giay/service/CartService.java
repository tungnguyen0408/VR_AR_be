package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.response.CartDTO;
import bt1.web_ban_giay.dto.response.CartItemDTO;
import bt1.web_ban_giay.dto.response.ProductDTO;
import bt1.web_ban_giay.dto.response.ProductVariantDTO;
import bt1.web_ban_giay.entity.Cart;
import bt1.web_ban_giay.entity.CartItem;
import bt1.web_ban_giay.entity.Product;
import bt1.web_ban_giay.entity.ProductVariant;
import bt1.web_ban_giay.entity.User;
import bt1.web_ban_giay.exception.InvalidException;
import bt1.web_ban_giay.repository.CartItemRepository;
import bt1.web_ban_giay.repository.CartRepository;
import bt1.web_ban_giay.repository.ProductRepository;
import bt1.web_ban_giay.repository.ProductVariantRepository;
import bt1.web_ban_giay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private UserRepository userRepository;

    public CartDTO getCart(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user.get());
                    return cartRepository.save(newCart);
                });

        return toDTO(cart);
    }

    public int getCartItemCount(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }
        return cartItemRepository.countByCartUserId(userId);
    }

    @Transactional
    public CartDTO addToCart(Long userId, Long productId, Long variantId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy người dùng"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm"));

        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy biến thể sản phẩm"));

        // Get or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Check if item already exists in cart
        CartItem existingItem = cartItemRepository.findByCartIdAndProductIdAndProductVariantId(
                        cart.getId(), productId, variantId)
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setProductVariant(variant);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }

        return toDTO(cart);
    }


    @Transactional
    public void removeFromCart(Long cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (!cartItem.isPresent()) {
            throw new InvalidException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public CartDTO updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new InvalidException("Không tìm thấy sản phẩm trong giỏ hàng"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return toDTO(cartItem.getCart());
    }

    @Transactional
    public void clearCart(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }

        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            cartItemRepository.deleteByCartId(cart.get().getId());
        }
    }

    private CartDTO toDTO(Cart cart) {
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList());

        return new CartDTO(
                cart.getId(),
                cart.getUser().getId(),
                itemDTOs,
                cart.getCreatedAt(),
                cart.getUpdatedAt());
    }

    private CartItemDTO toCartItemDTO(CartItem item) {
        return CartItemDTO.builder()
                .id(item.getId())
                .cartId(item.getCart().getId())
                .product(toProductDTO(item.getProduct()))
                .productVariant(toProductVariantDTO(item.getProductVariant()))
                .quantity(item.getQuantity())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setBrand(product.getBrand());
        dto.setMaterial(product.getMaterial());
        dto.setStatus(product.getStatus() != null ? product.getStatus() : null);
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            dto.setImageUrls(product.getImages().stream()
                    .map(image -> image.getImageUrl())
                    .collect(Collectors.toList()));
        }

        return dto;
    }


    private ProductVariantDTO toProductVariantDTO(ProductVariant variant) {
        Long productId = (variant.getProduct() != null) ? variant.getProduct().getId() : null;

        return new ProductVariantDTO(
                variant.getId(),
                productId,
                variant.getSize(),
                variant.getSku(),
                variant.getColor(),
                variant.getStockQuantity(),
                variant.getCreatedAt(),
                variant.getUpdatedAt());
    }


}
