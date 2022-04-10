package com.portfolioegjp.Portfolio.email.controller;

import com.portfolioegjp.Portfolio.email.dto.DetalleContacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@RequestMapping("/contacto")
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class EmailSender {

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/getdetails")
    public @ResponseBody DetalleContacto sendMail(@RequestBody DetalleContacto detalleContacto) throws Exception {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name",detalleContacto.getName());
        model.put("lastName",detalleContacto.getLastName());
        model.put("comments",detalleContacto.getComments());

        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("email-template", context);

        try {
            helper.setTo(detalleContacto.getEmail());
            helper.setText(html,true);
            helper.setSubject("Argentina Programa - #YoProgramo (Esteban Perea)");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);

        return detalleContacto;

    }


}