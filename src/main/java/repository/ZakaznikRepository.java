package repository;

import model.Zakaznik;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ZakaznikRepository extends JpaRepository<Zakaznik, Integer> {

    Optional<Zakaznik> findByEmail(String email);

    boolean existsByEmail(String email);
}