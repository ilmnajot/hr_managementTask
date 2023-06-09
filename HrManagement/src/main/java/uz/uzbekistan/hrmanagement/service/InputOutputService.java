package uz.uzbekistan.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzbekistan.hrmanagement.entity.InOutStatus;
import uz.uzbekistan.hrmanagement.entity.InputOutput;
import uz.uzbekistan.hrmanagement.entity.User;
import uz.uzbekistan.hrmanagement.entity.payload.InputOutputDto;
import uz.uzbekistan.hrmanagement.repository.InputOutputRepository;
import uz.uzbekistan.hrmanagement.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

// BU SERVICE FAQAT MAXSUS SCANNER QILUVCHI KOMPUTER UCHUNGINA ISHLAYDI
@Service
public class InputOutputService {
    private final InputOutputRepository repository;
    private final InputOutputRepository statusRepository;
    private final UserRepository userRepository;

    @Autowired
    public InputOutputService(InputOutputRepository repository, InputOutputRepository statusRepository, UserRepository userRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> saveInputOutputHistory(InputOutputDto dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (!optionalUser.isPresent()) return ResponseEntity.notFound().build();
        Optional<InOutStatus> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (!optionalStatus.isPresent()) return ResponseEntity.notFound().build();
        repository.save(new InputOutput(optionalStatus.get().getStatus(), optionalUser.get()));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getInputOutputHistory(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ResponseEntity.ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getInputOutputForUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(repository.findAllByUser_Id(userId));
    }
}
