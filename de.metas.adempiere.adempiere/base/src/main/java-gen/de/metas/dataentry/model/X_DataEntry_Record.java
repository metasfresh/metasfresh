/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Record
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Record extends org.compiere.model.PO implements I_DataEntry_Record, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1393467211L;

    /** Standard Constructor */
    public X_DataEntry_Record (Properties ctx, int DataEntry_Record_ID, String trxName)
    {
      super (ctx, DataEntry_Record_ID, trxName);
      /** if (DataEntry_Record_ID == 0)
        {
			setDataEntry_Field_ID (0);
			setDataEntry_Record_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Record (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.dataentry.model.I_DataEntry_Field getDataEntry_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class);
	}

	@Override
	public void setDataEntry_Field(de.metas.dataentry.model.I_DataEntry_Field DataEntry_Field)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class, DataEntry_Field);
	}

	/** Set Dateneingabefeld.
		@param DataEntry_Field_ID Dateneingabefeld	  */
	@Override
	public void setDataEntry_Field_ID (int DataEntry_Field_ID)
	{
		if (DataEntry_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, Integer.valueOf(DataEntry_Field_ID));
	}

	/** Get Dateneingabefeld.
		@return Dateneingabefeld	  */
	@Override
	public int getDataEntry_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DataEntry_Record.
		@param DataEntry_Record_ID DataEntry_Record	  */
	@Override
	public void setDataEntry_Record_ID (int DataEntry_Record_ID)
	{
		if (DataEntry_Record_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Record_ID, Integer.valueOf(DataEntry_Record_ID));
	}

	/** Get DataEntry_Record.
		@return DataEntry_Record	  */
	@Override
	public int getDataEntry_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Stringwert.
		@param ValueStr Stringwert	  */
	@Override
	public void setValueStr (java.lang.String ValueStr)
	{
		set_Value (COLUMNNAME_ValueStr, ValueStr);
	}

	/** Get Stringwert.
		@return Stringwert	  */
	@Override
	public java.lang.String getValueStr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueStr);
	}

	/** 
	 * ValueYesNo AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int VALUEYESNO_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String VALUEYESNO_Yes = "Y";
	/** No = N */
	public static final String VALUEYESNO_No = "N";
	/** Set Ja/Nein-Wert.
		@param ValueYesNo Ja/Nein-Wert	  */
	@Override
	public void setValueYesNo (java.lang.String ValueYesNo)
	{

		set_Value (COLUMNNAME_ValueYesNo, ValueYesNo);
	}

	/** Get Ja/Nein-Wert.
		@return Ja/Nein-Wert	  */
	@Override
	public java.lang.String getValueYesNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueYesNo);
	}
}