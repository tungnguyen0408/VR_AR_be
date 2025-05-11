// package bt1.web_ban_giay.controller;

// import bt1.web_ban_giay.dto.response.OrderEmailDTO;
// import bt1.web_ban_giay.entity.Order;
// import bt1.web_ban_giay.entity.User;
// import bt1.web_ban_giay.service.EmailService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class EmailController {

// @Autowired
// EmailService emailService;

// @PostMapping("/email/register")
// public ResponseEntity<Void> sendEmail(@RequestBody User user) {
// emailService.sendEmailFromTemplateSync(user, user.getEmail(), "Thông báo đăng
// kí thành công","register");
// return ResponseEntity.status(HttpStatus.OK).body(null);
// }

// @PostMapping("/email/order")
// public ResponseEntity<Void> sendEmailOrder(@RequestBody OrderEmailDTO req) {
// emailService.sendEmailOrder(req, "Thông báo đặt hàng thành công","order");
// return ResponseEntity.status(HttpStatus.OK).body(null);
// }
// }
