package com.learntoyounus.service;

import com.learntoyounus.entity.Cart;
import com.learntoyounus.entity.CartItem;
import com.learntoyounus.entity.Food;
import com.learntoyounus.entity.User;
import com.learntoyounus.repository.CartItemRepository;
import com.learntoyounus.repository.CartRepository;
import com.learntoyounus.request.CartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(CartItemRequest cartItemRequest, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(cartItemRequest.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItem()) {
            if (cartItem.getFood().equals(food)) {
                int updateQuantity = cartItem.getQuantity() + cartItemRequest.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), updateQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(cartItemRequest.getQuantity());
        newCartItem.setIngredients(cartItemRequest.getIngredients());
        newCartItem.setTotalPrice(cartItemRequest.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found!");
        }

        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found!");
        }

        CartItem cartItem = cartItemOptional.get();
        cart.getItem().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItem()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found with id " + id);
        }
        return cartOptional.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));

        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
