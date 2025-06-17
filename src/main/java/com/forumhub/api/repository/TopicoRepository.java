package com.forumhub.api.repository;

import com.forumhub.api.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Evita duplicações no cadastro
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Filtro por nome do curso
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);

    Page<Topico> findByAutor_Nome(String nome, Pageable pageable);

}
