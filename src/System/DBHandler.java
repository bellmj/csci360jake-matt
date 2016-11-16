package System;

import java.util.List;

public abstract class DBHandler<T> implements DataHandler<T>{
    public abstract void DBHandler(String adminUserName,char[] adminPswd);
}
