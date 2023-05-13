package uz.uzbekistan.hrmanagement.entity.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class InputOutputDto {


    private UUID userId;
    private Long status;
}
