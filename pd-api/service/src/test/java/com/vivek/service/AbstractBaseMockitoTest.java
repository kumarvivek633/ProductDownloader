package com.vivek.service;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

/**
 * The Class AbstractBaseMockitoTest.
 */
public abstract class AbstractBaseMockitoTest {

	/**
	 * reset all mocks before test case.
	 */
	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
	}
}
