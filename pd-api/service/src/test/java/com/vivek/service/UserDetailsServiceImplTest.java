package com.vivek.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.Test;

import com.vivek.dao.domain.Role;
import com.vivek.dao.domain.User;
import com.vivek.dao.repository.UserRepository;
import com.vivek.service.dto.RoleDto;
import com.vivek.service.dto.UserDto;
import com.vivek.service.impl.UserDetailsServiceImpl;

/**
 * The Class UserDetailsServiceImplTest.
 */
public class UserDetailsServiceImplTest extends AbstractBaseMockitoTest {

	/** The testee. */
	@InjectMocks
	UserDetailsServiceImpl testee;

	/** The user repository. */
	@Mock
	private UserRepository userRepository;

	/** The converter. */
	@Mock
	private ConversionService converter;

	/**
	 * Test load user by username.
	 */
	@Test
	public void testLoadUserByUsername() {
		User user = new User();
		user.setUserName("testUser");
		user.setUserFirstName("firstName");
		user.setUserLastName("lastName");
		user.setUserPassword("password");
		user.setRole(new Role());
		user.getRole().setRoleName("ADMIN");
		when(userRepository.findOneByUserNameIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(user));
		UserDetails userDetails = testee.loadUserByUsername("testUser");
		verify(userRepository, atLeast(1)).findOneByUserNameIgnoreCase(Mockito.anyString());
		assertNotNull(userDetails);
	}

	/**
	 * Test load user by username user not found.
	 */
	@Test(expectedExceptions = UsernameNotFoundException.class)
	public void testLoadUserByUsernameUserNotFound() {
		User user = new User();
		user.setUserName("testUser");
		user.setUserFirstName("firstName");
		user.setUserLastName("lastName");
		user.setUserPassword("password");
		user.setRole(new Role());
		user.getRole().setRoleName("ADMIN");
		when(userRepository.findOneByUserNameIgnoreCase(Mockito.anyString()))
				.thenThrow(new UsernameNotFoundException("test"));
		testee.loadUserByUsername("testUser");
	}

	/**
	 * Test save user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSaveUser() throws Exception {
		User user = new User();
		user.setUserName("testUser");
		user.setUserFirstName("firstName");
		user.setUserLastName("lastName");
		user.setUserPassword("password");
		user.setRole(new Role());
		user.getRole().setRoleName("ADMIN");
		UserDto userDto = new UserDto("testUserName", "first Name", "Last NAme", Instant.now(), new RoleDto());
		userDto.setUserPassword("password");
		when(converter.convert(userDto, User.class)).thenReturn(user);
		when(userRepository.save(Mockito.any())).thenReturn(user);
		when(converter.convert(user, UserDto.class)).thenReturn(userDto);
		UserDto userDto2 = testee.saveUser(userDto);
		assertNotNull(userDto2);
		verify(converter, atLeast(1)).convert(Mockito.any(), Mockito.any());
		verify(userRepository, atLeast(1)).save(Mockito.any());
	}

	/**
	 * Test get users.
	 */
	@Test
	public void testGetUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User());
		when(converter.convert(users.get(0), UserDto.class))
				.thenReturn(new UserDto("testUserName", "first Name", "Last NAme", Instant.now(), new RoleDto()));
		when(userRepository.findAll()).thenReturn(users);
		List<UserDto> userDtos = testee.getUsers();
		verify(converter, atLeast(1)).convert(Mockito.any(), Mockito.any());
		verify(userRepository, atLeast(1)).findAll();
		assertNotNull(userDtos);
		assertEquals(userDtos.size(), 1);
	}

	/**
	 * Test get user.
	 */
	@Test
	public void testGetUser() {
		User user = new User();
		user.setUserName("testUser");
		user.setUserFirstName("firstName");
		user.setUserLastName("lastName");
		user.setUserPassword("password");
		user.setRole(new Role());
		user.getRole().setRoleName("ADMIN");
		when(converter.convert(Mockito.any(), Mockito.any()))
				.thenReturn(new UserDto("testUserName", "first Name", "Last NAme", Instant.now(), new RoleDto()));
		when(userRepository.findOneByUserNameIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(user));
		UserDto userDto = testee.getUser("testUser");
		verify(converter, atLeast(1)).convert(Mockito.any(), Mockito.any());
		verify(userRepository, atLeast(1)).findOneByUserNameIgnoreCase(Mockito.any());
		assertNotNull(userDto);
	}

	/**
	 * Test delete user.
	 */
	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setUserName("testUser");
		user.setUserFirstName("firstName");
		user.setUserLastName("lastName");
		user.setUserPassword("password");
		user.setRole(new Role());
		user.getRole().setRoleName("ADMIN");
		when(userRepository.findOneByUserNameIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(user));
		testee.deleteUser("testUser");
		verify(userRepository, atLeast(1)).findOneByUserNameIgnoreCase(Mockito.any());
		verify(userRepository, atLeast(1)).delete(Mockito.any());
	}
}
