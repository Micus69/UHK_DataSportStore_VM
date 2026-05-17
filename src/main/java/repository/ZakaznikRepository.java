/*
 * Spring Data JPA repository for customer entity.
 *
 * Main functionality:
 * - Provides CRUD operations for customers
 * - Searches customers by email
 * - Checks if customer email already exists
 *
 * Spring automatically generates implementation
 * for declared repository methods.
 */

package repository;

import model.Zakaznik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZakaznikRepository
        extends JpaRepository<Zakaznik, Integer> {

    /*
     * Finds customer by email address.
     *
     * Returns:
     * Optional<Zakaznik>
     */
    Optional<Zakaznik> findByEmail(String email);

    /*
     * Checks whether customer email already exists.
     *
     * Returns:
     * true  -> email exists
     * false -> email does not exist
     */
    boolean existsByEmail(String email);
}