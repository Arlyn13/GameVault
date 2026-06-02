package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.auth.LoginState;
import es.ulpgc.eite.da.advmasterdetail.auth.RegisterState;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class AuthMediator {

    private static AuthMediator INSTANCE;

    private RegisterState registerState;
    private LoginState loginState;
    private UserItem user;

    private AuthMediator() {

    }

    public static void resetInstance() {
        INSTANCE = null;
    }

    public static AuthMediator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthMediator();
        }

        return INSTANCE;
    }

    public RegisterState getRegisterState() {
        return registerState;
    }

    public void setRegisterState(RegisterState state) {
        registerState = state;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public void setLoginState(LoginState state) {
        loginState = state;
    }

    public UserItem getUser() {
        return user;
    }

    public void setUser(UserItem item) {
        user = item;
    }
}