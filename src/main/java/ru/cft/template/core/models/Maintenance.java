package ru.cft.template.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.core.enums.MaintenanceStatus;
import ru.cft.template.core.enums.MaintenanceType;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "maintenances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @Enumerated(EnumType.STRING)
    private MaintenanceType type;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long maintenanceNumber;

    @Column(nullable = false)
    private Long payerPhone;

    @Column(nullable = true)
    private String comment;

    @Column(nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
}
