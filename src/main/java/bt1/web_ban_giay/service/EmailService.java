// package bt1.web_ban_giay.service;

// import bt1.web_ban_giay.dto.response.OrderEmailDTO;
// import bt1.web_ban_giay.entity.Order;
// import bt1.web_ban_giay.entity.User;
// import bt1.web_ban_giay.repository.OrderRepository;
// import bt1.web_ban_giay.repository.UserRepository;
// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.MailException;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.stereotype.Service;
// import org.thymeleaf.TemplateEngine;
// import org.thymeleaf.context.Context;

// import java.nio.charset.StandardCharsets;
// import java.text.NumberFormat;
// import java.util.Locale;
// import java.util.Optional;

// @Service
// public class EmailService {

// @Autowired
// JavaMailSender javaMailSender;
// @Autowired
// TemplateEngine templateEngine;

// @Autowired
// UserRepository userRepository;
// @Autowired
// OrderRepository orderRepository;
// public void sendEmailSync(String to, String subject, String content, boolean
// isMultipart, boolean isHtml) {
// // Prepare message using a Spring helper
// MimeMessage mimeMessage = javaMailSender.createMimeMessage();
// try {
// MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart,
// StandardCharsets.UTF_8.name());
// message.setTo(to);
// message.setSubject(subject);
// message.setText(content, isHtml);
// this.javaMailSender.send(mimeMessage);
// } catch (MailException | MessagingException e) {
// System.out.println("ERROR SEND EMAIL: " + e);
// }
// }

// public void sendEmailFromTemplateSync(User user,String to, String subject,
// String
// templateName) {
// Context context = new Context();
// context.setVariable("userName",user.getUsername());
// context.setVariable("userEmail",user.getEmail());
// String content = templateEngine.process(templateName, context);
// this.sendEmailSync(to, subject, content, false, true);
// }

// public void sendEmailOrder (OrderEmailDTO req, String subject, String
// templateName) {
// Optional<User> user=userRepository.findById(req.getUserId());
// Optional<Order> orderEmail= orderRepository.findById(req.getOrderId());
// Context context = new Context();
// context.setVariable("userName",user.get().getUsername());
// context.setVariable("products",orderEmail.get().getOrderDetails());

// NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi",
// "VN"));
// String tmp=formatter.format(orderEmail.get().getTotal());
// context.setVariable("totalPrice",tmp);

// String content = templateEngine.process(templateName, context);
// this.sendEmailSync(user.get().getEmail(), subject, content, false, true);
// }
// }
