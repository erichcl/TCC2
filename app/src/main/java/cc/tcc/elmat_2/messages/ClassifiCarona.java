package cc.tcc.elmat_2.messages;

/**
 * Created by erich on 23/10/2015.
 */
public enum ClassifiCarona {
    VERDE(0), AMARELO(1), VERMELHO(2);

    private final int value;
    private ClassifiCarona(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
