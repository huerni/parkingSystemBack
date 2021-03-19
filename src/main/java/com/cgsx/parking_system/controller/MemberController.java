package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Member;
import com.cgsx.parking_system.service.MemberService;
import com.cgsx.parking_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/listMember")
    public Result listMember(
            @RequestParam(name = "keyword")String keyword,
            @RequestParam(name = "memberType", defaultValue = "-1")Integer memberType,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size){
        return new Result().success("查询成功", memberService.getMember(keyword, memberType, page, size));
    }

    @PostMapping("/updateMember")
    public Result updateMember(@RequestBody Member member){
        memberService.updateMember(member);
        return new Result().success("更新成功", member);
    }
}
