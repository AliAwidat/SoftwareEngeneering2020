package src.lil.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.lil.Enums.Role;
import src.lil.common.DBConnection;
import src.lil.controllers.LoginCont;

public class Login implements LoginCont {

	public Role check_user(Integer id, String password) throws SQLException {
		try (Connection db = DBConnection.getInstance().getConnection();
				PreparedStatement preparedStatement = db.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
				PreparedStatement preparedStatement2 = db.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement2.setInt(1, id);
			try (ResultSet res = preparedStatement.executeQuery()) {
				if (res.next()) {
					String pw = res.getString("client_password");
					if (pw.equals(password)) {
						return Role.Client;
					}
				}
			}
			try (ResultSet res = preparedStatement2.executeQuery()) {
				if (res.next()) {
					String pw = res.getString("user_password");
					if (pw.equals(password)) {
						return Role.valueOf(res.getString("user_role"));
					}
				}
			}
			db.close();
		}
		return Role.Unregistered;
	}

}
