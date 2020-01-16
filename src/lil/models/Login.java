package src.lil.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import src.lil.Enums.LoginStatus;
import src.lil.Enums.Role;
import src.lil.common.DBConnection;
import src.lil.controllers.LoginCont;
import src.lil.exceptions.AlreadyLoggedIn;
import src.lil.exceptions.NotSignedIn;
import src.lil.exceptions.WrongCredentials;

public class Login implements LoginCont {
	public LoginStatus user_login(Integer id, String password, Map<Integer, Object> connected_map) {
		// checks if the id is in the connected users vectors.
		try {
			check_connected_users(id, connected_map);
		} catch (Exception e) {
			return LoginStatus.AlreadyIn;
		}
		try {
			Role user_role = check_user(id, password);
			connect_user(id,user_role,connected_map);
		} catch (Exception e) {
			if(e.getMessage().equals("Wrong user ID or password!")) {
				return LoginStatus.WrongCrad;
			}
			return LoginStatus.SQLfailed;
		}
		return LoginStatus.Successful;
	}
	/**
	 * This method checks if the entered credentials match with those in the DB.
	 */
	public Role check_user(Integer id, String password) throws SQLException, WrongCredentials {
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
		throw new WrongCredentials();
	}
	/**
	 * This method signs out a connected user.
	 * (can't use this method unless the user is connected).
	 */
	public LoginStatus user_logout(Integer id,Map<Integer,Object> connected_map) {
		try {
			disconnect_user(id, connected_map);
		} catch (NotSignedIn e) {
			return LoginStatus.NotSignedIn;
		}
		return LoginStatus.Successful;
	}

	/**
	 * This method checks if a user is already connected
	 */
	public Boolean check_connected_users(Integer _id, Map<Integer, Object> connected_users) throws AlreadyLoggedIn {
		if (connected_users.containsKey(_id)) {
			throw new AlreadyLoggedIn();
		}
		return false;
	}

	/**
	 * This method adds the user to the connected users after successful logging in.
	 */
	public void connect_user(Integer _id, Object _role, Map<Integer, Object> connected_users) {
		connected_users.put(_id, _role);
	}

	/**
	 * This method removes a connected user after logging out.
	 */
	public void disconnect_user(Integer _id, Map<Integer, Object> connected_users) throws NotSignedIn {
		if(!connected_users.containsKey(_id)) {
			throw new NotSignedIn();
		}
		connected_users.remove(_id);
	}

}
