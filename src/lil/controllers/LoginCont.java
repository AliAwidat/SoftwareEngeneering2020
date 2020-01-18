package src.lil.controllers;

import java.sql.SQLException;
import java.util.Map;

import src.lil.Enums.LoginStatus;
import src.lil.Enums.Role;
import src.lil.exceptions.*;

public interface LoginCont {
	public LoginStatus user_login(Integer id,String password,Map<Integer,Object> connected_map);
	public Role check_user(Integer id,String password) throws SQLException, WrongCredentials;
	public LoginStatus user_logout(Integer id,Map<Integer,Object> connected_map);
}
