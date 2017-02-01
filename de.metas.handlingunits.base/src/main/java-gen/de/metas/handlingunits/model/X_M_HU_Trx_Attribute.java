/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_HU_Trx_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Trx_Attribute extends org.compiere.model.PO implements I_M_HU_Trx_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -808427137L;

    /** Standard Constructor */
    public X_M_HU_Trx_Attribute (Properties ctx, int M_HU_Trx_Attribute_ID, String trxName)
    {
      super (ctx, M_HU_Trx_Attribute_ID, trxName);
      /** if (M_HU_Trx_Attribute_ID == 0)
        {
			setM_Attribute_ID (0);
			setM_HU_Trx_Attribute_ID (0);
			setM_HU_Trx_Hdr_ID (0);
			setOperation (null);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_M_HU_Trx_Attribute (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

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

	@Override
	public de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class);
	}

	@Override
	public void setM_HU_Attribute(de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class, M_HU_Attribute);
	}

	/** Set Handling Unit Attribute.
		@param M_HU_Attribute_ID Handling Unit Attribute	  */
	@Override
	public void setM_HU_Attribute_ID (int M_HU_Attribute_ID)
	{
		if (M_HU_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Attribute_ID, Integer.valueOf(M_HU_Attribute_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Attribute getM_HU_PI_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Attribute_ID, de.metas.handlingunits.model.I_M_HU_PI_Attribute.class);
	}

	@Override
	public void setM_HU_PI_Attribute(de.metas.handlingunits.model.I_M_HU_PI_Attribute M_HU_PI_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Attribute_ID, de.metas.handlingunits.model.I_M_HU_PI_Attribute.class, M_HU_PI_Attribute);
	}

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

	/** Set Handling Units Transaction Attribute.
		@param M_HU_Trx_Attribute_ID Handling Units Transaction Attribute	  */
	@Override
	public void setM_HU_Trx_Attribute_ID (int M_HU_Trx_Attribute_ID)
	{
		if (M_HU_Trx_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Attribute_ID, Integer.valueOf(M_HU_Trx_Attribute_ID));
	}

	/** Get Handling Units Transaction Attribute.
		@return Handling Units Transaction Attribute	  */
	@Override
	public int getM_HU_Trx_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
	}

	@Override
	public void setM_HU_Trx_Hdr(de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class, M_HU_Trx_Hdr);
	}

	/** Set HU-Transaktionskopf.
		@param M_HU_Trx_Hdr_ID HU-Transaktionskopf	  */
	@Override
	public void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, Integer.valueOf(M_HU_Trx_Hdr_ID));
	}

	/** Get HU-Transaktionskopf.
		@return HU-Transaktionskopf	  */
	@Override
	public int getM_HU_Trx_Hdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setM_HU_Trx_Line(de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, M_HU_Trx_Line);
	}

	/** Set HU-Transaktionszeile.
		@param M_HU_Trx_Line_ID HU-Transaktionszeile	  */
	@Override
	public void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, Integer.valueOf(M_HU_Trx_Line_ID));
	}

	/** Get HU-Transaktionszeile.
		@return HU-Transaktionszeile	  */
	@Override
	public int getM_HU_Trx_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Operation AD_Reference_ID=540410
	 * Reference name: M_HU_PI_Attribute_Operation
	 */
	public static final int OPERATION_AD_Reference_ID=540410;
	/** Save = SAVE */
	public static final String OPERATION_Save = "SAVE";
	/** Drop = DROP */
	public static final String OPERATION_Drop = "DROP";
	/** Set Arbeitsvorgang .
		@param Operation 
		Database Operation
	  */
	@Override
	public void setOperation (java.lang.String Operation)
	{

		set_Value (COLUMNNAME_Operation, Operation);
	}

	/** Get Arbeitsvorgang .
		@return Database Operation
	  */
	@Override
	public java.lang.String getOperation () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Operation);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
		set_Value (COLUMNNAME_ValueInitial, ValueInitial);
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
			 return Env.ZERO;
		return bd;
	}

	/** Set Zahlwert (initial).
		@param ValueNumberInitial 
		Initial Numeric Value
	  */
	@Override
	public void setValueNumberInitial (java.math.BigDecimal ValueNumberInitial)
	{
		set_Value (COLUMNNAME_ValueNumberInitial, ValueNumberInitial);
	}

	/** Get Zahlwert (initial).
		@return Initial Numeric Value
	  */
	@Override
	public java.math.BigDecimal getValueNumberInitial () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumberInitial);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}