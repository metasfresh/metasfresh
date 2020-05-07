/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_CardField
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Board_CardField extends org.compiere.model.PO implements I_WEBUI_Board_CardField, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1970032538L;

    /** Standard Constructor */
    public X_WEBUI_Board_CardField (Properties ctx, int WEBUI_Board_CardField_ID, String trxName)
    {
      super (ctx, WEBUI_Board_CardField_ID, trxName);
      /** if (WEBUI_Board_CardField_ID == 0)
        {
			setAD_Column_ID (0);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(z.SeqNo), 0) + 10 FROM WEBUI_Board_CardField z WHERE z.WEBUI_Board_ID=@WEBUI_Board_ID@
			setWEBUI_Board_CardField_ID (0);
			setWEBUI_Board_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Board_CardField (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Spalte in der Tabelle
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Spalte in der Tabelle
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Board card field.
		@param WEBUI_Board_CardField_ID Board card field	  */
	@Override
	public void setWEBUI_Board_CardField_ID (int WEBUI_Board_CardField_ID)
	{
		if (WEBUI_Board_CardField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_CardField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_CardField_ID, Integer.valueOf(WEBUI_Board_CardField_ID));
	}

	/** Get Board card field.
		@return Board card field	  */
	@Override
	public int getWEBUI_Board_CardField_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_CardField_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Board getWEBUI_Board() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class);
	}

	@Override
	public void setWEBUI_Board(de.metas.ui.web.base.model.I_WEBUI_Board WEBUI_Board)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class, WEBUI_Board);
	}

	/** Set Board.
		@param WEBUI_Board_ID Board	  */
	@Override
	public void setWEBUI_Board_ID (int WEBUI_Board_ID)
	{
		if (WEBUI_Board_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, Integer.valueOf(WEBUI_Board_ID));
	}

	/** Get Board.
		@return Board	  */
	@Override
	public int getWEBUI_Board_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}