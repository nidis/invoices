package com.nidis.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity(name = "invoices")
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, updatable = false)
    private Long invoiceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    //@Column(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @Column(name = "file_name")
    private String filePath;

    @Column(name = "type")
    private String invoiceType;

    @Column(name = "read")
    private boolean read;

    @Column(name = "time_read")
    private Timestamp timeRead;
}
