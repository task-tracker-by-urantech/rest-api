package com.urantech.restapi.repository.outbox;

import com.urantech.restapi.entity.outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByStatus(OutboxEvent.OutboxEventStatus status);
}
