package com.kostenko.javarush.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Table(schema = "movie", name = "film_text")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Text {
    @Column(nullable = false)
    @NonNull
    String title;

    @Column(length = 65_535)
    String description;
}
