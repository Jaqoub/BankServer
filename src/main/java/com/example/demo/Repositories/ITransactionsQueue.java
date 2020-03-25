package com.example.demo.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.TransActionsQueue;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TransferQueue;
import java.util.Optional;

public interface ITransactionsQueue extends JpaRepository<TransActionsQueue, Long> {
    List<TransActionsQueue> findByDateBeforeOrDate(LocalDate before,LocalDate now);
    List<TransActionsQueue> findByDate(LocalDate date);
}
