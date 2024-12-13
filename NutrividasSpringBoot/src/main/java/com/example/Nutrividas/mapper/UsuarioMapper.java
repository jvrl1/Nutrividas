package com.example.Nutrividas.mapper;

import com.example.Nutrividas.dto.UsuarioDto;
import com.example.Nutrividas.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDto toDto(Usuario usuario);
    Usuario toModel(UsuarioDto usuarioDto);
}
