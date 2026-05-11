package service;

import model.Zakaznik;
import repository.ZakaznikRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ZakaznikService {

    private final ZakaznikRepository zakaznikRepository;

    public ZakaznikService(ZakaznikRepository zakaznikRepository) {
        this.zakaznikRepository = zakaznikRepository;
    }

    public List<Zakaznik> findAll() {
        return zakaznikRepository.findAll();
    }

    public Zakaznik save(Zakaznik zakaznik) {
        if (zakaznik.getDatumRegistrace() == null) {
            zakaznik.setDatumRegistrace(LocalDate.now());
        }

        if (zakaznikRepository.existsByEmail(zakaznik.getEmail())) {
            throw new IllegalArgumentException("Customer with this email already exists.");
        }

        return zakaznikRepository.save(zakaznik);
    }

    public Zakaznik findById(Integer id) {
        return zakaznikRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }

    public void deleteById(Integer id) {
        zakaznikRepository.deleteById(id);
    }
}