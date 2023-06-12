package pl.urz.ssur_app.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pl.urz.ssur_app.model.UserGroup;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserGroupsRepository {
    List<UserGroup> getAll();

    Optional<UserGroup> findOneByGroupId(@Param("id") Integer grpId);

    List<Integer> findAllGrpIdByUserId(@Param("id") Integer usrId);
}
