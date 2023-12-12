package AI.Powered.Virtual.Medical.Doctor.validator;


import AI.Powered.Virtual.Medical.Doctor.dto.UserSignUpDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^(?=.{1,50}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public boolean validateEmail(UserSignUpDto user) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(user.getEmail());
        return matcher.matches();
    }
    public boolean emailNull(UserSignUpDto user){
        return user.getEmail() == null;
    }

}