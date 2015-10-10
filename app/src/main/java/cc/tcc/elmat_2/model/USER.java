package cc.tcc.elmat_2.model;

/**
 * Created by erich on 10/10/2015.
 */
public class USER {
    private int UserID;
    private double FacebookID;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public double getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(double facebookID) {
        FacebookID = facebookID;
    }

    public USER(int userID, double facebookID) {
        UserID = userID;
        FacebookID = facebookID;
    }
}
