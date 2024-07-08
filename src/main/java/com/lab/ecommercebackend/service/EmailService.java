package com.lab.ecommercebackend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class EmailService {
	
	//@Autowired
	private JavaMailSender javaMailSender;
	
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        }
    
	
	//Envia uma email para o destintário passado no metodo no metodo
    //A mensagem por enquanto está genérica
    public void enviarEmail(String destinatario) {
        try {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Bem vindo ao meu ecommerce!");
        mensagem.setText("Olá! :):),\n" +
                "\n" +
                "Seja muito bem-vindo(a) à nossa comunidade! Estamos muito felizes em tê-lo(a) conosco.\n" +
                "\n" +
                "Somos uma equipe dedicada a oferecer a melhor experiência possível aos nossos usuários, e estamos ansiosos para compartilhar tudo o que temos a oferecer com você.\n" +
                "\n" +
                "Sinta-se à vontade para explorar nosso site, descobrir nossos produtos incríveis e aproveitar as vantagens exclusivas de ser parte da nossa comunidade.\n" +
                "\n" +
                "Se precisar de qualquer ajuda ou tiver alguma dúvida, não hesite em entrar em contato conosco. Estamos sempre aqui para ajudar!\n" +
                "\n" +
                "Mais uma vez, obrigado(a) por se juntar a nós. Esperamos que você tenha uma experiência incrível em nossa plataforma.\n" +
                "\n" +
                "Atenciosamente,\n" +
                "O desenvolvedor que só queria deixar uma msg bonita");
                javaMailSender.send(mensagem);
    }
        catch (Exception e) {
			e.printStackTrace();
		}
    }
}
