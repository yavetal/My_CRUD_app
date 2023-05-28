package users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/read")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<Users> all = userRepository.findAll();
        System.out.println(all);
        model.addAttribute(Constants.MODEL_ATTRIBUTE_NAME, all);
        return "all";
    }

    @GetMapping("/{id}")
    @Transactional
    public String id(@PathVariable("id") Long id, Model model) {
        Optional<Users> optionalUser = userRepository.findById(id);
        Users user = optionalUser.get();
        System.out.println(user);
        model.addAttribute(Constants.MODEL_ATTRIBUTE_NAME, user);
        return "id";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute(Constants.MODEL_ATTRIBUTE_NAME, new Users());
        return "new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") Users user) {
        userRepository.save(user);
        return "redirect:/read/all";
    }

    @GetMapping("/edit/{id}")
    @Transactional
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute(Constants.MODEL_ATTRIBUTE_NAME, userRepository.findById(id).get());
        System.out.println(userRepository.findById(id).get());
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") Users user) {
        userRepository.save(user);
        return "redirect:/read/all";

    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        System.out.println(id.toString());
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/read/all";
    }
}