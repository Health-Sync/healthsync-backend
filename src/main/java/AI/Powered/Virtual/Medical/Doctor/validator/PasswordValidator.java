package AI.Powered.Virtual.Medical.Doctor.validator;


import AI.Powered.Virtual.Medical.Doctor.dto.UserSignUpDto;
import AI.Powered.Virtual.Medical.Doctor.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator{
    private Pattern pattern;
    private Matcher matcher;
    private static final String NUMBER_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,50}$";
    public boolean matchingPassword(UserSignUpDto user){
        return user.getPassword().equals(user.getConfirmationPassword());
    }

    public boolean Passwordsmatch(String newPass, String confirmPass, User user){
        user.setPassword(newPass);
        return user.getPassword().equals(confirmPass);
    }

    public boolean passwordValid(String password){
        pattern = Pattern.compile(NUMBER_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validPassword(UserSignUpDto user){
        pattern = Pattern.compile(NUMBER_PATTERN);
        matcher = pattern.matcher(user.getPassword());
        return matcher.matches();
    }

    public boolean nullPassword(UserSignUpDto userSignUpDto){
        return userSignUpDto.getPassword() == null;
    }
    public boolean passwordNull(String password, String confirmPass){
        return password == null & confirmPass == null;
    }
}
