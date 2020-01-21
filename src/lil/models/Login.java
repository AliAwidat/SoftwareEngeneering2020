package src.lil.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import src.lil.Enums.LoginStatus;
import src.lil.Enums.Role;
import src.lil.common.DBConnection;
import src.lil.controllers.LoginCont;
import src.lil.exceptions.AlreadyLoggedIn;
import src.lil.exceptions.NotSignedIn;
import src.lil.exceptions.WrongCredentials;

public class Login implements LoginCont {
	/**
	 * map of connected server.
	 */
	private Map<Integer, Object> connected_users;

	/**
	 * Constructor
	 */

	public Login() {
		connected_users = new HashMap<Integer, Object>();
	}

	/**
	 * This method signs in a user
	 * 
	 * @param id : user id to sign in password : user's password connected_map :
	 *           data structurer to save which users are already connected
	 * @return Login status depending in the result.
	 */
	public LoginStatus user_login(Integer id, String password) {

		try {
			check_connected_users(id);
			connect_user(id, check_user(id, password));
		} catch (WrongCredentials e) {
			return LoginStatus.WrongCrad;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AlreadyLoggedIn e) {
			return LoginStatus.AlreadyIn;
		}
		return LoginStatus.Successful;
	}

	/**
	 * This method checks if the entered credentials match with those in the DB.
	 */
	public Object check_user(Integer id, String password) throws SQLException, WrongCredentials {
		try (Connection db = DBConnection.getInstance().getConnection();
				PreparedStatement preparedStatement = db.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
				PreparedStatement preparedStatement2 = db.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement2.setInt(1, id);
			try (ResultSet res = preparedStatement.executeQuery()) {
				if (res.next()) {
					String pw = res.getString("client_password");
					if (pw.equals(password)) {
						return new Client(res);
					}
				}
			}
			try (ResultSet res = preparedStatement2.executeQuery()) {
				if (res.next()) {
					String pw = res.getString("user_password");
					if (pw.equals(password)) {
						if (Role.valueOf(res.getString("user_role")) == Role.Employee) {
							return new Employee(res);
						} else if (Role.valueOf(res.getString("user_role")) == Role.StoreManger) {
							return new StoreManger(res);
						} else if (Role.valueOf(res.getString("user_role")) == Role.ChainManger) {
							return new ChainManger(res);
						}

					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			db.close();
		}
		throw new WrongCredentials();
	}

	/**
	 * gets the user's role
	 */
//	public Role get_role(Integer id) {
//		//return connected_users;
//	}
	/**
	 * This method signs out a connected user. (can't use this method unless the
	 * user is connected).
	 */
	public LoginStatus user_logout(Integer id) {
		try {
			disconnect_user(id);
		} catch (NotSignedIn e) {
			return LoginStatus.NotSignedIn;
		}
		return LoginStatus.Successful;
	}

	/**
	 * This method checks if a user is already connected
	 */
	public Boolean check_connected_users(Integer _id) throws AlreadyLoggedIn {
		if (this.connected_users.containsKey(_id)) {
			throw new AlreadyLoggedIn();
		}
		return false;
	}

	/**
	 * This method adds the user to the connected users after successful logging in.
	 */
	public void connect_user(Integer _id, Object user) {
		this.connected_users.put(_id, user);
	}

	/**
	 * This method removes a connected user after logging out.
	 */
	public void disconnect_user(Integer _id) throws NotSignedIn {
		if (!this.connected_users.containsKey(_id)) {
			throw new NotSignedIn();
		}
		this.connected_users.remove(_id);
	}

}
