package AI.Powered.Virtual.Medical.Doctor.controller;


import AI.Powered.Virtual.Medical.Doctor.dto.LoginDTO;
import AI.Powered.Virtual.Medical.Doctor.interfaces.OauthUserService;
import AI.Powered.Virtual.Medical.Doctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private OauthUserService userService;

    @Autowired
    UserRepository userRepo;

    @ModelAttribute("user")
    public LoginDTO userLoginDTO() {
        return new LoginDTO();
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public void loginUser(@ModelAttribute("user")
                          LoginDTO userLoginDTO) {
        System.out.println("UserDTO"+userLoginDTO);
        userService.loadUserByUsername(userLoginDTO.getUsername());
    }
}
