package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "address")
@Getter
@Setter
@ToString(exclude = {"city", "customers", "stores", "staffList"})
@EqualsAndHashCode(exclude = {"id", "city", "customers", "stores", "staffList", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Column(length = 50, nullable = false)
    @NonNull
    String address;

    @Column(name = "address2", length = 50)
    String secondAddress;

    @Column(length = 20, nullable = false)
    @NonNull
    String district;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "city_id", nullable = false)
    @NonNull
    City city;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "address")
    List<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "address")
    List<Store> stores;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "address")
    List<Staff> staffList;

    @Column(name = "postal_code", length = 10)
    String postalCode;

    @Column(length = 20, nullable = false)
    @NonNull
    String phone;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
