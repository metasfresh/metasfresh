/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Attribute extends org.compiere.model.PO implements I_M_HU_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1660606286L;

    /** Standard Constructor */
    public X_M_HU_Attribute (Properties ctx, int M_HU_Attribute_ID, String trxName)
    {
      super (ctx, M_HU_Attribute_ID, trxName);
      /** if (M_HU_Attribute_ID == 0)
        {
			setM_Attribute_ID (0);
			setM_HU_Attribute_ID (0);
			setM_HU_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Attribute (Properties ctx, ResultSet rs, String trxName)
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

//	@Override
//	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
//	}
//
//	@Override
//	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
//	{
//		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
//	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handling Unit Attribute.
		@param M_HU_Attribute_ID Handling Unit Attribute	  */
	@Override
	public void setM_HU_Attribute_ID (int M_HU_Attribute_ID)
	{
		if (M_HU_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, Integer.valueOf(M_HU_Attribute_ID));
	}

	/** Get Handling Unit Attribute.
		@return Handling Unit Attribute	  */
	@Override
	public int getM_HU_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

//	@Override
//	public de.metas.handlingunits.model.I_M_HU_PI_Attribute getM_HU_PI_Attribute() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Attribute_ID, de.metas.handlingunits.model.I_M_HU_PI_Attribute.class);
//	}
//
//	@Override
//	public void setM_HU_PI_Attribute(de.metas.handlingunits.model.I_M_HU_PI_Attribute M_HU_PI_Attribute)
//	{
//		set_ValueFromPO(COLUMNNAME_M_HU_PI_Attribute_ID, de.metas.handlingunits.model.I_M_HU_PI_Attribute.class, M_HU_PI_Attribute);
//	}

	/** Set Handling Units Packing Instructions Attribute.
		@param M_HU_PI_Attribute_ID Handling Units Packing Instructions Attribute	  */
	@Override
	public void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID)
	{
		if (M_HU_PI_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, Integer.valueOf(M_HU_PI_Attribute_ID));
	}

	/** Get Handling Units Packing Instructions Attribute.
		@return Handling Units Packing Instructions Attribute	  */
	@Override
	public int getM_HU_PI_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Datum.
		@param ValueDate Datum	  */
	@Override
	public void setValueDate (java.sql.Timestamp ValueDate)
	{
		set_Value (COLUMNNAME_ValueDate, ValueDate);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getValueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValueDate);
	}

	/** Set Datum (initial).
		@param ValueDateInitial Datum (initial)	  */
	@Override
	public void setValueDateInitial (java.sql.Timestamp ValueDateInitial)
	{
		set_Value (COLUMNNAME_ValueDateInitial, ValueDateInitial);
	}

	/** Get Datum (initial).
		@return Datum (initial)	  */
	@Override
	public java.sql.Timestamp getValueDateInitial () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValueDateInitial);
	}

	/** Set Merkmals-Wert (initial).
		@param ValueInitial 
		Initial Value of the Attribute
	  */
	@Override
	public void setValueInitial (java.lang.String ValueInitial)
	{
		set_ValueNoCheck (COLUMNNAME_ValueInitial, ValueInitial);
	}

	/** Get Merkmals-Wert (initial).
		@return Initial Value of the Attribute
	  */
	@Override
	public java.lang.String getValueInitial () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueInitial);
	}

	/** Set Zahlwert.
		@param ValueNumber 
		Numeric Value
	  */
	@Override
	public void setValueNumber (java.math.BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	/** Get Zahlwert.
		@return Numeric Value
	  */
	@Override
	public java.math.BigDecimal getValueNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumber);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zahlwert (initial).
		@param ValueNumberInitial 
		Initial Numeric Value
	  */
	@Override
	public void setValueNumberInitial (java.math.BigDecimal ValueNumberInitial)
	{
		set_ValueNoCheck (COLUMNNAME_ValueNumberInitial, ValueNumberInitial);
	}

	/** Get Zahlwert (initial).
		@return Initial Numeric Value
	  */
	@Override
	public java.math.BigDecimal getValueNumberInitial () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumberInitial);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}