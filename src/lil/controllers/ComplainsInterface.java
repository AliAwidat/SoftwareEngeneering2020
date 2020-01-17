package src.lil.controllers;

import java.io.IOException;
import java.sql.SQLException;

import src.lil.models.Order.NotFound;

public interface ComplainsInterface {

	public void addComplain(String complainTitle, String complainText, String email, String phone, String adress)throws SQLException, NotFound, IOException;
	public void viewComplains()throws NotFound, IOException;
}
