package AI.Powered.Virtual.Medical.Doctor.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
    void authenticateUser(String userEmail, HttpServletRequest servRequest);
    void validateToken(String userToken);
    void resetPassword(String newPass, String confirmPass);
}
