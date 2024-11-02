package com.kostenko.javarush.service;

import com.kostenko.javarush.model.entity.*;
import com.kostenko.javarush.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository repository;

    /**
     * Validates and creates a new Customer by delegating the task to the repository layer.
     * <p>
     * This method ensures the provided {@code Customer} is not {@code null},
     * then delegates the creation of the customer to the repository layer.
     * If validation fails, an {@code IllegalArgumentException} is thrown.
     *
     * @param customer the {@code Customer} entity to be validated and created; cannot be {@code null}
     * @throws IllegalArgumentException if {@code customer} is {@code null}
     */
    public void createCustomer(Customer customer) {
        log.debug("Attempting to create a new customer");

        if (customer == null) {
            log.error("Customer creation failed: customer cannot be null");
            throw new IllegalArgumentException("Customer cannot be null");
        }

        repository.create(customer);
    }

    /**
     * Handles the return of a rented film by a customer.
     * <p>
     * This method validates that both the {@code Customer} and {@code rentalId}
     * are not {@code null}, then proceeds to mark the rental as returned by
     * delegating the operation to the repository layer.
     *
     * @param customer the {@code Customer} returning the rented film; cannot be {@code null}
     * @param rentalId the ID of the rental to be marked as returned; cannot be {@code null}
     * @throws IllegalArgumentException if either {@code customer} or {@code rentalId} is {@code null}
     */
    public void returnRentedFilmByCustomer(Customer customer, Integer rentalId) {
        log.debug("Customer {} is returning rental with ID {}",
                customer != null ? customer.getId() : null, rentalId);

        if (customer == null) {
            log.error("Customer cannot be null");
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (rentalId == null) {
            log.error("Rental ID cannot be null");
            throw new IllegalArgumentException("Rental ID cannot be null");
        }

        repository.returnRental(customer, rentalId);
    }

    /**
     * Method where a customer rents an available inventory, creates a rental, and makes a payment.
     *
     * @param customer the customer who rents the inventory
     * @param inventory the inventory to be rented
     * @param staff the staff processing the payment
     * @param paymentAmount the amount the customer pays for the rental
     * @throws IllegalStateException if the inventory is not available for rental
     */
    public void rentInventory(Customer customer, Inventory inventory, Staff staff, BigDecimal paymentAmount) {
        log.debug("Customer {} is attempting to rent inventory {}", customer.getId(), inventory.getId());

        boolean isAvailable = inventory.getRentals().stream()
                .allMatch(r -> r.getReturnDate() != null || r.getId() == null);

        if (!isAvailable) {
            log.error("Inventory {} is not available for rental", inventory.getId());
            throw new IllegalStateException("Inventory is not available for rental");
        }

        Rental rental = new Rental();
        rental.setInventory(inventory);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDateTime.now());
        rental.setStaff(staff);

        customer.getRentals().add(rental);

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setStaff(staff);
        payment.setRental(rental);
        payment.setAmount(paymentAmount);
        payment.setPaymentDate(LocalDateTime.now());

        customer.getPayments().add(payment);

        repository.rentInventory(customer, rental, payment);
    }
}
