package com.learntoyounus.service;

import com.learntoyounus.entity.Order;
import com.learntoyounus.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
