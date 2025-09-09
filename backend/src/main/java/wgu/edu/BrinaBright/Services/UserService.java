package wgu.edu.BrinaBright.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wgu.edu.BrinaBright.DTOs.UpdateAccountRequest;
import wgu.edu.BrinaBright.DTOs.UserDTO;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Repos.MunicipalityRepository;
import wgu.edu.BrinaBright.Repos.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MunicipalityRepository municipalityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findDTOById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Municipality m = user.getMunicipality();
        Long muniId = (m != null) ? m.getId() : null;
        String muniName = (m != null) ? m.getName() : null;
        return new UserDTO(user.getId(), user.getEmail(), user.getRole().name(), muniId, muniName);
    }

    public void updateUserAccount(Long userId, UpdateAccountRequest request) {
        User user = userRepository.findById(userId).orElseThrow();

        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email().trim());
        }
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password().trim()));
        }
        if (request.municipalityId() != null) {
            Municipality m = municipalityRepository.findById(request.municipalityId()).orElseThrow();
            user.setMunicipality(m);
        }

        userRepository.save(user);
    }
}
