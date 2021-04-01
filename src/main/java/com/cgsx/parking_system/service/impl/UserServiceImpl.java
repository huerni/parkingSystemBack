package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.repository.UserRepository;
import com.cgsx.parking_system.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public void updateUser(User user){
        userRepository.save(user);
    }

    @Override
    public Page<User> findAllUser(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "userId");
        return userRepository.findAll(this.getWhereClause(keyword), pageable);
    }

    public Specification<User> getWhereClause(String keyword){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /**
                 * 多表查询
                 */
                if(StringUtils.isNotBlank(keyword)){
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(root.get("username"),"%" + keyword + "%")
                                    )
                            )
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}
