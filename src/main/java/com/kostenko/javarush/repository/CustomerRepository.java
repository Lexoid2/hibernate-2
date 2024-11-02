package com.kostenko.javarush.repository;

import com.kostenko.javarush.exceptions.RentalNotFoundException;
import com.kostenko.javarush.model.entity.Customer;
import com.kostenko.javarush.model.entity.Payment;
import com.kostenko.javarush.model.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
public class CustomerRepository {
    private final SessionFactory sessionFactory;

    /**
     * Persists a new Customer entity in the database.
     * <p>
     * This method initiates a transaction to persist the given {@code Customer} entity.
     * If the transaction is successful, the customer is saved in the database.
     * In case of any failure during the process, the transaction is rolled back.
     *
     * @param customer the {@code Customer} entity to be persisted in the database; cannot be {@code null}
     */
    public void create(Customer customer) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();

            log.debug("Customer created successfully: {}", customer);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("Transaction rolled back due to an error", e);
            } else {
                log.error("Error during customer creation", e);
            }
        }
    }

    /**
     * Marks a rental as returned for a given customer by updating the rental's return date.
     * <p>
     * This method identifies the {@code Rental} associated with the provided {@code rentalId}
     * in the list of rentals for the given {@code Customer}. If found, the method updates
     * the rental's return date to the current date and commits the change to the database.
     * If no such rental is found, a {@code RentalNotFoundException} is thrown.
     * If any other error occurs during the transaction, it is rolled back.
     *
     * @param customer the {@code Customer} who is returning the rental; cannot be {@code null}
     * @param rentalId the ID of the rental to be marked as returned; cannot be {@code null}
     * @throws RentalNotFoundException if the rental with the given {@code rentalId} is not found for the customer
     */
    public void returnRental(Customer customer, Integer rentalId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Rental rental = customer.getRentals().stream()
                    .filter(r -> r.getId().equals(rentalId))
                    .findFirst()
                    .orElseThrow(() -> new RentalNotFoundException("Rental not found for customer"));

            rental.setReturnDate(LocalDateTime.now());

            session.merge(customer);

            transaction.commit();
            log.debug("Rental with ID {} successfully returned for customer {}", rentalId, customer.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error while returning rental with ID {} for customer {}", rentalId, customer.getId(), e);
            throw e;
        }
    }

    /**
     * Persists the rental and payment for the given customer.
     *
     * @param customer the customer making the rental
     * @param rental the rental details to be saved
     * @param payment the payment details to be saved
     */
    public void rentInventory(Customer customer, Rental rental, Payment payment) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            rental.setCustomer(customer);
            payment.setCustomer(customer);

            customer = session.merge(customer);
            session.merge(rental);
            session.merge(payment);

            transaction.commit();
            log.debug("Rental and payment successfully saved for customer {}", customer.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error while processing rental and payment for customer {}", customer.getId(), e);
            throw e;
        }
    }
}
