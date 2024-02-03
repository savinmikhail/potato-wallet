package ru.cft.template.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column
    private String token;

    @Column(nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date expirationTime;

    @Column(name = "active")
    private Boolean active;
}
