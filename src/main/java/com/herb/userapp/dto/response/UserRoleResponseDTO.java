package com.herb.userapp.dto.response;

import com.herb.userapp.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponseDTO {
    private Role role;
}
