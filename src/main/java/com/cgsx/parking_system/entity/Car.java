package com.cgsx.parking_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private static final long serialVersionUID = 6361581576434220583L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    //车牌号
    private String carNum;

    //车主人
    private String carOwner;

    //车进入次数
    private Integer carTimes;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "car_id")
    private List<Record> recordList = new ArrayList<>();

}
