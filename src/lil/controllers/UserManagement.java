package src.lil.controllers;

import src.lil.Enums.Role;
import src.lil.models.Client;
import src.lil.models.Employee;
import src.lil.models.User;

public interface UserManagement {
    Employee getEmployeeById(int userId);
    Client getClientById(int userId);
    boolean DeleteUser(int userId, boolean isClient);
    boolean updateUser(Object updatedUser);
    boolean setUserAbilityToOrder(int userId, boolean isBlock);
    boolean changeRule(int userId, Role role);
//    boolean insertUser(User user);
}

