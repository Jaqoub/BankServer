package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TransferQueue;

public interface ITransactionsQueue extends JpaRepository<TransferQueue, Long> {
    List<TransferQueue> findByDateBeforeOrDate(LocalDate before, LocalDate now);
    List<TransferQueue> findByDate(LocalDate date);
}
