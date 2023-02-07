package org.nbu.company.packages.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PackageDto {
    private int clientSenderId;
    private int clientRecipientId;
    private int employeeId;
    private String description;
    private double weight;
}
