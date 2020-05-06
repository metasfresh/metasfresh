/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ImpFormat_Row
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_ImpFormat_Row extends org.compiere.model.PO implements I_AD_ImpFormat_Row, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1451603552L;

    /** Standard Constructor */
    public X_AD_ImpFormat_Row (Properties ctx, int AD_ImpFormat_Row_ID, String trxName)
    {
      super (ctx, AD_ImpFormat_Row_ID, trxName);
      /** if (AD_ImpFormat_Row_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_ImpFormat_ID (0);
			setAD_ImpFormat_Row_ID (0);
			setDataType (null);
			setDecimalPoint (null); // .
			setDivideBy100 (false);
			setName (null);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_ImpFormat_Row WHERE AD_ImpFormat_ID=@AD_ImpFormat_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_ImpFormat_Row (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class);
	}

	@Override
	public void setAD_ImpFormat(org.compiere.model.I_AD_ImpFormat AD_ImpFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class, AD_ImpFormat);
	}

	/** Set Import-Format.
		@param AD_ImpFormat_ID Import-Format	  */
	@Override
	public void setAD_ImpFormat_ID (int AD_ImpFormat_ID)
	{
		if (AD_ImpFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_ID, Integer.valueOf(AD_ImpFormat_ID));
	}

	/** Get Import-Format.
		@return Import-Format	  */
	@Override
	public int getAD_ImpFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ImpFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Format-Feld.
		@param AD_ImpFormat_Row_ID Format-Feld	  */
	@Override
	public void setAD_ImpFormat_Row_ID (int AD_ImpFormat_Row_ID)
	{
		if (AD_ImpFormat_Row_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_Row_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_Row_ID, Integer.valueOf(AD_ImpFormat_Row_ID));
	}

	/** Get Format-Feld.
		@return Format-Feld	  */
	@Override
	public int getAD_ImpFormat_Row_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ImpFormat_Row_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Callout.
		@param Callout 
		Fully qualified class names and method - separated by semicolons
	  */
	@Override
	public void setCallout (java.lang.String Callout)
	{
		set_Value (COLUMNNAME_Callout, Callout);
	}

	/** Get Callout.
		@return Fully qualified class names and method - separated by semicolons
	  */
	@Override
	public java.lang.String getCallout () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Callout);
	}

	/** Set Konstante.
		@param ConstantValue 
		Constant value
	  */
	@Override
	public void setConstantValue (java.lang.String ConstantValue)
	{
		set_Value (COLUMNNAME_ConstantValue, ConstantValue);
	}

	/** Get Konstante.
		@return Constant value
	  */
	@Override
	public java.lang.String getConstantValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConstantValue);
	}

	/** Set Data Format.
		@param DataFormat 
		Format String in Java Notation, e.g. ddMMyy
	  */
	@Override
	public void setDataFormat (java.lang.String DataFormat)
	{
		set_Value (COLUMNNAME_DataFormat, DataFormat);
	}

	/** Get Data Format.
		@return Format String in Java Notation, e.g. ddMMyy
	  */
	@Override
	public java.lang.String getDataFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataFormat);
	}

	/** 
	 * DataType AD_Reference_ID=210
	 * Reference name: AD_ImpFormat_Row Type
	 */
	public static final int DATATYPE_AD_Reference_ID=210;
	/** String = S */
	public static final String DATATYPE_String = "S";
	/** Number = N */
	public static final String DATATYPE_Number = "N";
	/** Date = D */
	public static final String DATATYPE_Date = "D";
	/** Constant = C */
	public static final String DATATYPE_Constant = "C";
	/** YesNo = B */
	public static final String DATATYPE_YesNo = "B";
	/** Set Daten-Typ.
		@param DataType 
		Type of data
	  */
	@Override
	public void setDataType (java.lang.String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Daten-Typ.
		@return Type of data
	  */
	@Override
	public java.lang.String getDataType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataType);
	}

	/** Set Dezimal-Punkt.
		@param DecimalPoint 
		Decimal Point in the data file - if any
	  */
	@Override
	public void setDecimalPoint (java.lang.String DecimalPoint)
	{
		set_Value (COLUMNNAME_DecimalPoint, DecimalPoint);
	}

	/** Get Dezimal-Punkt.
		@return Decimal Point in the data file - if any
	  */
	@Override
	public java.lang.String getDecimalPoint () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DecimalPoint);
	}

	/** Set Durch 100 teilen.
		@param DivideBy100 
		Divide number by 100 to get correct amount
	  */
	@Override
	public void setDivideBy100 (boolean DivideBy100)
	{
		set_Value (COLUMNNAME_DivideBy100, Boolean.valueOf(DivideBy100));
	}

	/** Get Durch 100 teilen.
		@return Divide number by 100 to get correct amount
	  */
	@Override
	public boolean isDivideBy100 () 
	{
		Object oo = get_Value(COLUMNNAME_DivideBy100);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set End-Nr..
		@param EndNo End-Nr.	  */
	@Override
	public void setEndNo (int EndNo)
	{
		set_Value (COLUMNNAME_EndNo, Integer.valueOf(EndNo));
	}

	/** Get End-Nr..
		@return End-Nr.	  */
	@Override
	public int getEndNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EndNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Skript.
		@param Script 
		Dynamic Java Language Script to calculate result
	  */
	@Override
	public void setScript (java.lang.String Script)
	{
		set_Value (COLUMNNAME_Script, Script);
	}

	/** Get Skript.
		@return Dynamic Java Language Script to calculate result
	  */
	@Override
	public java.lang.String getScript () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Script);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start No.
		@param StartNo 
		Starting number/position
	  */
	@Override
	public void setStartNo (int StartNo)
	{
		set_Value (COLUMNNAME_StartNo, Integer.valueOf(StartNo));
	}

	/** Get Start No.
		@return Starting number/position
	  */
	@Override
	public int getStartNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StartNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}