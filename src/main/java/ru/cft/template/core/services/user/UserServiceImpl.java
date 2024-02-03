package ru.cft.template.core.services.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.api.dto.user.request.RegisterDTO;
import ru.cft.template.api.dto.user.request.UpdateDTO;
import ru.cft.template.api.dto.user.response.GetUserDTO;
import ru.cft.template.api.exceptions.UserNotFoundException;
import ru.cft.template.core.repositories.user.UserRepository;
import ru.cft.template.core.services.wallet.WalletService;
import ru.cft.template.core.models.User;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    //    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;

    @Transactional(readOnly = true)
    public List<User> get() {
        return repository.findAll();
    }

    public GetUserDTO getUserById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return mapUserToDTO(user);
    }

    private GetUserDTO mapUserToDTO(User user) {
        GetUserDTO userDTO = new GetUserDTO();
        userDTO.setId(user.getId());
        userDTO.setWalletId(user.getWallet().getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setFullName(user.getFirstName() + " " + user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setLastUpdateDate(user.getLastUpdateDate());
        userDTO.setAge(user.getAge());

        return userDTO;
    }

    @Transactional
    public GetUserDTO update(UUID id, UpdateDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        repository.save(user);
        return mapUserToDTO(user);
    }

    @Transactional
    public void register(RegisterDTO dto) {
        User user = User.builder()
                .phone(dto.getPhone())
//            .password(passwordEncoder.encode(dto.getPassword()))
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .wallet(walletService.createWallet())
                .lastUpdateDate(new Date())
                .registrationDate(new Date())
                .build();
        repository.save(user);
    }
}
