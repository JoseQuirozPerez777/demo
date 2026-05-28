package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReseniaRequestDto;
import com.example.demo.dto.ReseniasPuntoRequestDto;
import com.example.demo.dto.ReseniasPuntoResponseDto;
import com.example.demo.services.ReseniasPuntoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resenias")
@RequiredArgsConstructor
public class ReseniasPuntoController {

    private final ReseniasPuntoService reseniasPuntoService;

    @GetMapping
    public List<ReseniasPuntoResponseDto> listarReseniasPuntos() {
        return reseniasPuntoService.listarReseniasPuntos();
    }

    @GetMapping("/{id}")
    public ReseniasPuntoResponseDto obtenerReseniasPorId(@PathVariable String id) {
        return reseniasPuntoService.obtenerReseniasPorId(id);
    }

    @GetMapping("/punto/{puntoId}")
    public ReseniasPuntoResponseDto obtenerReseniasPorPuntoId(@PathVariable String puntoId) {
        return reseniasPuntoService.obtenerReseniasPorPuntoId(puntoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReseniasPuntoResponseDto crearDocumentoResenias(
            @Valid @RequestBody ReseniasPuntoRequestDto dto
    ) {
        return reseniasPuntoService.crearDocumentoResenias(dto);
    }

    @PutMapping("/{id}")
    public ReseniasPuntoResponseDto actualizarDocumentoResenias(
            @PathVariable String id,
            @Valid @RequestBody ReseniasPuntoRequestDto dto
    ) {
        return reseniasPuntoService.actualizarDocumentoResenias(id, dto);
    }

    @PostMapping("/punto/{puntoId}/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public ReseniasPuntoResponseDto agregarResenia(
            @PathVariable String puntoId,
            @Valid @RequestBody ReseniaRequestDto dto
    ) {
        return reseniasPuntoService.agregarResenia(puntoId, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDocumentoResenias(@PathVariable String id) {
        reseniasPuntoService.eliminarDocumentoResenias(id);
    }
}