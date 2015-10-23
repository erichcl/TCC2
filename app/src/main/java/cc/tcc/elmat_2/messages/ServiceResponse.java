package cc.tcc.elmat_2.messages;

/**
 * Created by erich on 23/10/2015.
 */
public class ServiceResponse <T> {
    public boolean  SUCCESS;
    public String  CODMENSAGEM;
    public T  RETORNO;
    public String  EXCEPTION;
}
