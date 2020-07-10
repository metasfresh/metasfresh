/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UI_ElementGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UI_ElementGroup extends org.compiere.model.PO implements I_AD_UI_ElementGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 916265240L;

    /** Standard Constructor */
    public X_AD_UI_ElementGroup (Properties ctx, int AD_UI_ElementGroup_ID, String trxName)
    {
      super (ctx, AD_UI_ElementGroup_ID, trxName);
      /** if (AD_UI_ElementGroup_ID == 0)
        {
			setAD_UI_ElementGroup_ID (0);
			setName (null);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID=@AD_UI_Column_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_UI_ElementGroup (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_UI_Column getAD_UI_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_UI_Column_ID, org.compiere.model.I_AD_UI_Column.class);
	}

	@Override
	public void setAD_UI_Column(org.compiere.model.I_AD_UI_Column AD_UI_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_UI_Column_ID, org.compiere.model.I_AD_UI_Column.class, AD_UI_Column);
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

	/** Set UI Element Group.
		@param AD_UI_ElementGroup_ID UI Element Group	  */
	@Override
	public void setAD_UI_ElementGroup_ID (int AD_UI_ElementGroup_ID)
	{
		if (AD_UI_ElementGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, Integer.valueOf(AD_UI_ElementGroup_ID));
	}

	/** Get UI Element Group.
		@return UI Element Group	  */
	@Override
	public int getAD_UI_ElementGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_ElementGroup_ID);
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

	/** Set UI Style.
		@param UIStyle UI Style	  */
	@Override
	public void setUIStyle (java.lang.String UIStyle)
	{
		set_Value (COLUMNNAME_UIStyle, UIStyle);
	}

	/** Get UI Style.
		@return UI Style	  */
	@Override
	public java.lang.String getUIStyle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UIStyle);
	}
}