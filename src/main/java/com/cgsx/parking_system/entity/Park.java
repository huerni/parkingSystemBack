package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统详细信息db
 */
@Entity
@Table(name = "park")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Park implements Serializable {
    private static final long serialVersionUID = 2002194038364835433L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkId;

    private String parkName;

    private Integer timeAll;

    private Integer SpaceFull;

    private Integer SpaceEmpty;
}
