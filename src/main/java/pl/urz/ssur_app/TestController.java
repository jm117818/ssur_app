package pl.urz.ssur_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.urz.ssur_app.model.Groups;
import pl.urz.ssur_app.model.UserGroups;
import pl.urz.ssur_app.model.Users;
import pl.urz.ssur_app.repository.GroupsRepository;
import pl.urz.ssur_app.repository.UserGroupsRepository;
import pl.urz.ssur_app.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class TestController {
    UsersRepository userRepository;
    GroupsRepository groupsRepository;
    UserGroupsRepository userGroupsRepository;

    @Autowired
    public void setUserGroupsRepository(UserGroupsRepository userGroupsRepository) {
        this.userGroupsRepository = userGroupsRepository;
    }

    @Autowired
    public void setGroupsRepository(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Autowired
    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/usr")
    public List<Users> test() {
        return userRepository.getAll();
    }

    @GetMapping("/grp")
    public List<Groups> grp() {
        return groupsRepository.getAll();
    }

    @GetMapping("/userGroups")
    public List<UserGroups> userGroups() {
        return userGroupsRepository.getAll();
    }

    @GetMapping("/userGroups/{id}")
    @ResponseBody
    public Optional<UserGroups> userGropusByGrpId (@PathVariable("id") Integer id) {
        return userGroupsRepository.findOneByGroupId(id);
    }
}