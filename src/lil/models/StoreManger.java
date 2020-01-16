package src.lil.models;

import src.lil.Enums.ReportType;
import src.lil.Enums.Role;

public class StoreManger extends Employee {
    public StoreManger(int userId) {
        super(userId);
    }

    public StoreManger(int userId, String name, String phone, String bankAccount, String email, String password, Role role, String store_id) {
        super(userId, name, phone, bankAccount, email, password, role, store_id);
    }

    public void viewReport(ReportType reportType){

    }
}
