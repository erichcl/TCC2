package cc.tcc.elmat_2.messages;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

import cc.tcc.elmat_2.model.USER;

/**
 * Created by erich on 20/10/2015.
 */
public class User{
        public long FacebookID;
        public int UserID;
        public String Name;
        public int RelationStatus;


    public User() {
    }

    public User(USER U)
    {
        this.FacebookID = Math.round(U.FacebookID);
        this.UserID = U.UserID;
        this.Name = U.Name;
    }
}
