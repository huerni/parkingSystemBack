package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 记录db
 */
@Entity
@Table(name = "record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {
    private static final long serialVersionUID = 5899225916613417837L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private Date enterDate;

    private Date leaveDate;

    // 0:临时支付  1:月租卡  2:年租卡  3:固定车
    private Integer payment;

    private Float money;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
