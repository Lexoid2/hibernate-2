package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "customer")
@Getter
@Setter
@ToString(exclude = {"store", "address", "payments", "rentals"})
@EqualsAndHashCode(exclude = {"id", "store", "address", "payments", "rentals", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @NonNull
    Store store;

    @Column(name = "first_name", length = 45, nullable = false)
    @NonNull
    String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    @NonNull
    String lastName;

    @Column(length = 50)
    String email;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @NonNull
    Address address;

    @OneToMany(mappedBy = "customer")
    List<Payment> payments;

    @OneToMany(mappedBy = "customer")
    List<Rental> rentals;

    @Column(name = "active", nullable = false)
    @NonNull
    Boolean isActive = true;

    @Column(name = "create_date")
    @NonNull
    LocalDateTime createDate;

    @Column(name = "last_update")
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
