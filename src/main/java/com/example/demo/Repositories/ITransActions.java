package com.example.demo.Repositories;

import com.example.demo.Model.TransActions;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ITransActions extends CrudRepository<TransActions, Long> {
    List<TransActions> findByDateBeforeOrDate(LocalDate now, LocalDate now1);
}
