package cc.tcc.elmat_2.model.TABLEBUILDER;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by erich on 24/10/2015.
 */
public class RIDE_TABLE extends TABLE_BUILDER implements BaseColumns {

    public RIDE_TABLE() {
        TABLE_NAME = "RIDE";
        ArrayList<TABLE_BUILDER.ColumnType> lista = new ArrayList<TABLE_BUILDER.ColumnType>();
        lista.add(new TABLE_BUILDER.ColumnType("RideID", INTEGER_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("UserID", INTEGER_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("Hour", TEXT_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("LatOrg", FLOAT_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("LonOrg", FLOAT_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("LatDest", FLOAT_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("LonDest", FLOAT_TYPE));

        lista.add(new TABLE_BUILDER.ColumnType("distanciaOrg", FLOAT_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("distanciaDes", FLOAT_TYPE));

        lista.add(new TABLE_BUILDER.ColumnType("classOrg", INTEGER_TYPE));
        lista.add(new TABLE_BUILDER.ColumnType("classDes", INTEGER_TYPE));

        Columns = lista;
    }
}
