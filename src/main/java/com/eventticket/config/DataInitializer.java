package com.eventticket.config;

import com.eventticket.model.Event;
import com.eventticket.model.User;
import com.eventticket.model.Booking;
import com.eventticket.repository.EventRepository;
import com.eventticket.repository.UserRepository;
import com.eventticket.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Bersihkan data lama
        bookingRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();

        // User
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        userRepository.save(admin);

        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword(passwordEncoder.encode("password123"));
        user1.setRole("USER");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("jane_smith");
        user2.setPassword(passwordEncoder.encode("password123"));
        user2.setRole("USER");
        userRepository.save(user2);

        // Event
        Event event1 = new Event();
        event1.setTitle("Tech Conference 2024");
        event1.setDescription("Konferensi teknologi terbesar di Indonesia");
        event1.setEventDate(LocalDateTime.now().plusDays(30));
        event1.setVenue("Jakarta Convention Center");
        event1.setTicketPrice(new BigDecimal("500000"));
        event1.setAvailableTickets(100);
        event1.setCategory("Conference");
        event1.setImageUrl("https://example.com/tech-conf.jpg");
        event1.setPromoLabel("Early Bird 20%");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setTitle("Web Development Workshop");
        event2.setDescription("Workshop web development dari dasar hingga advanced");
        event2.setEventDate(LocalDateTime.now().plusDays(15));
        event2.setVenue("Digital Hub Jakarta");
        event2.setTicketPrice(new BigDecimal("250000"));
        event2.setAvailableTickets(50);
        event2.setCategory("Workshop");
        event2.setImageUrl("https://example.com/web-dev.jpg");
        event2.setPromoLabel("Promo Spesial");
        eventRepository.save(event2);

        // Booking
        Booking booking1 = new Booking();
        booking1.setEvent(event1);
        booking1.setUser(user1);
        booking1.setCustomerName("John Doe");
        booking1.setCustomerEmail("john@example.com");
        booking1.setCustomerPhone("081234567890");
        booking1.setNumberOfTickets(2);
        booking1.setTotalAmount(new BigDecimal("1000000"));
        booking1.setStatus("PAID");
        booking1.setPaymentMethod("BANK_TRANSFER");
        booking1.setQrCode("QR_TECH_CONF_001");
        booking1.setRedeemStatus("PENDING");
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setEvent(event2);
        booking2.setUser(user2);
        booking2.setCustomerName("Jane Smith");
        booking2.setCustomerEmail("jane@example.com");
        booking2.setCustomerPhone("089876543210");
        booking2.setNumberOfTickets(1);
        booking2.setTotalAmount(new BigDecimal("250000"));
        booking2.setStatus("PAID");
        booking2.setPaymentMethod("QRIS");
        booking2.setQrCode("QR_WEB_DEV_001");
        booking2.setRedeemStatus("APPROVED");
        bookingRepository.save(booking2);

        System.out.println("Database telah diisi dengan data sample!");
    }
} 