package com.nidis.services;

import com.nidis.models.Invoice;
import com.nidis.repositories.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice findInvoice(Long invoiceId, int page, int size) {
        final Pageable pageable = new PageRequest(page, size);

        return invoiceRepository.findByInvoiceId(invoiceId);
    }

    public Page<Invoice> findInvoicesOfCustomer(Long customerId, int page, int size) {
        final Pageable pageable = new PageRequest(page, size);

        return invoiceRepository.findInvoicesByCustomerId(customerId, pageable);
    }
}
