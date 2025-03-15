package com.urantech.restapi.entity.outbox;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outbox_event")
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_id")
    private Long aggregateId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "payload")
    private String payload;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OutboxEventStatus status;

    public enum OutboxEventStatus {
        PENDING,
        SENT,
        FAILED
    }
}
