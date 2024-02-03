package ru.cft.template.core.repositories.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.core.enums.TransactionType;
import ru.cft.template.core.models.Bill;
import ru.cft.template.core.models.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByUser(User user);
    List<Bill> findByUserAndType(User user, TransactionType type);
}
