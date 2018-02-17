/** Generated Model - DO NOT CHANGE */
package de.metas.datev.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DATEV_ExportFormat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DATEV_ExportFormat extends org.compiere.model.PO implements I_DATEV_ExportFormat, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -730053407L;

    /** Standard Constructor */
    public X_DATEV_ExportFormat (Properties ctx, int DATEV_ExportFormat_ID, String trxName)
    {
      super (ctx, DATEV_ExportFormat_ID, trxName);
      /** if (DATEV_ExportFormat_ID == 0)
        {
			setCSVEncoding (null);
			setCSVFieldDelimiter (null);
			setDATEV_ExportFormat_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_DATEV_ExportFormat (Properties ctx, ResultSet rs, String trxName)
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

	/** Set CSV Encoding.
		@param CSVEncoding CSV Encoding	  */
	@Override
	public void setCSVEncoding (java.lang.String CSVEncoding)
	{
		set_Value (COLUMNNAME_CSVEncoding, CSVEncoding);
	}

	/** Get CSV Encoding.
		@return CSV Encoding	  */
	@Override
	public java.lang.String getCSVEncoding () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CSVEncoding);
	}

	/** Set CSV Field Delimiter.
		@param CSVFieldDelimiter CSV Field Delimiter	  */
	@Override
	public void setCSVFieldDelimiter (java.lang.String CSVFieldDelimiter)
	{
		set_Value (COLUMNNAME_CSVFieldDelimiter, CSVFieldDelimiter);
	}

	/** Get CSV Field Delimiter.
		@return CSV Field Delimiter	  */
	@Override
	public java.lang.String getCSVFieldDelimiter () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CSVFieldDelimiter);
	}

	/** Set CSV Field Quote.
		@param CSVFieldQuote CSV Field Quote	  */
	@Override
	public void setCSVFieldQuote (java.lang.String CSVFieldQuote)
	{
		set_Value (COLUMNNAME_CSVFieldQuote, CSVFieldQuote);
	}

	/** Get CSV Field Quote.
		@return CSV Field Quote	  */
	@Override
	public java.lang.String getCSVFieldQuote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CSVFieldQuote);
	}

	/** Set DATEV Export Format.
		@param DATEV_ExportFormat_ID DATEV Export Format	  */
	@Override
	public void setDATEV_ExportFormat_ID (int DATEV_ExportFormat_ID)
	{
		if (DATEV_ExportFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportFormat_ID, Integer.valueOf(DATEV_ExportFormat_ID));
	}

	/** Get DATEV Export Format.
		@return DATEV Export Format	  */
	@Override
	public int getDATEV_ExportFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DATEV_ExportFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Decimal Separator.
		@param DecimalSeparator Decimal Separator	  */
	@Override
	public void setDecimalSeparator (java.lang.String DecimalSeparator)
	{
		set_Value (COLUMNNAME_DecimalSeparator, DecimalSeparator);
	}

	/** Get Decimal Separator.
		@return Decimal Separator	  */
	@Override
	public java.lang.String getDecimalSeparator () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DecimalSeparator);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
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

	/** Set Number Grouping Separator.
		@param NumberGroupingSeparator Number Grouping Separator	  */
	@Override
	public void setNumberGroupingSeparator (java.lang.String NumberGroupingSeparator)
	{
		set_Value (COLUMNNAME_NumberGroupingSeparator, NumberGroupingSeparator);
	}

	/** Get Number Grouping Separator.
		@return Number Grouping Separator	  */
	@Override
	public java.lang.String getNumberGroupingSeparator () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NumberGroupingSeparator);
	}
}