package com.example.demo.services;

import com.example.demo.dto.ReseniaRequestDto;
import com.example.demo.dto.ReseniaResponseDto;
import com.example.demo.dto.ReseniasPuntoRequestDto;
import com.example.demo.dto.ReseniasPuntoResponseDto;
import com.example.demo.entities.Resenia;
import com.example.demo.entities.ReseniasPunto;
import com.example.demo.repositories.ReseniasPuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReseniasPuntoService {

    private final ReseniasPuntoRepository reseniasPuntoRepository;

    public List<ReseniasPuntoResponseDto> listarReseniasPuntos() {
        return reseniasPuntoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReseniasPuntoResponseDto obtenerReseniasPorId(String id) {
        ReseniasPunto documento = reseniasPuntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento de reseñas no encontrado"));

        return toResponse(documento);
    }

    public ReseniasPuntoResponseDto obtenerReseniasPorPuntoId(String puntoId) {
        ReseniasPunto documento = reseniasPuntoRepository.findByPuntoId(puntoId)
                .orElseThrow(() -> new RuntimeException("No existen reseñas para este punto"));

        return toResponse(documento);
    }

    public ReseniasPuntoResponseDto crearDocumentoResenias(ReseniasPuntoRequestDto dto) {
        ReseniasPunto documento = ReseniasPunto.builder()
                .puntoId(dto.getPuntoId())
                .resenias(toReseniaEntityList(dto.getResenias()))
                .build();

        return toResponse(reseniasPuntoRepository.save(documento));
    }

    public ReseniasPuntoResponseDto actualizarDocumentoResenias(String id, ReseniasPuntoRequestDto dto) {
        ReseniasPunto documento = reseniasPuntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento de reseñas no encontrado"));

        documento.setPuntoId(dto.getPuntoId());
        documento.setResenias(toReseniaEntityList(dto.getResenias()));

        return toResponse(reseniasPuntoRepository.save(documento));
    }

    public void eliminarDocumentoResenias(String id) {
        ReseniasPunto documento = reseniasPuntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento de reseñas no encontrado"));

        reseniasPuntoRepository.delete(documento);
    }

    public ReseniasPuntoResponseDto agregarResenia(String puntoId, ReseniaRequestDto dto) {
        ReseniasPunto documento = reseniasPuntoRepository.findByPuntoId(puntoId)
                .orElseGet(() -> ReseniasPunto.builder()
                        .puntoId(puntoId)
                        .resenias(new ArrayList<>())
                        .build());

        documento.getResenias().add(toReseniaEntity(dto));

        return toResponse(reseniasPuntoRepository.save(documento));
    }

    private ReseniasPuntoResponseDto toResponse(ReseniasPunto documento) {
        return ReseniasPuntoResponseDto.builder()
                .id(documento.getId())
                .puntoId(documento.getPuntoId())
                .resenias(toReseniaResponseList(documento.getResenias()))
                .build();
    }

    private List<Resenia> toReseniaEntityList(List<ReseniaRequestDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(this::toReseniaEntity)
                .toList();
    }

    private Resenia toReseniaEntity(ReseniaRequestDto dto) {
        return Resenia.builder()
                .usuarioId(dto.getUsuarioId())
                .puntaje(dto.getPuntaje())
                .comentario(dto.getComentario())
                .build();
    }

    private List<ReseniaResponseDto> toReseniaResponseList(List<Resenia> resenias) {
        if (resenias == null) {
            return new ArrayList<>();
        }

        return resenias.stream()
                .map(resenia -> ReseniaResponseDto.builder()
                        .usuarioId(resenia.getUsuarioId())
                        .puntaje(resenia.getPuntaje())
                        .comentario(resenia.getComentario())
                        .build())
                .toList();
    }
}