package users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/read")
public class UserController {

    private final UserRepository userRepository ;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String all(Model model){
        List<Users> all = userRepository.findAll();
        System.out.println(all.toString());
        model.addAttribute("users", all);
        return "all";
    }
    @GetMapping("/{id}")
    @Transactional
    public  String  id (@PathVariable("id") Long id, Model model){
        Users user = userRepository.getById(id);
        System.out.println(user.toString());
       model.addAttribute("user", user);
        return "id";
    }
    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new Users());
        return "new";
    }
    @PostMapping()
    public String createNewUser(@ModelAttribute("user") Users user){
        Users save = userRepository.save(user);
        return "redirect:/read/all";
    }
}
