package com.example.business_center.model.dto;

import com.example.business_center.security.user.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends UserDto {
    private List<Long> serviceOrders;
    private List<Long> rents;
}
