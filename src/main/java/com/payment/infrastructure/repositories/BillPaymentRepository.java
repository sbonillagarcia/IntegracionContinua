package com.payment.infrastructure.repositories;

import com.payment.domain.model.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
    Optional<BillPayment> findByCompanyIdAndReferenceNumber(int companyId, String referenceNumber);

    Optional<BillPayment> findByReferenceNumber(String referenceNumber);

}
