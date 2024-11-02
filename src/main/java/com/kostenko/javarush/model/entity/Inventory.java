package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "inventory")
@Getter
@Setter
@ToString(exclude = {"film", "store", "rentals"})
@EqualsAndHashCode(exclude = {"id", "film", "store", "rentals", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "film_id", nullable = false)
    @NonNull
    Film film;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "store_id", nullable = false)
    Store store;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "inventory")
    List<Rental> rentals;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
