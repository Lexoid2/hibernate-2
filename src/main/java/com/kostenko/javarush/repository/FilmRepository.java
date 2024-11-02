package com.kostenko.javarush.repository;

import com.kostenko.javarush.model.entity.Film;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@AllArgsConstructor
@Slf4j
public class FilmRepository {
    private final SessionFactory sessionFactory;

    /**
     * Persists a new Film entity in the database.
     * <p>
     * This method initiates a transaction to persist the given {@code Film} entity.
     * If the transaction is successful, the film is saved in the database.
     * In case of any failure during the process, the transaction is rolled back.
     *
     * @param film the {@code Film} entity to be persisted in the database; cannot be {@code null}
     */
    public void create(Film film) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(film);
            transaction.commit();

            log.debug("Film created successfully: {}", film);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("Transaction rolled back due to an error", e);
            } else {
                log.error("Error during film creation", e);
            }
        }
    }
}
