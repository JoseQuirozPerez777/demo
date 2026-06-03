package com.example.demo.services;

import com.example.demo.dto.PuntoRequestDto;
import com.example.demo.dto.PuntoResponseDto;
import com.example.demo.dto.RecompensaDto;
import com.example.demo.dto.RedDto;
import com.example.demo.entities.Punto;
import com.example.demo.entities.Recompensa;
import com.example.demo.entities.Red;
import com.example.demo.repositories.PuntoRepository;
import com.example.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PuntoService {

    private final PuntoRepository puntoRepository;
    private final UsuarioRepository usuarioRepository;
    // =========================
    // LISTAR
    // =========================
    public List<PuntoResponseDto> listarPuntos() {
        return puntoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =========================
    // OBTENER POR ID
    // =========================
    public PuntoResponseDto obtenerPuntoPorId(String id) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto no encontrado"));

        return toResponse(punto);
    }

    // =========================
    // CREAR
    // =========================
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
                .redes(toRedEntityList(dto.getRedes()))
                .build();

        return toResponse(puntoRepository.save(punto));
    }
    public List<PuntoResponseDto> buscarPorUsuarioId(String usuarioId) {
    // 1. Buscamos en la base de datos todos los puntos que pertenecen a ese usuarioId
    List<Punto> puntosDelUsuario = puntoRepository.findByUsuarioId(usuarioId);

    // 2. Convertimos la lista de entidades 'Punto' a 'PuntoResponseDto' usando Streams
    return puntosDelUsuario.stream()
            .map(this::convertirADto)
            .toList();
}
    // =========================
    // ACTUALIZAR
    // =========================
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
        punto.setRedes(toRedEntityList(dto.getRedes()));

        return toResponse(puntoRepository.save(punto));
    }
    private PuntoResponseDto convertirADto(Punto punto) {
    if (punto == null) {
        return null;
    }

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
            .whatsapp(punto.getWhatsapp()) // Asegúrate de que coincida con el nombre en tu DTO
            .materiales(punto.getMateriales())
            .recompensas(toRecompensaDtoList(punto.getRecompensas()))
            .usuarioId(punto.getUsuarioId())
            .imagenes(punto.getImagenes())
            .redes(toRedDtoList(punto.getRedes()))
            .build();
}
    // =========================
    // ELIMINAR
    // =========================
    public void eliminarPunto(String id) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto no encontrado"));

        puntoRepository.delete(punto);
    }

    // =========================
    // BUSQUEDAS
    // =========================
    public List<PuntoResponseDto> buscarPorTipo(String tipo) {
        if(tipo.equalsIgnoreCase("todos")){

          return puntoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();  
        }

        return puntoRepository.findByTipoIgnoreCase(tipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =========================
    // MAPPER RESPONSE
    // =========================
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
                .redes(toRedDtoList(punto.getRedes()))
                .build();
    }

    // =========================
    // RECOMPENSAS MAPPERS
    // =========================
    private List<Recompensa> toRecompensaEntityList(List<RecompensaDto> dtos) {
        if (dtos == null) return new ArrayList<>();

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
        if (recompensas == null) return new ArrayList<>();

        return recompensas.stream()
                .map(r -> RecompensaDto.builder()
                        .nombre(r.getNombre())
                        .descripcion(r.getDescripcion())
                        .stock(r.getStock())
                        .estado(r.getEstado())
                        .build())
                .toList();
    }

    // =========================
    // REDES MAPPERS (CORREGIDO)
    // =========================
    private List<Red> toRedEntityList(List<RedDto> dtos) {
        if (dtos == null) return new ArrayList<>();

        return dtos.stream()
                .map(dto -> Red.builder()
                        .nombre(dto.getNombre())
                        .enlace(dto.getEnlace())
                        .build())
                .toList();
    }

    private List<RedDto> toRedDtoList(List<Red> redes) {
        if (redes == null) return new ArrayList<>();

        return redes.stream()
                .map(r -> RedDto.builder()
                        .nombre(r.getNombre())
                        .enlace(r.getEnlace())
                        .build())
                .toList();
    }

    // =========================
    // SAFE LIST
    // =========================
    private List<String> safeList(List<String> lista) {
        return lista == null ? new ArrayList<>() : lista;
    }
    public List<PuntoResponseDto> buscarPorRadio(String latitudStr, String longitudStr, double radioKm) {
        double latCentro = Double.parseDouble(latitudStr);
        double lngCentro = Double.parseDouble(longitudStr);

        return puntoRepository.findAll().stream()
                .filter(punto -> punto.getLat() != null && punto.getLng() != null)
                .filter(punto -> calcularDistanciaKm(latCentro, lngCentro, punto.getLat(), punto.getLng()) <= radioKm)
                .map(this::convertirADto)
                .toList();
    }
    public List<PuntoResponseDto> buscarPorRadioYTipo(String latitudStr, String longitudStr, double radioKm, String tipo) {
        double latCentro = Double.parseDouble(latitudStr);
        double lngCentro = Double.parseDouble(longitudStr);

        // Primero filtramos por tipo en la Base de Datos (más eficiente)
        List<Punto> puntosDelTipo = puntoRepository.findByTipoIgnoreCase(tipo);

        // Luego filtramos por distancia en memoria
        return puntosDelTipo.stream()
                .filter(punto -> punto.getLat() != null && punto.getLng() != null)
                .filter(punto -> calcularDistanciaKm(latCentro, lngCentro, punto.getLat(), punto.getLng()) <= radioKm)
                .map(this::convertirADto)
                .toList();
    }

    // --- FÓRMULA MATEMÁTICA HAVERSINE ---
    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la tierra en kilómetros

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c; // Retorna la distancia en kilómetros
    }
    public boolean puedeEditarPunto(String puntoId, String usuarioId) {
        // 1. Buscamos el punto en la base de datos
        Optional<Punto> puntoOpt = puntoRepository.findById(puntoId);
        if (puntoOpt.isEmpty()) {
            return false; // Si el punto no existe, nadie puede editarlo
        }
        Punto punto = puntoOpt.get();

        // 2. Control de Propietario: Si el usuario es el dueño del punto, se le permite editar directamente
        if (punto.getUsuarioId() != null && punto.getUsuarioId().equals(usuarioId.trim())) {
            return true;
        }

        // 3. Control de Administrador: Si no es el dueño, buscamos al usuario en la BD para ver su rol
        return usuarioRepository.findById(usuarioId.trim())
                .map(usuario -> {
                    // Cambia 'getRol()' por el método real de tu entidad Usuario (ej. getRole(), getTipoUsuario())
                    String rol = usuario.getRol().toString(); 
                    
                    // Si el rol almacenado en la BD es "ADMIN", se le concede el permiso
                    return rol != null && rol.trim().equalsIgnoreCase("ADMIN");
                })
                .orElse(false); // Si el usuario no existe en la BD, devuelve false
    }
    
}
