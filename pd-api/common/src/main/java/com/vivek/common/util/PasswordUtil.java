package com.vivek.common.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The Class PasswordUtil.
 */
public class PasswordUtil {
	
	/** The Constant iterations. */
	private static final int iterations = 20 * 1000;
	
	/** The Constant saltLen. */
	private static final int saltLen = 32;
	
	/** The Constant desiredKeyLen. */
	private static final int desiredKeyLen = 256;

	/**
	 * Gets the salted hash.
	 *
	 * @param password the password
	 * @return the salted hash
	 * @throws Exception the exception
	 */
	public static String getSaltedHash(String password) throws Exception {
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
			return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
	}

	/**
	 * Check.
	 *
	 * @param password the password
	 * @param stored the stored
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean check(String password, String stored) throws Exception {
		String[] saltAndHash = stored.split("\\$");
		if (saltAndHash.length != 2) {
			throw new IllegalStateException("The stored password must have the form 'salt$hash'");
		}
		String hashOfInput = hash(password, Base64.getDecoder().decode(saltAndHash[0]));
		return hashOfInput.equals(saltAndHash[1]);
	}

	/**
	 * Hash.
	 *
	 * @param password the password
	 * @param salt the salt
	 * @return the string
	 * @throws Exception the exception
	 */
	private static String hash(String password, byte[] salt) throws Exception {
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException("Empty passwords are not supported.");
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}