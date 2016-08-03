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
	private static final long serialVersionUID = -688368404L;

    /** Standard Constructor */
    public X_AD_UI_Column (Properties ctx, int AD_UI_Column_ID, String trxName)
    {
      super (ctx, AD_UI_Column_ID, trxName);
      /** if (AD_UI_Column_ID == 0)
        {
			setAD_UI_Column_ID (0);
			setAD_UI_Section_ID (0);
			setUIStyle (null);
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

	/** 
	 * UIStyle AD_Reference_ID=540664
	 * Reference name: AD_UI_Column_UIStyle
	 */
	public static final int UISTYLE_AD_Reference_ID=540664;
	/** Left = L */
	public static final String UISTYLE_Left = "L";
	/** Right = R */
	public static final String UISTYLE_Right = "R";
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