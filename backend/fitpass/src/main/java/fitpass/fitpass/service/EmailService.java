package fitpass.fitpass.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
