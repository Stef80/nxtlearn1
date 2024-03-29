package it.nextre.academy.nxtlearn.repository;

import it.nextre.academy.nxtlearn.model.Guida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuidaRepository extends JpaRepository<Guida, Integer> {
    List<Guida> findTop10ByOrderByDataCreazioneDesc();
    List<Guida> findAllByNomeContaining(String name);
}
