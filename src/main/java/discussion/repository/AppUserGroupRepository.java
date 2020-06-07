package discussion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import discussion.model.AppUserGroup;

public interface AppUserGroupRepository extends JpaRepository<AppUserGroup, Long>{

	List<AppUserGroup> findByAppUsername(String username);

}
