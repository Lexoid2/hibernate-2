package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "movie", name = "payment")
@Getter
@Setter
@ToString(exclude = {"customer", "staff", "rental"})
@EqualsAndHashCode(exclude = {"id", "customer", "staff", "rental", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NonNull
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NonNull
    Staff staff;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    Rental rental;

    @Column(nullable = false, precision = 5, scale = 2)
    @NonNull
    BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    @NonNull
    LocalDateTime paymentDate;

    @Column(name = "last_update")
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
