package com.eventticket.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String customerPhone;

    @Column(nullable = false)
    private Integer numberOfTickets;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED

    @Column(nullable = false)
    private String paymentMethod; // e.g. BANK_TRANSFER, QRIS, CREDIT_CARD

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    private String paymentReference;

    @Column(length = 512)
    private String qrCode;

    @Column(nullable = false)
    private String redeemStatus; // PENDING, APPROVED
    public String getRedeemStatus() { return redeemStatus; }
    public void setRedeemStatus(String redeemStatus) { this.redeemStatus = redeemStatus; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
        if (paymentMethod == null) {
            paymentMethod = "BANK_TRANSFER";
        }
        if (redeemStatus == null) {
            redeemStatus = "PENDING";
        }
    }
} 