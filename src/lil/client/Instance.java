package src.lil.client;


import javafx.beans.property.SimpleBooleanProperty;

import src.lil.Enums.Role;



public class Instance {
    public static ClientConsole clientConsole;

    private static User currentUser = null;

    private static Object currentUser = null;

    private static String response = null;
    private static Object tempReturnValue = null;
    private static String jsonReturnValue = null;

    public static boolean isVarListener() {
        return varListener.get();
    }

    public static SimpleBooleanProperty varListenerProperty() {
        return varListener;
    }

    public static void setVarListener(boolean varListener) {
        Instance.varListener.set(varListener);
    }

    private static SimpleBooleanProperty varListener = new SimpleBooleanProperty(false);
    public static Object getTempReturnValue() {
        Object temp = tempReturnValue;
        resetTempReturnValue();
        return temp;
    }

    public static void setTempReturnValue(Object tempReturnValue) {
        resetTempReturnValue();
        Instance.tempReturnValue = tempReturnValue;
    }

    public static void resetTempReturnValue(){
        tempReturnValue = null;
    }

    public static ClientConsole getClientConsole() {
        return clientConsole;
    }

    public static void setClientConsole(ClientConsole clientConsole) {
        Instance.clientConsole = clientConsole;
    }



    public static void setCurrentUser(Object currentUser) {

        Instance.currentUser = currentUser;
        if (Instance.isVarListener()){
            Instance.varListenerProperty().set(false);
        } else {
            Instance.varListenerProperty().set(true);
        }
    }

    public static String getResponse() {
        return response;
    }

    public static void resetResponse(){
        Instance.response = null;
    }

    public static void setResponse(String response) {
        Instance.response = response;
    }

    public static String getJsonReturnValue() {
        return jsonReturnValue;
    }

    public static void setJsonReturnValue(String jsonReturnValue) {
        Instance.jsonReturnValue = jsonReturnValue;
    }
}
