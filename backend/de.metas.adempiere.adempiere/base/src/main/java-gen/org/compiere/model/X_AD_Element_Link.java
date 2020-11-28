/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Element_Link
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Element_Link extends org.compiere.model.PO implements I_AD_Element_Link, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1146869607L;

    /** Standard Constructor */
    public X_AD_Element_Link (Properties ctx, int AD_Element_Link_ID, String trxName)
    {
      super (ctx, AD_Element_Link_ID, trxName);
      /** if (AD_Element_Link_ID == 0)
        {
			setAD_Element_Link_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Element_Link (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	/** Set System-Element.
		@param AD_Element_ID 
		Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System-Element.
		@return Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Element_Link.
		@param AD_Element_Link_ID AD_Element_Link	  */
	@Override
	public void setAD_Element_Link_ID (int AD_Element_Link_ID)
	{
		if (AD_Element_Link_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Element_Link_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Element_Link_ID, Integer.valueOf(AD_Element_Link_ID));
	}

	/** Get AD_Element_Link.
		@return AD_Element_Link	  */
	@Override
	public int getAD_Element_Link_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_Link_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Field getAD_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class);
	}

	@Override
	public void setAD_Field(org.compiere.model.I_AD_Field AD_Field)
	{
		set_ValueFromPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class, AD_Field);
	}

	/** Set Feld.
		@param AD_Field_ID 
		Ein Feld einer Datenbanktabelle
	  */
	@Override
	public void setAD_Field_ID (int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_Value (COLUMNNAME_AD_Field_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Field_ID, Integer.valueOf(AD_Field_ID));
	}

	/** Get Feld.
		@return Ein Feld einer Datenbanktabelle
	  */
	@Override
	public int getAD_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tab getAD_Tab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

	/** Set Register.
		@param AD_Tab_ID 
		Register auf einem Fenster
	  */
	@Override
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_Value (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	/** Get Register.
		@return Register auf einem Fenster
	  */
	@Override
	public int getAD_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	/** Set Fenster.
		@param AD_Window_ID 
		Eingabe- oder Anzeige-Fenster
	  */
	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Fenster.
		@return Eingabe- oder Anzeige-Fenster
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}