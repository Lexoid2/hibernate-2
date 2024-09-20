package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "category")
@Getter
@Setter
@ToString(exclude = "films")
@EqualsAndHashCode(exclude = {"id", "films", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    @Setter(AccessLevel.NONE)
    Short id;

    @Column(length = 25, nullable = false)
    @NonNull
    String name;

    @ManyToMany(mappedBy = "categories")
    List<Film> films;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
