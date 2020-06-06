package discussion.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import discussion.dto.SignupConfirmEmail;
import discussion.exceptions.ActivationEmailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender javaMailSender;
	private final MailContentBuilder mailContentBuilder;
	
	@Async // Will use apache kafka for email message
	void sendMail(SignupConfirmEmail signupConfirmEmail) {
		MimeMessagePreparator messagePreparator=new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
				mimeMessageHelper.setFrom("naimul@gmail.com");
				mimeMessageHelper.setTo(signupConfirmEmail.getRecipient());
				mimeMessageHelper.setSubject(signupConfirmEmail.getSubject());
				mimeMessageHelper.setText(signupConfirmEmail.getBody());		
			}
		};
		try {
			javaMailSender.send(messagePreparator);
            log.info("Activation sign up email sent");
        } catch (MailException e) {
            throw new ActivationEmailException("Exception occurred when sending mail to " + signupConfirmEmail.getRecipient());
        }
	}

}
