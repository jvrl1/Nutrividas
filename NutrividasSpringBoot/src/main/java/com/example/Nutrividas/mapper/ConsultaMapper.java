package com.example.Nutrividas.mapper;

import com.example.Nutrividas.dto.ConsultaDto;
import com.example.Nutrividas.model.Consulta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {
    ConsultaMapper INSTANCE = Mappers.getMapper(ConsultaMapper.class);

    @Mapping(target = "nutricionistaId", source = "nutricionista.id")
    @Mapping(target = "nutricionistaNome", source = "nutricionista.nome") 
    @Mapping(target = "usuarioId", source = "usuario.id")
    ConsultaDto toDto(Consulta consulta);

    @Mapping(target = "nutricionista.id", source = "nutricionistaId")
    @Mapping(target = "usuario.id", source = "usuarioId")
    Consulta toModel(ConsultaDto consultaDto);
}
