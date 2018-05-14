package com.nidis.services;

import com.nidis.models.Invoice;
import com.nidis.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice findInvoice(Long invoiceId) {
        return invoiceRepository.findByInvoiceId(invoiceId);
    }

    public Page<Invoice> findInvoicesOfCustomer(Long customerId, int page, int size) {
        final Pageable pageable = new PageRequest(page, size);

        return invoiceRepository.findByCustomerId(customerId, pageable);
    }
}
