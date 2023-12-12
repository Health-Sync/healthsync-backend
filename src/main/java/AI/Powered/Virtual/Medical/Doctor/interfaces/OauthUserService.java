package AI.Powered.Virtual.Medical.Doctor.interfaces;


import AI.Powered.Virtual.Medical.Doctor.dto.UserSignUpDto;
import AI.Powered.Virtual.Medical.Doctor.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OauthUserService extends UserDetailsService {
    User save(UserSignUpDto userRegisteredDTO);
}
