package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "movie", name = "language")
@Getter
@Setter
@ToString(exclude = {"filmsAsMainLanguage", "filmsAsOriginalLanguage"})
@EqualsAndHashCode(exclude = {"id", "filmsAsMainLanguage", "filmsAsOriginalLanguage", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    @Setter(AccessLevel.NONE)
    Short id;

    @Column(length = 20, nullable = false)
    @NonNull
    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "language")
    List<Film> filmsAsMainLanguage;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "originalLanguage")
    List<Film> filmsAsOriginalLanguage;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
