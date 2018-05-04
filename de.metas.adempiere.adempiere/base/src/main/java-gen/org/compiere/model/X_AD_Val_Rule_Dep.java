/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Val_Rule_Dep
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Val_Rule_Dep extends org.compiere.model.PO implements I_AD_Val_Rule_Dep, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1948215174L;

    /** Standard Constructor */
    public X_AD_Val_Rule_Dep (Properties ctx, int AD_Val_Rule_Dep_ID, String trxName)
    {
      super (ctx, AD_Val_Rule_Dep_ID, trxName);
      /** if (AD_Val_Rule_Dep_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_Val_Rule_Dep_ID (0);
			setAD_Val_Rule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Val_Rule_Dep (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Validation Rule Depends On.
		@param AD_Val_Rule_Dep_ID Validation Rule Depends On	  */
	@Override
	public void setAD_Val_Rule_Dep_ID (int AD_Val_Rule_Dep_ID)
	{
		if (AD_Val_Rule_Dep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_Dep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_Dep_ID, Integer.valueOf(AD_Val_Rule_Dep_ID));
	}

	/** Get Validation Rule Depends On.
		@return Validation Rule Depends On	  */
	@Override
	public int getAD_Val_Rule_Dep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_Dep_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
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

	/** Set Technical note.
		@param TechnicalNote 
		A note that is not indended for the user documentation, but for developers, customizers etc
	  */
	@Override
	public void setTechnicalNote (java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	/** Get Technical note.
		@return A note that is not indended for the user documentation, but for developers, customizers etc
	  */
	@Override
	public java.lang.String getTechnicalNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TechnicalNote);
	}
}