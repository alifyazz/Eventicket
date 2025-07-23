package com.eventticket.repository;

import com.eventticket.model.Booking;
import com.eventticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerEmail(String email);
    List<Booking> findByEventId(Long eventId);
    List<Booking> findByStatus(String status);
    List<Booking> findByUser(User user);
    List<Booking> findByUserAndStatus(User user, String status);
    Booking findByQrCode(String qrCode);
} 