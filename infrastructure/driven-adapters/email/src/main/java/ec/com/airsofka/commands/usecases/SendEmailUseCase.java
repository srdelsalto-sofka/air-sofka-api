package ec.com.airsofka.commands.usecases;


import ec.com.airsofka.EmailTemplate;
import ec.com.airsofka.commands.SendEmailCommand;
import ec.com.airsofka.generics.interfaces.IUseCaseExecute;
import ec.com.airsofka.queries.responses.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.reactivestreams.Publisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

public class SendEmailUseCase implements IUseCaseExecute<SendEmailCommand, EmailResponse> {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SendEmailUseCase(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public Mono<EmailResponse> execute(SendEmailCommand cmd) {
        return Mono.fromRunnable(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                Context context = new Context();
                context.setVariables(cmd.getVariables());
                String htmlContent = templateEngine.process("booking-confirmation", context);

                helper.setTo(cmd.getTo());
                helper.setSubject("Your Flight Booking Confirmation");
                helper.setText(htmlContent, true);

                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Error sending email", e);
            }
        });
    }
}

