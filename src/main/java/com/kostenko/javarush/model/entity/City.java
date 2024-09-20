package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "city")
@Getter
@Setter
@ToString(exclude = {"country", "addresses"})
@EqualsAndHashCode(exclude = {"id", "country", "addresses", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class City {
    @Id
    @GeneratedValue
    @Column(name = "city_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Column(length = 50, nullable = false)
    @NonNull
    String city;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @NonNull
    Country country;

    @OneToMany(mappedBy = "city")
    List<Address> addresses;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
