package src.lil.controllers;

import java.sql.SQLException;

public interface LoginCont {
	int check_user(Integer id,String password) throws SQLException;
}
