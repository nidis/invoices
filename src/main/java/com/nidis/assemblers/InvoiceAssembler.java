package com.nidis.assemblers;

import com.nidis.controllers.InvoiceController;
import com.nidis.models.Invoice;
import com.nidis.resources.InvoiceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class InvoiceAssembler extends EmbeddableResourceAssemblerSupport<Invoice, InvoiceResource, InvoiceController> {

    @Autowired
    public InvoiceAssembler(final EntityLinks entityLinks,
                            RelProvider relProvider) {
        super(entityLinks, relProvider, InvoiceController.class, InvoiceResource.class);
    }

    @Override
    protected InvoiceResource instantiateResource(Invoice entity) {
        return new InvoiceResource(entity.getInvoiceId(), entity.getCustomer().getId(), entity.getInvoiceType(), entity.isRead());
    }

    @Override
    public Link linkToSingleResource(Invoice invoice) {
        return entityLinks.linkToSingleResource(InvoiceResource.class, invoice.getCustomer().getId());
    }

    @Override
    public InvoiceResource toResource(Invoice entity) {
        final InvoiceResource resource = toBaseResource(entity);

        addActionLinks(resource, entity);
//        final String customerRel = relProvider.getCollectionResourceRelFor((CustomerResource.class));

        return resource;
    }

    private InvoiceResource toBaseResource(Invoice entity) {
        return createResourceWithId(entity.getInvoiceId(), entity);
    }

    private void addActionLinks(final InvoiceResource resource, final Invoice entity) {
        final Link invoiceLink = linkTo(methodOn(controllerClass).findInvoice(entity.getInvoiceId())).withRel("invoice");
        resource.add(invoiceLink);
    }
}
