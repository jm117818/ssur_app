package pl.urz.ssur_app.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pl.urz.ssur_app.model.Groups;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface GroupsRepository {

    List<Groups> getAll();

    Optional<Groups> findOneByName (@Param("name") String name);
}
