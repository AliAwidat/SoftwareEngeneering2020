package src.lil.models;

public abstract class User {
    protected int userId;
    protected String name;
    protected String phone;
    protected String bankAccount;
    protected boolean isBlocked;
    protected String email;
    protected String password;
    protected String storeId;

    public User(int userId){
        this.userId = userId;
    }
    public User(int userId, String name, String phone, String bankAccount,String email, String password, String storeId){
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.email = email;
        this.password = password;
        this.isBlocked = false;
        this.storeId = storeId;
    }

    public abstract boolean register() throws Exception;

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsConnected() {
        return isBlocked;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
