package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(schema = "movie", name = "film_text")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Text {
    @Id
    @GeneratedValue
    @Column(name = "film_id")
    @Setter(AccessLevel.NONE)
    Integer id;

    @Column(nullable = false)
    @NonNull
    String title;

    @Column(length = 65_535)
    String description;
}
