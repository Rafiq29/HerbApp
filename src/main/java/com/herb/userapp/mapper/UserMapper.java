package com.herb.userapp.mapper;

import com.herb.userapp.dto.request.UserRequestDTO;
import com.herb.userapp.dto.response.UserResponseDTO;
import com.herb.userapp.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserRequestDTO requestDTO);

    UserResponseDTO toDto(User user);
}
