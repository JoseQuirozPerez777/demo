package com.example.demo.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UsuarioRequestDto;
import com.example.demo.dto.UsuarioResponseDto;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDto> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponseDto obtenerUsuarioPorId(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return toResponse(usuario);
    }

    public UsuarioResponseDto crearUsuario(UsuarioRequestDto dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build(); // rol = USER automáticamente

        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto actualizarUsuario(String id, UsuarioRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(
            passwordEncoder.encode(dto.getPassword())
        );

        return toResponse(usuarioRepository.save(usuario));
    }

    public void eliminarUsuario(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDto buscarPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ese correo"));

        return toResponse(usuario);
    }

    private UsuarioResponseDto toResponse(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .build();
    }

    public UsuarioResponseDto login(LoginRequestDto dto) {

        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        boolean valido = passwordEncoder.matches(
                dto.getPassword(),
                usuario.getPassword()
        );

        if (!valido) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        return toResponse(usuario);
    }
}