package wgu.edu.BrinaBright.Security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    public String email;
    public String password;
    public String zipCode;
    public Long municipalityId;
}
