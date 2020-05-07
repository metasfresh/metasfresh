/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Board extends org.compiere.model.PO implements I_WEBUI_Board, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 870288944L;

    /** Standard Constructor */
    public X_WEBUI_Board (Properties ctx, int WEBUI_Board_ID, String trxName)
    {
      super (ctx, WEBUI_Board_ID, trxName);
      /** if (WEBUI_Board_ID == 0)
        {
			setAD_Table_ID (0);
			setName (null);
			setWEBUI_Board_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Board (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	/** Set Dynamische Validierung.
		@param AD_Val_Rule_ID 
		Regel für die  dynamische Validierung
	  */
	@Override
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamische Validierung.
		@return Regel für die  dynamische Validierung
	  */
	@Override
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
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