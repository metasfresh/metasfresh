/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UI_Column
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UI_Column extends org.compiere.model.PO implements I_AD_UI_Column, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 191928515L;

    /** Standard Constructor */
    public X_AD_UI_Column (Properties ctx, int AD_UI_Column_ID, String trxName)
    {
      super (ctx, AD_UI_Column_ID, trxName);
      /** if (AD_UI_Column_ID == 0)
        {
			setAD_UI_Column_ID (0);
			setAD_UI_Section_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM AD_UI_Column where AD_UI_Column.AD_UI_Section_ID=@AD_UI_Section_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_UI_Column (Properties ctx, ResultSet rs, String trxName)
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

	/** Set UI Column.
		@param AD_UI_Column_ID UI Column	  */
	@Override
	public void setAD_UI_Column_ID (int AD_UI_Column_ID)
	{
		if (AD_UI_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Column_ID, Integer.valueOf(AD_UI_Column_ID));
	}

	/** Get UI Column.
		@return UI Column	  */
	@Override
	public int getAD_UI_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_UI_Section getAD_UI_Section() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_UI_Section_ID, org.compiere.model.I_AD_UI_Section.class);
	}

	@Override
	public void setAD_UI_Section(org.compiere.model.I_AD_UI_Section AD_UI_Section)
	{
		set_ValueFromPO(COLUMNNAME_AD_UI_Section_ID, org.compiere.model.I_AD_UI_Section.class, AD_UI_Section);
	}

	/** Set UI Section.
		@param AD_UI_Section_ID UI Section	  */
	@Override
	public void setAD_UI_Section_ID (int AD_UI_Section_ID)
	{
		if (AD_UI_Section_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Section_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Section_ID, Integer.valueOf(AD_UI_Section_ID));
	}

	/** Get UI Section.
		@return UI Section	  */
	@Override
	public int getAD_UI_Section_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_Section_ID);
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
}