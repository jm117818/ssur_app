package pl.urz.ssur_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.urz.ssur_app.model.User;
import pl.urz.ssur_app.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class TestController {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public List<User> test(){
    return userRepository.getAll();
    }
}
