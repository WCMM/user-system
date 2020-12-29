package com.example.demo.controller;

/**
 * @Author wangnan
 * @Date 2019/4/8/008 2019-04 15:27
 * @Param []
 * @return
 **/

import com.example.demo.utils.result.Result;
import com.example.demo.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    JavaMailSender jms;

    @Value("${spring.mail.sendTo}")
    private String sendTo;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/send")
    public Result send() {
        MimeMessage message = mailSender.createMimeMessage();
        String content="<html>\n"+"<body>\n"
                + "<h3>hello world!测试发送html格式邮件</h3>\n"
                +"</body>\n"+"</html>";
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setSubject("问好邮件");
            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("html格式邮件发送成功");
        } catch (Exception e) {
            System.out.println("html格式邮件发送失败");
        }
        return ResultUtil.success();
    }
}
