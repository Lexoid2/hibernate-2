package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "staff")
@Getter
@Setter
@ToString(exclude = {"address", "mainStore", "stores", "payments", "rentals"})
@EqualsAndHashCode(exclude = {"id", "address", "mainStore", "stores", "payments", "rentals", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue
    @Column(name = "staff_id")
    @Setter(AccessLevel.NONE)
    Short id;

    @Column(name = "first_name", length = 45, nullable = false)
    @NonNull
    String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    @NonNull
    String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @NonNull
    Address address;

    @Lob
    Blob picture;

    @Column(length = 50)
    String email;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @NonNull
    Store mainStore;

    @OneToMany(mappedBy = "mainStaff")
    List<Store> stores;

    @OneToMany(mappedBy = "staff")
    List<Payment> payments;

    @OneToMany(mappedBy = "staff")
    List<Rental> rentals;

    @Column(name = "active", nullable = false)
    @NonNull
    Boolean isActive = true;

    @Column(length = 16, nullable = false)
    @NonNull
    String username;

    @Column(length = 40)
    String password;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
