package cc.tcc.elmat_2.messages;

/**
 * Created by erich on 23/10/2015.
 */
public enum ClassifiCarona {
    NULL(0), VERDE(1), AMARELO(2), VERMELHO(3);

    private final int value;
    private ClassifiCarona(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
