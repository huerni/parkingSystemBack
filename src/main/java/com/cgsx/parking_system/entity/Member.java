package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员db
 */
@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = 1588491292637973220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date openDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    // 1:月租卡 2:年租卡  3:固定车
    private Integer memberType;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;


}
