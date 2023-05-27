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
        model.addAttribute("users", all);
        return "all";
    }

    @GetMapping("/{id}")
    @Transactional
    public String id(@PathVariable("id") Long id, Model model) {
        Optional<Users> optionalUser = userRepository.findById(id);
        Users user = optionalUser.get();
        System.out.println(user);
        model.addAttribute("user", user);
        return "id";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new Users());
        return "new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") Users user) {
        userRepository.save(user);
        return "redirect:/read/all";
    }

    @GetMapping("/{id}/edit")
    @Transactional
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        System.out.println(userRepository.findById(id).get());
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") Users user) {
        userRepository.save(user);
        return "redirect:/read/all";

    }

    @PostMapping("/delite/{id}")
    public String deliteUserr(@PathVariable("id") Long id) {

        System.out.println(id.toString());
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/read/all";
    }
}