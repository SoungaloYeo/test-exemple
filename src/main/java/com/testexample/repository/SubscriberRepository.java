package com.testexample.repository;

import com.testexample.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
