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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
    public ResponseEntity<InvoiceResource> findInvoice(@PathVariable Long invoiceId) {

        final Invoice invoice = invoiceService.findInvoice(invoiceId);
        final InvoiceResource resource = invoiceAssembler.toResource(invoice);

        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/{invoiceId}/view", produces = "application/pdf")
    public ResponseEntity<byte[]> viewInvoice(@PathVariable Long invoiceId) throws IOException{
        //https://stackoverflow.com/questions/34676037/how-to-return-binary-data-instead-of-base64-encoded-byte-in-spring-mvc-rest-co

        final Invoice invoice = invoiceService.findInvoice(invoiceId);

        byte[] pdf  = convertToBytes(getPDF(invoice.getFilePath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "pdf"));
        headers.setContentDispositionFormData("attachment", "x.pdf");
        headers.setContentLength(pdf.length);

        //final InvoiceResource resource = invoiceAssembler.toResource(invoice);

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<InvoiceResource>> findInvoicesOfCustomer(@PathVariable Long customerId,
                                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        final Iterable<Invoice> invoices = invoiceService.findInvoicesOfCustomer(customerId, page, size);
        final Resources<InvoiceResource> wrapped = invoiceAssembler.toEmbeddedList(invoices);

        return ResponseEntity.ok(wrapped);
    }

    private byte getPDF() {

    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

}
