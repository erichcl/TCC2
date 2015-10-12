package cc.tcc.elmat_2.model.TABLEBUILDER;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Erich on 11/10/2015.
 */
public class TABLE_BUILDER
{

    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER";
    protected static final String BIGINT_TYPE = " BIGINT";
    protected static final String COMMA_SEP = ",";

    protected class ColumnType
    {
        public String CName;
        public String CType;

        public ColumnType() {
        }

        public ColumnType(String name, String type) {
            this.CName = name;
            this.CType = type;
        }
    }

    public String TABLE_NAME;
    public ArrayList<ColumnType> Columns;

    public TABLE_BUILDER() {}

    public String CREATE_TABLE()
    {
        String SQL = "CREATE TABLE " +TABLE_NAME + " (";
        for (int i = 0; i < Columns.size(); i++)
        {
            SQL += Columns.get(i).CName + Columns.get(i).CType + COMMA_SEP;
        }
        SQL = SQL.substring(0, SQL.length()-1);
        SQL += " )";
        return SQL;
    }

    public String DROP_TABLE()
    {
        String SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return SQL;
    }
}


