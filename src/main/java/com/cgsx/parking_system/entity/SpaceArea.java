package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 车位区域db
 */
@Entity
@Table(name = "spaceArea")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceArea implements Serializable {
    private static final long serialVersionUID = 7738856200213007655L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceAreaId;

    private String area;
}
