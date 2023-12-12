package AI.Powered.Virtual.Medical.Doctor.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.interfaces.PasswordResetService;
import tech.aluve.calendar.repository.UserRepository;
import tech.aluve.calendar.security.JwtToken;
import tech.aluve.calendar.validator.PasswordValidator;

@Service("PasswordResetServiceImpl")
public class PasswordResetServiceImpl implements PasswordResetService {

    Logger logger = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailService emailService;
    private PasswordValidator passwordValidator;
    private PasswordEncoder passwordEncoder;
    private JwtToken token;
    private String emailFromUser;
    private String passwordResponseCode;
    private String passwordResponseMessage;

    public PasswordResetServiceImpl (PasswordEncoder encodepassword, UserRepository userRepo, JwtToken jwtToken, EmailService userEmailService){
        this.passwordEncoder = encodepassword;
        this.userRepository = userRepo;
        this.token = jwtToken;
    }

    @Override
    public void authenticateUser(String email, HttpServletRequest servRequest) {
        String schemeName = servRequest.getScheme();
        String serverName = servRequest.getServerName();
        String userEmail = email;
        passwordValidator = new PasswordValidator();
        try {
            if(userRepository.existsByEmail(userEmail)){
                User user = userRepository.findByEmail(userEmail);
                String newToken = token.createToken(user);
                user.setEmail(userEmail);
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setSubject("Pending password reset");
                simpleMailMessage.setText("Click on link to reset your password!"+schemeName+"://"+serverName+":8080"+"/passwordreset/verifytoken?token="+newToken);
                emailService.sendEmail(simpleMailMessage);
                //Setting message and code
                setPasswordResponseMessage("Successfully Authenticated!");
                setPasswordResponseCode("R00");

                logger.debug("Debug log message");
                logger.info("Info log message");
                logger.error("Error log message");
                logger.warn("Warn log message");
                logger.trace("Trace log message");
            }else{
                setPasswordResponseMessage("Please register first!");
                setPasswordResponseCode("R01");
            }
        }catch (Exception e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }

    }

    @Override
    public void validateToken(String userToken){
        try {
            if(token.validateClaims(userToken)){
                setUserEmail(token.getEmail(userToken));
                setPasswordResponseMessage("Token Valid and Redirected to password reset page");
                setPasswordResponseCode("R00");
            }
        }catch (ExpiredJwtException exp){
            setPasswordResponseMessage("Token Expired!");
            setPasswordResponseCode("R01");
        }

    }

    @Override
    public void resetPassword(String newPass, String confirmPass){
        try {
            passwordValidator = new PasswordValidator();

            if(getUserEmail() != null){
                User user = userRepository.findByEmail(getUserEmail());
                if (!passwordValidator.passwordValid(newPass) || !passwordValidator.Passwordsmatch(newPass, confirmPass, user)
                        || passwordValidator.passwordNull(newPass, confirmPass)) {
                    setPasswordResponseMessage("Password not valid");
                    setPasswordResponseCode("R01");
                }else {
                    user.setPassword(passwordEncoder.encode(newPass));
                    userRepository.save(user);
                    setPasswordResponseMessage("Password successfully updated");
                    setPasswordResponseCode("R00");

                }
            }else {
                setPasswordResponseMessage("Email not registered!");
                setPasswordResponseCode("R01");
            }

            logger.debug("Debug log message");
            logger.info("Info log message");
            logger.error("Error log message");
            logger.warn("Warn log message");
            logger.trace("Trace log message");
        }catch (Exception e){
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }

    }

    public String getPasswordResponseCode() {
        return passwordResponseCode;
    }

    public void setPasswordResponseCode(String passwordResponseCode) {
        this.passwordResponseCode = passwordResponseCode;
    }

    public String getPasswordResponseMessage() {
        return passwordResponseMessage;
    }

    public void setPasswordResponseMessage(String passwordResponseMessage) {
        this.passwordResponseMessage = passwordResponseMessage;
    }


    public String getUserEmail() {
        return emailFromUser;
    }

    public void setUserEmail(String userEmail) {
        this.emailFromUser = userEmail;
    }


}
