package AI.Powered.Virtual.Medical.Doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    private String email;
    private String password;
    private String confirm_password;


}
