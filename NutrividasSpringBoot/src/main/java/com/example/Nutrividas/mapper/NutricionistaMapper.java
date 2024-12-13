package com.example.Nutrividas.mapper;

import com.example.Nutrividas.dto.NutricionistaDto;
import com.example.Nutrividas.model.Nutricionista;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NutricionistaMapper {
    NutricionistaMapper INSTANCE = Mappers.getMapper(NutricionistaMapper.class);
    NutricionistaDto toDto(Nutricionista n);
    Nutricionista toModel(NutricionistaDto n);
}

