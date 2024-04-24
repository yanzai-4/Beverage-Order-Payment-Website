
package com.example.springpaymentsapi.repository;

import com.example.springpaymentsapi.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<AuthRequest, Long> {

}


