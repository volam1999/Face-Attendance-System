package com.tpcop.DataAPI.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.model.ATModel;
import com.tpcop.DataAPI.model.LogModel;

public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsernameOrEmailIgnoreCase(String username, String email);

	User findById(long id);

	List<User> findByUsernameIsNot(String username);

	User findByUsernameOrEmailAndPasswordIgnoreCase(String username, String email, String password);

	long countByStatus(int status);

	long countDistinctRackByDepartmentId(long id);

	@Query("select new com.tpcop.DataAPI.model.ATModel(u.id,u.firstname,u.lastname, t.dayAtWorks) FROM User u INNER JOIN TimeIn t ON u.id = t.uid")
	List<ATModel> getAllATModels();

	@Query("select new com.tpcop.DataAPI.model.LogModel(u.id, l.id, l.detectedTime, u.username, u.firstname,u.lastname) FROM User u INNER JOIN Log l ON u.id = l.uid")
	List<LogModel> getLogModel();
}
