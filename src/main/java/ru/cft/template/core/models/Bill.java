package ru.cft.template.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cft.template.core.enums.TransactionType;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "bills")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Long amount;//pennies

    @Column(nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date transactionDate = new Date();

    @Enumerated(EnumType.STRING)
    private TransactionType type;//"transfer/payment"

    @Column(nullable = true, name = "receiver_phone")
    //todo how to use optional here and dont get hybernate error?
    private Long receiverPhone;// optional. Used for type = transfer

    @Column(nullable = true, name = "maintenance_number")
    private Long maintenanceNumber;// optional. Used for type = payment,

    @Column
    private String status;//"successful"
}
