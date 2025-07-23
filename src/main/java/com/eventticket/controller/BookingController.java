package com.eventticket.controller;

import com.eventticket.model.Booking;
import com.eventticket.model.User;
import com.eventticket.model.Event;
import com.eventticket.repository.UserRepository;
import com.eventticket.repository.EventRepository;
import com.eventticket.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.eventticket.service.CustomUserDetails;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestParam Long eventId,
                                           @RequestParam(required = false) Long userId,
                                           @RequestParam String customerName,
                                           @RequestParam String customerEmail,
                                           @RequestParam String customerPhone,
                                           @RequestParam int numberOfTickets,
                                           @RequestParam String paymentMethod,
                                           @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            User user;
            if (userId != null) {
                user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            } else if (principal != null) {
                user = userRepository.findById(principal.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            } else {
                throw new RuntimeException("User not authenticated");
            }
            Booking createdBooking = bookingService.createBooking(event, user, customerName, customerEmail, customerPhone, numberOfTickets, paymentMethod);
            return ResponseEntity.ok(createdBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public List<Booking> getBookingsByEmail(@PathVariable String email) {
        return bookingService.getBookingsByEmail(email);
    }

    @GetMapping("/event/{eventId}")
    public List<Booking> getBookingsByEventId(@PathVariable Long eventId) {
        return bookingService.getBookingsByEventId(eventId);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return bookingService.getBookingsByUser(user);
    }

    @GetMapping("/user/me")
    public List<Booking> getMyBookings(@AuthenticationPrincipal CustomUserDetails principal) {
        if (principal == null) throw new RuntimeException("Not authenticated");
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        return bookingService.getBookingsByUser(user);
    }

    @PostMapping("/scan-qr")
    public ResponseEntity<?> scanQr(@RequestParam String qr) {
        try {
            Booking booking = bookingService.approveBookingByQr(qr);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public List<Booking> getBookingsByStatus(@PathVariable String status) {
        return bookingService.getBookingsByStatus(status);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<?> confirmPayment(@PathVariable Long id, @RequestParam String paymentReference) {
        try {
            Booking booking = bookingService.confirmPayment(id, paymentReference);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

@Controller
class BookingPageController {
    @GetMapping("/booking.html")
    public String bookingPage() {
        return "booking";
    }
}

@Controller
class MyBookingsPageController {
    @GetMapping("/my-bookings.html")
    public String myBookingsPage() {
        return "my-bookings";
    }
}

@Controller
class PaymentSuccessPageController {
    @GetMapping("/payment-success.html")
    public String paymentSuccessPage() {
        return "payment-success";
    }
} 