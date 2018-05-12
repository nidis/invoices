package com.nidis.controllers;

import com.nidis.assemblers.InvoiceAssembler;
import com.nidis.models.Invoice;
//import com.nidis.services.InvoiceService;
import com.nidis.resources.InvoiceResource;
import com.nidis.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ExposesResourceFor(InvoiceResource.class)
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceAssembler invoiceAssembler;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, InvoiceAssembler invoiceAssembler) {
        this.invoiceService = invoiceService;
        this.invoiceAssembler = invoiceAssembler;
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResource> findInvoice(@PathVariable Long invoiceId,
                                                       @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        final Invoice invoice = invoiceService.findInvoice(invoiceId, page, size);
        final InvoiceResource resource = invoiceAssembler.toResource(invoice);

        return ResponseEntity.ok(resource);
    }


    @RequestMapping(value = "/invoices/{customerId}", method = RequestMethod.GET)
    public  Page<Invoice> findInvoicesOfCustomer(@PathVariable Long customerId,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return  invoiceService.findInvoicesOfCustomer(customerId, page, size);
    }

}
