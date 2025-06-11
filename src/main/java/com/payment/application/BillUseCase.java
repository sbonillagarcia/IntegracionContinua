package com.payment.application;

import com.payment.domain.model.*;
import com.payment.domain.ports.BillService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillUseCase implements BillService {

    private final BillService billService;

    public BillUseCase(BillService billService) {
        this.billService = billService;
    }

    @Override
    public BillResponse queryBill(BillRequest request) {
        return billService.queryBill(request);
    }

    @Override
    public String payBill(BillPaymentRequest request) {
        return billService.payBill(request);
    }

    @Override
    public Optional<BillPayment> findByReference(String reference) {
        return billService.findByReference(reference);
    }

    @Override
    public void save(BillPayment payment) {
        billService.save(payment);
    }
}
