package src.lil.models;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class LoginTest {

	@Test
	public void test() throws SQLException {
		Login new_login = new Login();
		Assert.assertTrue(new_login.check_user(4444, "4066")== 0);
		Assert.assertTrue(new_login.check_user(4444, "6")== -1);
		Assert.assertTrue(new_login.check_user(31, "")==1);
	}

}
