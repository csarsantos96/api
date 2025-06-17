package com.forumhub.api.repository;

import com.forumhub.api.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByTopico_Id(Long topicoId);

}
