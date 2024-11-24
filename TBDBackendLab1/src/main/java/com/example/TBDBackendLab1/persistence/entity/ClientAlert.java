package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAlert {

    private Integer alert_id;
    private Integer client_id;
    private Date alert_date;
    private String alert_desc;
}
