package src.lil.controllers;

import java.sql.SQLException;

import src.lil.Enums.Role;

public interface LoginCont {
	Role check_user(Integer id,String password) throws SQLException;
}
