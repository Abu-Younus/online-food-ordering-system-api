package com.learntoyounus.request;

import lombok.Data;

@Data
public class CartItemUpdateRequest {
    private Long cartItemId;
    private int quantity;
}
