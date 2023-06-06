package pl.urz.ssur_app.repository;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pl.urz.ssur_app.model.UserGroups;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserGroupsRepository {
    List<UserGroups> getAll();

    Optional<UserGroups> findOneByGroupId(@Param("id") Integer grpId);
}
