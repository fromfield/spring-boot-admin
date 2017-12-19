package com.github.shiro;

import com.github.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

	private int hashIterations = 2;
	private String algorithmName = "md5";

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	
	public String generateSalt() {
		return RandomStringUtils.randomAlphanumeric(6);
	}
	
	public String encryptPassword(String password, String salt) {
		return new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
	}

	public User encryptPassword(User user) {
		String salt = generateSalt();
		user.setSalt(salt);
		String saltPassword = encryptPassword(user.getPassword(), salt);
		user.setPassword(saltPassword);
		return user;
	}
	
	public static void main(String[] args) {
		
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = randomNumberGenerator.nextBytes().toHex();
		System.err.println(salt);

		System.err.println(new PasswordService().encryptPassword("123456", "iMiracle"));

		//667c6f301844caa395463df00895f829
		//667c6f301844caa395463df00895f829

	}
}
