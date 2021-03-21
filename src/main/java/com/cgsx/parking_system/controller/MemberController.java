package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Member;
import com.cgsx.parking_system.service.MemberService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/listMember")
    public Result listMember(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "memberType", defaultValue = "-1")Integer memberType,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size){
        log.info("请求接口listMember， 参数:"+"keyword:"+keyword+"memberType:"+memberType+"page:"+page+"limit"+size);
        return new Result().success("查询成功", memberService.getMember(keyword, memberType, page, size));
    }

    @PostMapping("/updateMember")
    public Result updateMember(@RequestBody Member member){
        memberService.updateMember(member);
        log.info("请求接口updateMember,body:"+member.toString());
        return new Result().success("更新成功", member);
    }

    @PostMapping("/addMember")
    public Result addMember(@RequestBody Member member){
        memberService.updateMember(member);
        log.info("请求接口addMember,body:" + member.toString());
        return new Result().success("添加成功", member);
    }
}
