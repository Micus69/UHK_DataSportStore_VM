package repository

import.model.Zakaznik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZakaznikRepository
        extends JpaRepository<Zakaznik, Integer> {
}