package fitpass.fitpass.controller;

import fitpass.fitpass.service.ManagesService;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/manages")
public class ManagesController {
    @Autowired
    private ManagesService managesService;

    @Autowired
    private UserService userService;

}
