package com.payment.infrastructure.adapters;

import com.payment.domain.model.*;
import com.payment.domain.ports.BillService;
import com.payment.infrastructure.repositories.BillPaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillServiceImpl implements BillService {

    private final BillPaymentRepository paymentRepository;

    public BillServiceImpl(BillPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public BillResponse queryBill(BillRequest request) {
        Optional<BillPayment> optionalPayment = paymentRepository
                .findByCompanyIdAndReferenceNumber(request.getCompanyId(), request.getReferenceNumber());

        if (optionalPayment.isPresent()) {
            BillPayment payment = optionalPayment.get();
            return new BillResponse(payment.getCompanyId(), payment.getReferenceNumber(), payment.getAmount());
        } else {
            throw new RuntimeException("Factura no encontrada para esa empresa y referencia.");
        }
    }

    @Override
    public String payBill(BillPaymentRequest request) {
        BillPayment payment = new BillPayment();
        payment.setUserId(request.getUserId());
        payment.setCompanyId(request.getCompanyId());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setAmount(request.getAmount());
        payment.setStatus("PAID");
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);
        return "Payment registered successfully";
    }

    @Override
    public Optional<BillPayment> findByReference(String reference) {
        return paymentRepository.findByReferenceNumber(reference);
    }

    @Override
    public void save(BillPayment payment) {
        paymentRepository.save(payment);
    }
}
