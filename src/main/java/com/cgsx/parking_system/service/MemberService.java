package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Member;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface MemberService {
    Page<Member> getMember(String keyword, int type, int page, int size);

    void updateMember(Member member);
}
