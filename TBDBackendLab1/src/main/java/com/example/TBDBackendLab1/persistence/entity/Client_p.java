package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client_p {
        private Integer client_id;  // Asegúrate de que el nombre coincide con la columna SQL
        private String client_name;
        private Integer total_orders;
        private Integer problematic_orders;
        private Double issue_percentage;
}
