package com.nidis.repositories;

import com.nidis.models.Customer;
import com.nidis.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAll();

/*    @Query(value = "select * from invoices where invoice_id = :invoiceId ORDER BY ?#{#pageable}",
            //countQuery = "select count(*) from invoices where invoice_id = :invoiceId",
            nativeQuery = true)*/
    Invoice findByInvoiceId(/*@Param(value = "invoiceId")*/ Long invoiceId);

    //@Query("select invoiceId from invoices where customerId = :customerId")
    Page<Invoice> findByCustomerId(@Param(value = "customerId") Long customerId, Pageable pageRequest);

  /*  Page<Invoice> findByDateRange(Integer customerId, Integer from, Integer to, Pageable pageRequest);

    Page<Invoice> findReadInvoices(Integer customerId, boolean read, Pageable pageRequest);

    void save(String fileName, String type, boolean read);

    void delete(Invoice invoice);*/
}
