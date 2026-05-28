package com.example.demo.services;

import com.example.demo.dto.PuntoRequestDto;
import com.example.demo.dto.PuntoResponseDto;
import com.example.demo.dto.RecompensaDto;
import com.example.demo.entities.Punto;
import com.example.demo.entities.Recompensa;
import com.example.demo.repositories.PuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PuntoService {

    private final PuntoRepository puntoRepository;

    public List<PuntoResponseDto> listarPuntos() {
        return puntoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PuntoResponseDto obtenerPuntoPorId(String id) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto no encontrado"));

        return toResponse(punto);
    }

    public PuntoResponseDto crearPunto(PuntoRequestDto dto) {
        Punto punto = Punto.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .direccion(dto.getDireccion())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .horario(dto.getHorario())
                .telefono(dto.getTelefono())
                .whatsapp(dto.getWhatsapp())
                .materiales(safeList(dto.getMateriales()))
                .recompensas(toRecompensaEntityList(dto.getRecompensas()))
                .usuarioId(dto.getUsuarioId())
                .imagenes(safeList(dto.getImagenes()))
                .redes(safeList(dto.getRedes()))
                .build();

        return toResponse(puntoRepository.save(punto));
    }

    public PuntoResponseDto actualizarPunto(String id, PuntoRequestDto dto) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto no encontrado"));

        punto.setNombre(dto.getNombre());
        punto.setTipo(dto.getTipo());
        punto.setDescripcion(dto.getDescripcion());
        punto.setDireccion(dto.getDireccion());
        punto.setLat(dto.getLat());
        punto.setLng(dto.getLng());
        punto.setHorario(dto.getHorario());
        punto.setTelefono(dto.getTelefono());
        punto.setWhatsapp(dto.getWhatsapp());
        punto.setMateriales(safeList(dto.getMateriales()));
        punto.setRecompensas(toRecompensaEntityList(dto.getRecompensas()));
        punto.setUsuarioId(dto.getUsuarioId());
        punto.setImagenes(safeList(dto.getImagenes()));
        punto.setRedes(safeList(dto.getRedes()));

        return toResponse(puntoRepository.save(punto));
    }

    public void eliminarPunto(String id) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto no encontrado"));

        puntoRepository.delete(punto);
    }

    public List<PuntoResponseDto> buscarPorTipo(String tipo) {
        return puntoRepository.findByTipo(tipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PuntoResponseDto> buscarPorUsuarioId(String usuarioId) {
        return puntoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PuntoResponseDto toResponse(Punto punto) {
        return PuntoResponseDto.builder()
                .id(punto.getId())
                .nombre(punto.getNombre())
                .tipo(punto.getTipo())
                .descripcion(punto.getDescripcion())
                .direccion(punto.getDireccion())
                .lat(punto.getLat())
                .lng(punto.getLng())
                .horario(punto.getHorario())
                .telefono(punto.getTelefono())
                .whatsapp(punto.getWhatsapp())
                .materiales(safeList(punto.getMateriales()))
                .recompensas(toRecompensaDtoList(punto.getRecompensas()))
                .usuarioId(punto.getUsuarioId())
                .imagenes(safeList(punto.getImagenes()))
                .redes(safeList(punto.getRedes()))
                .build();
    }

    private List<Recompensa> toRecompensaEntityList(List<RecompensaDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(dto -> Recompensa.builder()
                        .nombre(dto.getNombre())
                        .descripcion(dto.getDescripcion())
                        .stock(dto.getStock())
                        .estado(dto.getEstado())
                        .build())
                .toList();
    }

    private List<RecompensaDto> toRecompensaDtoList(List<Recompensa> recompensas) {
        if (recompensas == null) {
            return new ArrayList<>();
        }

        return recompensas.stream()
                .map(recompensa -> RecompensaDto.builder()
                        .nombre(recompensa.getNombre())
                        .descripcion(recompensa.getDescripcion())
                        .stock(recompensa.getStock())
                        .estado(recompensa.getEstado())
                        .build())
                .toList();
    }

    private List<String> safeList(List<String> lista) {
        return lista == null ? new ArrayList<>() : lista;
    }
}