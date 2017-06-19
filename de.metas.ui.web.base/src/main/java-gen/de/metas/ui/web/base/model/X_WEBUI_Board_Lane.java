/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_Lane
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Board_Lane extends org.compiere.model.PO implements I_WEBUI_Board_Lane, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1796327006L;

    /** Standard Constructor */
    public X_WEBUI_Board_Lane (Properties ctx, int WEBUI_Board_Lane_ID, String trxName)
    {
      super (ctx, WEBUI_Board_Lane_ID, trxName);
      /** if (WEBUI_Board_Lane_ID == 0)
        {
			setName (null);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(z.SeqNo), 0) + 10 FROM WEBUI_Board_Lane z where z.WEBUI_Board_ID=@WEBUI_Board_ID@
			setWEBUI_Board_ID (0);
			setWEBUI_Board_Lane_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Board_Lane (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Board lane.
		@param WEBUI_Board_Lane_ID Board lane	  */
	@Override
	public void setWEBUI_Board_Lane_ID (int WEBUI_Board_Lane_ID)
	{
		if (WEBUI_Board_Lane_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, Integer.valueOf(WEBUI_Board_Lane_ID));
	}

	/** Get Board lane.
		@return Board lane	  */
	@Override
	public int getWEBUI_Board_Lane_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_Lane_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}