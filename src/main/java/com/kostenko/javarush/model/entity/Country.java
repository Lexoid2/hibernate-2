package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "country")
@Getter
@Setter
@ToString(exclude = "cities")
@EqualsAndHashCode(exclude = {"id", "cities", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {
    @Id
    @GeneratedValue
    @Column(name = "country_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Column(length = 50, nullable = false)
    @NonNull
    String country;

    @OneToMany(mappedBy = "country")
    List<City> cities;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
