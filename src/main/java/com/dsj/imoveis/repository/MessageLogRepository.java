package com.dsj.imoveis.repository;

import com.dsj.imoveis.lib.entities.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
    @Query("SELECT COUNT(m) > 0 FROM MessageLog m WHERE m.phoneNumber = :phoneNumber AND m.sentAt >= :startOfDay")
    boolean hasMessageBeenSentToday(String phoneNumber, LocalDateTime startOfDay);

}
