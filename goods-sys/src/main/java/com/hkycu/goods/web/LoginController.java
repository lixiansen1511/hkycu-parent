package com.hkycu.goods.web;

import com.hkycu.goods.model.UmsMember;
import com.hkycu.goods.result.CodeMsg;
import com.hkycu.goods.result.Result;
import com.hkycu.goods.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    @ResponseBody
    public Result<UmsMember> login(@RequestBody UmsMember umsMember, HttpServletRequest request){
        System.out.println("username====" + umsMember.getUsername());
        System.out.println("password====" + umsMember.getPassword());

        Result<UmsMember> result = this.memberService.login(umsMember);
        request.getSession().setAttribute("member",result.getData());
        return result;
    }

    @PostMapping("/adminlogin")
    @ResponseBody
    public Result adminlogin(@RequestParam("username") String username,@RequestParam("password") String password){
        System.out.println("==username==" + username+"==password=="+password);
        UmsMember umsMember = memberService.loginByPhone(username,password);
        if (ObjectUtils.isEmpty(umsMember)){
            return new Result(CodeMsg.PASSWORD_ERROR);
        }
        Result result = new Result(CodeMsg.SUCESS);
        UmsMember member = new UmsMember();
        member.setUsername(username);
        member.setPassword(password);
        result.setData(member);
        return result;
    }


}
