package src.lil.models;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import src.lil.Enums.Role;

public class LoginTest extends TestCase {

	@Test
	public void test() throws SQLException {
		Login new_login = new Login();
		Assert.assertTrue(new_login.check_user(4444, "4066")== Role.Client);
		Assert.assertTrue(new_login.check_user(4444, "6")== Role.Unregistered);
		Assert.assertTrue(new_login.check_user(31, "")==Role.Employee);
		Assert.assertTrue(new_login.check_user(1, "")==Role.ChainManger);
	}

}
