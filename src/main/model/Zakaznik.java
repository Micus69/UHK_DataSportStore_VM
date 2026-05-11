package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "zakaznik")
public class Zakaznik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zakaznik_id")
    private Integer id;

    @Column(nullable = false)
    private String jmeno;

    @Column(nullable = false)
    private String prijmeni;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefon;

    @Column(nullable = false)
    private String mesto;

    @Column(nullable = false)
    private String ulice;

    @Column(nullable = false)
    private String psc;

    private LocalDate datumRegistrace;

    public Zakaznik() {
    }

}