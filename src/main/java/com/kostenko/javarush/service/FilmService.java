package com.kostenko.javarush.service;

import com.kostenko.javarush.model.entity.*;
import com.kostenko.javarush.repository.FilmRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class FilmService {
    private final FilmRepository repository;

    /**
     * Makes the given film available for rental by persisting it and its related inventories
     * and rentals in the database.
     * <p>
     * Checks that the provided {@code Film} is not {@code null} and has a non-empty list
     * of inventories. Ensures that each {@code Inventory} in the film has a non-empty list
     * of rentals.
     * <p>
     * If any condition is not met, throws an {@code IllegalArgumentException}.
     * Otherwise, persists the film, its inventories, and rentals.
     *
     * @param film the {@code Film} entity to be made available for rental; cannot be {@code null}
     * @throws IllegalArgumentException if the {@code film} is {@code null}, its inventory list
     *         is {@code null} or empty, or if any inventory's rental list is {@code null} or empty
     */
    public void makeFilmAvailableForRental(Film film) {
        log.debug("Attempting to create a new film with rentals");

        if (film == null) {
            log.error("Film creation failed: film cannot be null");
            throw new IllegalArgumentException("Film cannot be null");
        }

        if (film.getInventories() == null || film.getInventories().isEmpty()) {
            log.error("Film creation failed: inventories list cannot be null or empty");
            throw new IllegalArgumentException("Inventories list cannot be null or empty");
        }

        for (Inventory inventory : film.getInventories()) {
            if (inventory.getRentals() == null || inventory.getRentals().isEmpty()) {
                log.error("Film creation failed: rentals list for inventory {} cannot be null or empty",
                        inventory.getId());
                throw new IllegalArgumentException("Rentals list for inventory cannot be null or empty");
            }
        }

        repository.create(film);
    }
}
