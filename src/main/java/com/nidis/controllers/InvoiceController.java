package com.nidis.controllers;

import com.nidis.assemblers.InvoiceAssembler;
import com.nidis.models.Invoice;
import com.nidis.resources.InvoiceResource;
import com.nidis.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ExposesResourceFor(InvoiceResource.class)
@RequestMapping(value = "/invoices", produces = MediaTypes.HAL_JSON_VALUE)
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceAssembler invoiceAssembler;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, InvoiceAssembler invoiceAssembler) {
        this.invoiceService = invoiceService;
        this.invoiceAssembler = invoiceAssembler;
    }

    @GetMapping
    public ResponseEntity<Resources<InvoiceResource>> findInvoices() {
        final Iterable<Invoice> invoices = invoiceService.findAll();
        final Resources<InvoiceResource> wrapped = invoiceAssembler.toEmbeddedList(invoices);

        return ResponseEntity.ok(wrapped);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResource> findInvoice(@PathVariable Long invoiceId,
                                                       @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        final Invoice invoice = invoiceService.findInvoice(invoiceId, page, size);
        final InvoiceResource resource = invoiceAssembler.toResource(invoice);

        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<InvoiceResource>> findInvoicesOfCustomer(@PathVariable Long customerId,
                                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        final Iterable<Invoice> invoices = invoiceService.findInvoicesOfCustomer(customerId, page, size);
        final Resources<InvoiceResource> wrapped = invoiceAssembler.toEmbeddedList(invoices);

        return ResponseEntity.ok(wrapped);
    }

}
