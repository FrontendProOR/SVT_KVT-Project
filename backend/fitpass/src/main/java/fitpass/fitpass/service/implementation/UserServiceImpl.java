package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.UserDTO;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.repository.UserRepository;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository uRepo;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public List<User> findAll() {
        return uRepo.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return uRepo.findFirstByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        return uRepo.findById(id);
    }

    @Override
    public User createUser(UserDTO dto) {
        if (uRepo.findFirstByEmail(dto.getEmail()).isPresent()) {
            return null;
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAddress(dto.getAddress());
        user.setCreatedAt(LocalDate.now());

        return uRepo.save(user);
    }
    @Override
    public boolean changePassword(String email, String oldPwd, String newPwd) {
        User user = uRepo.findFirstByEmail(email).orElse(null);
        if (user == null || !pwdEncoder.matches(oldPwd, user.getPassword())) {
            return false;
        }
        user.setPassword(pwdEncoder.encode(newPwd));
        uRepo.save(user);
        return true;
    }

    @Override
    public User updateUser(UserDTO dto, String email) {
        User user = uRepo.findFirstByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        user.setAddress(dto.getAddress());
        user.setBirthday(dto.getBirthday());
        user.setCity(dto.getCity());
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setSurname(dto.getSurname());
        user.setZipCode(dto.getZipCode());
        return uRepo.save(user);
    }
}
