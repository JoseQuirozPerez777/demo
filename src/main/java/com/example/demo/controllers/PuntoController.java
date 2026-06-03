package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PuntoRequestDto;
import com.example.demo.dto.PuntoResponseDto;
import com.example.demo.services.PuntoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/puntos")
@RequiredArgsConstructor
public class PuntoController {

    private final PuntoService puntoService;

    @GetMapping
    public List<PuntoResponseDto> listarPuntos() {
        return puntoService.listarPuntos();
    }

    @GetMapping("/{id}")
    public PuntoResponseDto obtenerPuntoPorId(@PathVariable String id) {
        return puntoService.obtenerPuntoPorId(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<PuntoResponseDto> buscarPorTipo(@PathVariable String tipo) {
        return puntoService.buscarPorTipo(tipo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<PuntoResponseDto> buscarPorUsuarioId(@PathVariable String usuarioId) {
        return puntoService.buscarPorUsuarioId(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PuntoResponseDto crearPunto(@Valid @RequestBody PuntoRequestDto dto) {
        return puntoService.crearPunto(dto);
    }
    
    @PutMapping("/{id}")
    public PuntoResponseDto actualizarPunto(
            @PathVariable String id,
            @Valid @RequestBody PuntoRequestDto dto
    ) {
        return puntoService.actualizarPunto(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPunto(@PathVariable String id) {
        puntoService.eliminarPunto(id);
    }
    @GetMapping("/por-radio")
    public List<PuntoResponseDto> buscarPorRadio(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam double radio
    ) {
        return puntoService.buscarPorRadio(lat, lng, radio);
    }
    @GetMapping("/por-radio-y-tipo")
    public List<PuntoResponseDto> buscarPorRadioYTipo(
            @RequestParam String lat,
            @RequestParam String lng,
            @RequestParam double radio,
            @RequestParam String tipo
    ) {
        return puntoService.buscarPorRadioYTipo(lat, lng, radio, tipo);
    }
    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> actualizarPunto(
        @PathVariable String id,
        @RequestParam String usuarioId, // Solo recibimos el ID del usuario que interactúa
        @Valid @RequestBody PuntoRequestDto dto
       ) {
    // El servicio ahora investiga el rol internamente en la BD
    boolean autorizado = puntoService.puedeEditarPunto(id, usuarioId);
    
    if (!autorizado) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No tienes permisos para modificar este punto. Debes ser el propietario o un Administrador.");
    }
    
    PuntoResponseDto puntoActualizado = puntoService.actualizarPunto(id, dto);
    return ResponseEntity.ok(puntoActualizado);
}
}