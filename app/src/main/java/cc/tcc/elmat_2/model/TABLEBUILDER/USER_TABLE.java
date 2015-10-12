package cc.tcc.elmat_2.model.TABLEBUILDER;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Erich on 11/10/2015.
 */
public class USER_TABLE extends TABLE_BUILDER implements BaseColumns {

    public USER_TABLE() {
        TABLE_NAME = "USER";
        ArrayList<ColumnType> lista = new ArrayList<ColumnType>();
        lista.add(new ColumnType("UserID", INTEGER_TYPE));
        lista.add(new ColumnType("FacebookID", BIGINT_TYPE));
        Columns = lista;
    }
}