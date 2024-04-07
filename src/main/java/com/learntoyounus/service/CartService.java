package com.learntoyounus.service;

import com.learntoyounus.entity.Cart;
import com.learntoyounus.entity.CartItem;
import com.learntoyounus.request.CartItemRequest;

public interface CartService {
    public CartItem addItemToCart(CartItemRequest cartItemRequest, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;
}
