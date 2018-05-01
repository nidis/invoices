package com.nidis.repository;

import com.nidis.model.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InvoicesRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findAll();

    @Query("select * from Invoices where customer_id = :customerId")
    List<Invoice> findByCustomerId(@Param(value = "customerId") Integer customerId, Pageable pageRequest);

    List<Invoice> findByDateRange(Integer customerId, Integer from, Integer to, Pageable pageRequest);

    List<Invoice> findReadInvoices(Integer customerId, boolean read, Pageable pageRequest);

    void save(String fileName, String type, boolean read);

    void delete(Invoice invoice);
}
