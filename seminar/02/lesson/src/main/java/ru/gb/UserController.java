package ru.gb;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/users")
public class UserController {

    // REST
    // GET /users/{id} => 404 (Not Found)
    // GET /users?nameLike='Igor%' => 204 (No Content)

    // DELETE /users/{id} - удаляет
    // POST   /users/      {"id": "1", "name": "newName"} - создает
    // PUT    /users/{id}  {"id": "1", "name": "newName"} - изменение

    // HTTP HyperText Transfer Protocol
    // GET POST PUT PATCH DELETE ... HEAD OPTIONS

    // http://ip-address/users/all -> List<User>
    // http://ip-address/users/1 -> User(1, Igor)
    // http://ip-address/users?name=Igor -> User(1, Igor)

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/test", consumes = MediaType.IMAGE_JPEG_VALUE)
    public String test() {
        return """
                <h1>Title</h1>
                """;
    }

    //  @RequestMapping(path = "/users", method = RequestMethod.GET)
    @GetMapping("/all")
    public List<User> getUsers() {
        return repository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public User getUser(@PathVariable long id) {
        return repository.getById(id);
    }

    @GetMapping
    public User getUserByName(@RequestParam String name) {
        return repository.getByName(name);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        User existsUser = repository.getById(id);
        if (existsUser == null) { // 404
            throw new IllegalArgumentException();
        }

        existsUser.setName(user.getName());
        return existsUser;
    }

}
