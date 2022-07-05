package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Student;
import com.tmm.zhxy.pojo.Teacher;
import com.tmm.zhxy.service.AdminService;
import com.tmm.zhxy.service.ClazzService;
import com.tmm.zhxy.service.StudentService;
import com.tmm.zhxy.service.TeacherService;
import com.tmm.zhxy.util.*;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system/")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public Result login(HttpSession session, @RequestBody LoginForm loginForm) {
        //拿到session中的二维码
        String verifiCode = (String)session.getAttribute("verifiCode");
        if (!loginForm.getVerifiCode().equalsIgnoreCase(verifiCode)) {
            return Result.fail().message("验证码错误");
        }
        if(StringUtils.isBlank(verifiCode)) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }

        Map<String, Object> map = new HashMap<>();
        switch (loginForm.getUserType()) {
            case 1:{
                Admin admin = adminService.login(loginForm);
                if(admin != null) {
                    map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    return Result.ok(map);
                }else {
                    return Result.fail().message("用户名或密码错误");
                }
            }
            case 2:{
                Student student = studentService.login(loginForm);
                if(student != null) {
                    map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    return Result.ok(map);
                }else {
                    return Result.fail().message("用户名或密码错误");
                }
            }
            case 3:{
                Teacher teacher = teacherService.login(loginForm);
                if(teacher != null) {
                    map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    return Result.ok(map);
                }else {
                    return Result.fail().message("用户名或密码错误");
                }
            }
        }

        return Result.fail().message("无此用户");
    }

    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        if(StringUtils.isBlank(token)) {
            return Result.fail().message("请先登录");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        Map<String, Object> map = new HashMap<>();
        map.put("userType", userType);
        if(userType == 1) {
            Admin admin = adminService.getOneAdminById(userId.intValue());
            map.put("user", admin);
        }
        if(userType == 2) {
            Student student = studentService.getOneStudentById(userId.intValue());
            map.put("user", student);
        }
        if(userType == 3) {
            Teacher teacher = teacherService.getOneTeacherById(userId.intValue());
            map.put("user", teacher);
        }
        return Result.ok(map);
    }


    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取图片上的验证码
        String verifiCode =new String( CreateVerifiCodeImage.getVerifiCode());
        // 将验证码文本放入session域,为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        // 将验证码图片响应给浏览器

        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile headImg, HttpServletRequest request) {
        if(headImg.isEmpty()) {
            return Result.ok();
        }

        int i = headImg.getOriginalFilename().lastIndexOf(".");
        String fileSuffix = headImg.getOriginalFilename().substring(i);
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.ROOT);
        String filename = uuid.concat(fileSuffix);
        String realPath = request.getServletContext().getRealPath("/upload");
        File file = new File(realPath);
        if(!file.exists()) {
            file.mkdir();
        }
        String filePath = realPath + File.separator + filename;
        try {
            headImg.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().message("文件上传失败，请稍后再试");
        }
        return Result.ok("/upload/" + filename);
    }

    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd,
                            @RequestHeader("token") String token) {

        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        if(userType == 1) {
            Admin admin = adminService.getOneAdminById(userId.intValue());
            String realOldPwd = admin.getPassword();
            if(!MD5.encrypt(oldPwd).equals(realOldPwd)) {
                return Result.fail().message("原密码有误");
            }
            Admin newAdmin = new Admin();
            admin.setId(userId.intValue());
            admin.setPassword(MD5.encrypt(newPwd));
            adminService.saveOrUpdate(admin);
        }
        if(userType == 2) {
            Student student = studentService.getOneStudentById(userId.intValue());
            String realOldPwd = student.getPassword();
            if(!MD5.encrypt(oldPwd).equals(realOldPwd)) {
                return Result.fail().message("原密码有误");
            }
            Student newStudent = new Student();
            student.setId(userId.intValue());
            student.setPassword(MD5.encrypt(newPwd));
            studentService.saveOrUpdate(student);
        }
        if(userType == 3) {
            Teacher teacher = teacherService.getOneTeacherById(userId.intValue());
            String realOldPwd = teacher.getPassword();
            if(!MD5.encrypt(oldPwd).equals(realOldPwd)) {
                return Result.fail().message("原密码有误");
            }
            Teacher newTeacher = new Teacher();
            teacher.setId(userId.intValue());
            teacher.setPassword(MD5.encrypt(newPwd));
            teacherService.saveOrUpdate(teacher);
        }
        return Result.ok().message("密码修改成功");
    }


}
