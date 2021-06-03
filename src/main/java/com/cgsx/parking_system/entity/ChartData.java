package com.cgsx.parking_system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chartData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartData implements Serializable {

    private static final long serialVersionUID = 3546790279807415543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private Integer type;

    private Integer year;

    private Integer Jan;

    private Integer Feb;

    private Integer Mar;

    private Integer Apr;

    private Integer May;

    private Integer June;

    private Integer July;

    private Integer Aug;

    private  Integer Sept;

    private  Integer Oct;

    private Integer Novem;

    private Integer Decem;

}
