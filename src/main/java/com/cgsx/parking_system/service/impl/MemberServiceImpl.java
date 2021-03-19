package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.entity.Member;
import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.repository.MemberRepository;
import com.cgsx.parking_system.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Page<Member> getMember(String keyword, int type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "memberId");
        return memberRepository.findAll(this.getWhereClause(keyword, type), pageable);
    }

    @Override
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public Specification<Member> getWhereClause(String keyword, int type){
        return new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /**
                 * 多表查询
                 */
                Join<Member, Car> sroot = root.join("member", JoinType.LEFT);
                if(StringUtils.isNotBlank(keyword)){
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(sroot.get("carowner"),"%" + keyword + "%"),
                                            criteriaBuilder.like(sroot.get("carNum"),"%" + keyword + "%")
                                    )
                            )
                    );
                }
                if(type != -1)
                    predicates.add(criteriaBuilder.equal(root.get("memberType"), type));
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}
