package pl.urz.ssur_app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.urz.ssur_app.model.Groups;
import pl.urz.ssur_app.model.User;
import pl.urz.ssur_app.repository.GroupsRepository;
import pl.urz.ssur_app.repository.UserGroupsRepository;
import pl.urz.ssur_app.repository.UsersRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    UsersRepository usersRepository;

    GroupsRepository groupsRepository;

    UserGroupsRepository userGroupsRepository;

    @Override
    public UserDetails loadUserByUsername(final String identifier) throws UsernameNotFoundException {
        User user;
        try{
            Integer userID = Integer.parseInt(identifier);
            user = usersRepository.findOneById(userID)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + identifier));
        } catch (NumberFormatException e) {
            user = usersRepository.findOneByEmail(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + identifier));
        }

        List<Groups> userGroupList = groupsRepository.getNamesByUsrId(user.getId());
        user.setGroups(new HashSet<>(userGroupList));

        return new UserDetailsImpl(user);
    }

    @Autowired
    public void setGroupsRepository(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Autowired
    public void setUserGroupsRepository(UserGroupsRepository userGroupsRepository) {
        this.userGroupsRepository = userGroupsRepository;
    }

    @Autowired
    public void setUsersRepository(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}