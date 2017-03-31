/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_POS_HUEditor_Filter
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_POS_HUEditor_Filter extends org.compiere.model.PO implements I_C_POS_HUEditor_Filter, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -947840637L;

    /** Standard Constructor */
    public X_C_POS_HUEditor_Filter (Properties ctx, int C_POS_HUEditor_Filter_ID, String trxName)
    {
      super (ctx, C_POS_HUEditor_Filter_ID, trxName);
      /** if (C_POS_HUEditor_Filter_ID == 0)
        {
			setAD_JavaClass_ID (0);
			setAD_Reference_ID (0);
			setC_POS_HUEditor_Filter_ID (0);
			setColumnName (null);
        } */
    }

    /** Load Constructor */
    public X_C_POS_HUEditor_Filter (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class);
	}

	@Override
	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass)
	{
		set_ValueFromPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class, AD_JavaClass);
	}

	/** Set AD_JavaClass.
		@param AD_JavaClass_ID AD_JavaClass	  */
	@Override
	public void setAD_JavaClass_ID (int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, Integer.valueOf(AD_JavaClass_ID));
	}

	/** Get AD_JavaClass.
		@return AD_JavaClass	  */
	@Override
	public int getAD_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

	/** Set Referenzschlüssel.
		@param AD_Reference_Value_ID 
		Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	@Override
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Referenzschlüssel.
		@return Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	@Override
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS HU Editor Filter.
		@param C_POS_HUEditor_Filter_ID POS HU Editor Filter	  */
	@Override
	public void setC_POS_HUEditor_Filter_ID (int C_POS_HUEditor_Filter_ID)
	{
		if (C_POS_HUEditor_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_HUEditor_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_HUEditor_Filter_ID, Integer.valueOf(C_POS_HUEditor_Filter_ID));
	}

	/** Get POS HU Editor Filter.
		@return POS HU Editor Filter	  */
	@Override
	public int getC_POS_HUEditor_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_HUEditor_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name der Spalte in der Datenbank
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get Spaltenname.
		@return Name der Spalte in der Datenbank
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
	}
}