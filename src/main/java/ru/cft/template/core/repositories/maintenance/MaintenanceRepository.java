package ru.cft.template.core.repositories.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.enums.MaintenanceType;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.models.Maintenance;
import ru.cft.template.core.models.User;

import java.util.List;
import java.util.UUID;

public interface MaintenanceRepository  extends JpaRepository<Maintenance, UUID> {
    List<Maintenance> findByUserAndType(User user, MaintenanceType type);
    List<Maintenance> findByUser(User user);


}
