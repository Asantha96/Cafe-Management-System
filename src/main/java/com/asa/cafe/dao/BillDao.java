package com.asa.cafe.dao;

import com.asa.cafe.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill, Integer> {

}
