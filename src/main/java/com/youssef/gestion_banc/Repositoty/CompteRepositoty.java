package com.youssef.gestion_banc.Repositoty;


import com.youssef.gestion_banc.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepositoty extends JpaRepository<Compte,Long> {

}
