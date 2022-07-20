package ti.vjps.museu.util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@SuppressWarnings("serial")
@WebServlet("/sendEmail")
public class EmailController extends HttpServlet{
	
	private final String EMAIL = ""; // INSIRA O EMAIL
	private final String SENHA = ""; // INSIRA A SENHA

	public EmailController(String texto, String assunto, String emailEnviar) {
		try {
			HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthentication(EMAIL, SENHA);
            email.setSSLOnConnect(true);
            email.setFrom(EMAIL);
            
            email.setSubject(assunto);
	        email.setHtmlMsg(texto);
	        email.addTo(emailEnviar);
	        email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
	}
}