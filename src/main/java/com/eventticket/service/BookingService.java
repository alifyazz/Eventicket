package com.eventticket.service;

import com.eventticket.model.Booking;
import com.eventticket.model.Event;
import com.eventticket.model.User;
import com.eventticket.repository.BookingRepository;
import com.eventticket.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public Booking createBooking(Event event, User user, String customerName, String customerEmail, String customerPhone, int numberOfTickets, String paymentMethod) {
        if (event.getAvailableTickets() < numberOfTickets) {
            throw new RuntimeException("Not enough tickets available");
        }
        event.setAvailableTickets(event.getAvailableTickets() - numberOfTickets);
        eventRepository.save(event);
        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setCustomerName(customerName);
        booking.setCustomerEmail(customerEmail);
        booking.setCustomerPhone(customerPhone);
        booking.setNumberOfTickets(numberOfTickets);
        booking.setTotalAmount(event.getTicketPrice().multiply(new java.math.BigDecimal(numberOfTickets)));
        booking.setStatus("PENDING");
        booking.setPaymentMethod(paymentMethod);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }

    public List<Booking> getBookingsByEventId(Long eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    @Transactional
    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
            
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    @Transactional
    public Booking confirmPayment(Long bookingId, String paymentReference) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("PAID");
        booking.setPaymentReference(paymentReference);
        booking.setQrCode(UUID.randomUUID().toString());
        booking.setRedeemStatus("PENDING");
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking approveBookingByQr(String qrCode) {
        Booking booking = bookingRepository.findAll().stream()
            .filter(b -> qrCode.equals(b.getQrCode()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("QR code not found"));
        if (!"PAID".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is not paid or already approved/cancelled");
        }
        booking.setRedeemStatus("APPROVED");
        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
            
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already cancelled");
        }
        
        // Return tickets to available pool
        Event event = booking.getEvent();
        event.setAvailableTickets(event.getAvailableTickets() + booking.getNumberOfTickets());
        eventRepository.save(event);
        
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    public java.util.Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }
} 