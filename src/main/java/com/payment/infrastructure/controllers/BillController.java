package com.payment.infrastructure.controllers;

import com.payment.application.BillUseCase;
import com.payment.domain.model.*;
import com.payment.domain.ports.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillUseCase billUseCase;
    private final List<BillService> billServices;

    public BillController(BillUseCase billUseCase, List<BillService> billServices) {
        this.billUseCase = billUseCase;
        this.billServices = billServices;
    }

    @PostMapping("/query")
    public ResponseEntity<?> consultarMonto(@RequestBody BillRequest request) {
        try {
            BillResponse response = billUseCase.queryBill(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/api/callback")
    public ResponseEntity<String> paymentCallback(@RequestParam String reference, @RequestParam String status) {
        // Se usa la primera implementación (puedes cambiar esto si deseas)
        BillService billService = billServices.get(0);

        Optional<BillPayment> paymentOpt = billService.findByReference(reference);
        if (paymentOpt.isPresent()) {
            BillPayment payment = paymentOpt.get();
            payment.setStatus(status.equals("success") ? "COMPLETED" : "FAILED");
            billService.save(payment);
            return ResponseEntity.ok("Estado actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payBill(@RequestBody BillPaymentRequest request) {
        String redirectUrl = billUseCase.payBill(request);
        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam String reference) {
        return billUseCase.findByReference(reference)
                .map(payment -> {
                    payment.setStatus("SUCCESS");
                    payment.setPaymentDate(LocalDateTime.now());
                    billUseCase.save(payment);
                    return ResponseEntity.ok("Pago exitoso.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/failure")
    public ResponseEntity<String> failure(@RequestParam String reference) {
        return billUseCase.findByReference(reference)
                .map(payment -> {
                    payment.setStatus("FAILED");
                    payment.setPaymentDate(LocalDateTime.now());
                    billUseCase.save(payment);
                    return ResponseEntity.ok("Pago fallido.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
