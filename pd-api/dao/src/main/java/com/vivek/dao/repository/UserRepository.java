package com.vivek.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivek.dao.domain.User;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find one by user name ignore case.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	Optional<User> findOneByUserNameIgnoreCase(String userName);

}
