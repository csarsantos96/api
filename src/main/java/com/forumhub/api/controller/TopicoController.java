package com.forumhub.api.controller;

import com.forumhub.api.dto.*;
import com.forumhub.api.model.*;
import com.forumhub.api.repository.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @PostMapping
    public ResponseEntity<TopicoDetalhadoResponse> criarTopico(@RequestBody @Valid TopicoRequest dto) {
        Curso curso = cursoRepository.findByNome(dto.getNomeCurso());
        Optional<Usuario> autorOptional = usuarioRepository.findByNome(dto.getNomeAutor());

        if (curso == null || autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico();
        topico.setTitulo(dto.getTitulo());
        topico.setMensagem(dto.getMensagem());
        topico.setCurso(curso);
        topico.setAutor(autorOptional.get());

        topicoRepository.save(topico);

        TopicoDetalhadoResponse resposta = new TopicoDetalhadoResponse(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                curso.getNome(),
                autorOptional.get().getNome(),
                topico.getStatus()
        );

        return ResponseEntity
                .created(URI.create("/topicos/" + topico.getId()))
                .body(resposta);
    }

    @GetMapping
    public Page<TopicoResponse> listar(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao) {

        Page<Topico> topicos = (nomeCurso == null)
                ? topicoRepository.findAll(paginacao)
                : topicoRepository.findByCurso_Nome(nomeCurso, paginacao);

        return topicos.map(t -> new TopicoResponse(
                t.getTitulo(),
                t.getMensagem(),
                t.getDataCriacao()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalhadoResponse> detalhar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico t = topicoOptional.get();
        TopicoDetalhadoResponse dto = new TopicoDetalhadoResponse(
                t.getTitulo(),
                t.getMensagem(),
                t.getDataCriacao(),
                t.getCurso().getNome(),
                t.getAutor().getNome(),
                t.getStatus()
        );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoRequest dto) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            Topico topico = optional.get();
            topico.setTitulo(dto.getTitulo());
            topico.setMensagem(dto.getMensagem());

            topicoRepository.save(topico);
            return ResponseEntity.ok("Tópico atualizado com sucesso.");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok("Tópico removido com sucesso.");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/respostas")
    public ResponseEntity<String> responderTopico(@PathVariable Long id, @RequestBody @Valid RespostaRequest dto) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        Optional<Usuario> autorOptional = usuarioRepository.findByNome(dto.nomeAutor());

        if (topicoOptional.isEmpty() || autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Tópico ou autor não encontrado.");
        }

        Resposta resposta = new Resposta();
        resposta.setMensagem(dto.mensagem());
        resposta.setTopico(topicoOptional.get());
        resposta.setAutor(autorOptional.get());

        respostaRepository.save(resposta);
        return ResponseEntity.ok("Resposta registrada com sucesso.");
    }

    @GetMapping("/{id}/respostas")
    public ResponseEntity<?> listarRespostas(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var respostas = respostaRepository.findByTopico_Id(id);
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/autor")
    public Page<TopicoResponse> listarPorAutor(
            @RequestParam String nomeAutor,
            @PageableDefault(size = 10) Pageable paginacao) {

        return topicoRepository.findByAutor_Nome(nomeAutor, paginacao)
                .map(t -> new TopicoResponse(
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getDataCriacao()
                ));
    }
}
