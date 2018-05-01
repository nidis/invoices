package com.nidis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, updatable = false)
    private long id;

    @Column(name = "file_name")
    private String filePath;

    @Column(name = "invoice_type")
    private String invoiceType;

    @Column(name = "read")
    private boolean read;

    @Column(name = "time_read")
    private Timestamp timeRead;
}
