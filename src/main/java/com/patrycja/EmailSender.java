package com.patrycja;

import java.io.IOException;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
public class EmailSender {
	
	private String emailTo;
	private String emailfrom = "patrycia.puchalska@gmail.com ";
	
	public EmailSender(String emailTo) {
		this.emailTo = emailTo;
	}
	
	public void sendEmail(String message) throws IOException {
		Email from = new Email(emailfrom);
	    String subject = "Wiadomości";
	    Email to = new Email(emailTo);
	    Content content = new Content("text/html", message);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("key");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	    } catch (IOException ex) {
	      throw ex;
	    }
	}
}

