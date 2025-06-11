package com.payment.domain.model;

import java.math.BigDecimal;

public class BillResponse {
    private int companyId;
    private String referenceNumber;
    private BigDecimal amount;

    public BillResponse(int companyId, String referenceNumber, BigDecimal amount) {
        this.companyId = companyId;
        this.referenceNumber = referenceNumber;
        this.amount = amount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
