package com.nidis.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.core.Relation;

@Relation(value = "invoice", collectionRelation = "invoices")
@Getter
public class InvoiceResource extends ResourceWithEmbeddeds {
    private final Long invoiceId;
    private final Long customerId;
    private final String invoiceType;
    private final boolean read;

    @JsonCreator
    public InvoiceResource(@JsonProperty("invoiceId") Long invoiceId,
                           @JsonProperty("customerId") Long customerId,
                           @JsonProperty("invoiceType") String invoiceType,
                           @JsonProperty("read") boolean read) {
        super();
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.invoiceType = invoiceType;
        this.read = read;
    }
}
