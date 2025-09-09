package wgu.edu.BrinaBright.Security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Enums.Role;
import wgu.edu.BrinaBright.Repos.MunicipalityRepository;
import wgu.edu.BrinaBright.Repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final MunicipalityRepository municipalityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwt;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        Municipality muni = null;
        if (req.getMunicipalityId() != null) {
            muni = municipalityRepository.findById(req.getMunicipalityId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid municipalityId"));
        } else if (req.getZipCode() != null && !req.getZipCode().isBlank()) {
            var nearest = municipalityRepository.findNearestByZip(req.getZipCode());
            if (!nearest.isEmpty() && nearest.get(0).getMeters() != null) {
                double miles = nearest.get(0).getMeters() / 1609.344;
                if (miles <= 50.0) {
                    muni = municipalityRepository.findById(nearest.get(0).getId()).orElse(null);
                }
            }
        }

        User u = new User();
        u.setEmail(req.getEmail());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        if (muni != null) u.setMunicipality(muni);

        userRepository.save(u);

        String token = jwt.generateToken(UserPrincipal.create(u));
        return ResponseEntity.ok(AuthResponse.from(u, token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );


        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {

            return ResponseEntity.badRequest().build();
        }

        String token = jwt.generateToken(UserPrincipal.create(user));
        return ResponseEntity.ok(AuthResponse.from(user, token));
    }
}
