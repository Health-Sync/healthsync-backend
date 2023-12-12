package AI.Powered.Virtual.Medical.Doctor.service;


import AI.Powered.Virtual.Medical.Doctor.configuration.AuthenticationResponse;
import AI.Powered.Virtual.Medical.Doctor.configuration.JwtService;
import AI.Powered.Virtual.Medical.Doctor.dto.LoginDTO;
import AI.Powered.Virtual.Medical.Doctor.dto.RegistrationDTO;
import AI.Powered.Virtual.Medical.Doctor.entries.Role;
import AI.Powered.Virtual.Medical.Doctor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegistrationDTO request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirm_password(passwordEncoder.encode(request.getConfirm_password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
