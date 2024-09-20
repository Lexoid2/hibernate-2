package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "rental")
@Getter
@Setter
@ToString(exclude = {"inventory", "customer", "staff", "payments"})
@EqualsAndHashCode(exclude = {"id", "inventory", "customer", "staff", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rental {
    @Id
    @GeneratedValue
    @Column(name = "rental_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Column(name = "rental_date", nullable = false)
    @NonNull
    LocalDateTime rentalDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    @NonNull
    Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NonNull
    Customer customer;

    @Column(name = "return_date")
    LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NonNull
    Staff staff;

    @OneToMany(mappedBy = "rental")
    List<Payment> payments;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
