package com.nidis.controllers;

import com.nidis.assemblers.InvoiceAssembler;
import com.nidis.models.Invoice;
import com.nidis.resources.InvoiceResource;
import com.nidis.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public ResponseEntity<InvoiceResource> findInvoice(@PathVariable Long invoiceId) {

        final Invoice invoice = invoiceService.findInvoice(invoiceId);
        final InvoiceResource resource = invoiceAssembler.toResource(invoice);

        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/{invoiceId}/view", produces = "application/pdf")
    public ResponseEntity<byte[]> viewInvoice(@PathVariable Long invoiceId) throws IOException {
        final Invoice invoice = invoiceService.findInvoice(invoiceId);

        byte[] pdf = convertToBytes(invoice.getFilePath());
        return new ResponseEntity<>(pdf,  HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<InvoiceResource>> findInvoicesOfCustomer(@PathVariable Long customerId,
                                                                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                             @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        final Iterable<Invoice> invoices = invoiceService.findInvoicesOfCustomer(customerId, page, size);
        final Resources<InvoiceResource> wrapped = invoiceAssembler.toEmbeddedList(invoices);

        return ResponseEntity.ok(wrapped);
    }


    @PostMapping(value = "/customer/{customerId}")
    public ResponseEntity<Resources<InvoiceResource>> createInvoice(@PathVariable Long customerId, @RequestBody Invoice invoice) {

        return null;
    }


    private byte[] convertToBytes(String dir) throws IOException {
        Path path = Paths.get(dir);

        return Files.readAllBytes(path);
    }

}
