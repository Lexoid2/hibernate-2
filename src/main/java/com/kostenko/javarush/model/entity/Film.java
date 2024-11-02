package com.kostenko.javarush.model.entity;

import com.kostenko.javarush.model.converters.RatingConverter;
import com.kostenko.javarush.model.enums.Rating;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(schema = "movie", name = "film")
@Getter
@Setter
@ToString(exclude = {"language", "originalLanguage", "actors", "categories", "inventories"})
@EqualsAndHashCode(exclude = {"id", "language", "originalLanguage", "actors",
                                "categories", "inventories", "lastUpdate"})
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Embedded
    @NonNull
    Text text;

    @Column(name = "release_year")
    Year releaseYear;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "language_id", nullable = false)
    @NonNull
    Language language;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "original_language_id")
    Language originalLanguage;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    List<Actor> actors;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "film")
    List<Inventory> inventories;

    @Column(name = "rental_duration", nullable = false)
    @NonNull
    Short rentalDuration = 3;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    @NonNull
    BigDecimal rentalRate = new BigDecimal("4.99");

    Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    @NonNull
    BigDecimal replacementCost = new BigDecimal("19.99");

    @Convert(converter = RatingConverter.class)
    Rating rating = Rating.G;

    @Column(name = "special_features")
    String specialFeatures;

    @Column(name = "last_update", nullable = false)
    @Setter(AccessLevel.NONE)
    LocalDateTime lastUpdate = LocalDateTime.now();

    public List<String> getSpecialFeatures() {
        return Arrays.asList(specialFeatures.split(","));
    }

    public void setSpecialFeatures(List<String> specialFeatures) {
        this.specialFeatures = String.join(",", specialFeatures);
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
