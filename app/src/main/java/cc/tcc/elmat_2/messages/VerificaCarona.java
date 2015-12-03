package cc.tcc.elmat_2.messages;

/**
 * Created by erich on 02/12/2015.
 */
public class VerificaCarona {
    public Ride Ride;
    public User User;

    public VerificaCarona()
    {
        Ride = new Ride();
        User = new User();
    }
}
