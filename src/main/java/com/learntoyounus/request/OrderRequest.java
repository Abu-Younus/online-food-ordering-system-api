package com.learntoyounus.request;

import com.learntoyounus.entity.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
