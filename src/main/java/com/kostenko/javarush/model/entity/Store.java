package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "store")
@Getter
@Setter
@ToString(exclude = {"mainStaff", "address", "customers", "inventories", "staffList"})
@EqualsAndHashCode(exclude = {"id", "mainStaff", "address", "customers", "staffList", "inventories", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    @Setter(AccessLevel.NONE)
    Short id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "manager_staff_id", nullable = false)
    @NonNull
    Staff mainStaff;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", nullable = false)
    @NonNull
    Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "mainStore")
    List<Staff> staffList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "store")
    List<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "store")
    List<Inventory> inventories;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
