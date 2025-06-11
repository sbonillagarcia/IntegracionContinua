package com.payment.domain.ports;

import com.payment.domain.model.*;

import java.util.Optional;

public interface BillService {
    BillResponse queryBill(BillRequest request);
    String payBill(BillPaymentRequest request);
    Optional<BillPayment> findByReference(String reference);
    void save(BillPayment payment);
}

