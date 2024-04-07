package com.learntoyounus.controller;

import com.learntoyounus.entity.Cart;
import com.learntoyounus.entity.CartItem;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.CartItemRequest;
import com.learntoyounus.request.CartItemUpdateRequest;
import com.learntoyounus.service.CartService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody CartItemRequest cartItemRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem cartItem = cartService.addItemToCart(cartItemRequest, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(cartItemUpdateRequest.getCartItemId(), cartItemUpdateRequest.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> cartClear(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findCartByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
