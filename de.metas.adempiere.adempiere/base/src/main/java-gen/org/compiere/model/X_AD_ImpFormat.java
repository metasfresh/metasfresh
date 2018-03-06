/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ImpFormat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_ImpFormat extends org.compiere.model.PO implements I_AD_ImpFormat, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1422151824L;

    /** Standard Constructor */
    public X_AD_ImpFormat (Properties ctx, int AD_ImpFormat_ID, String trxName)
    {
      super (ctx, AD_ImpFormat_ID, trxName);
      /** if (AD_ImpFormat_ID == 0)
        {
			setAD_ImpFormat_ID (0);
			setAD_Table_ID (0);
			setFormatType (null);
			setIsMultiLine (false); // N
			setName (null);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_AD_ImpFormat (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * FormatType AD_Reference_ID=209
	 * Reference name: AD_ImpFormat FormatType
	 */
	public static final int FORMATTYPE_AD_Reference_ID=209;
	/** Fixed Position = F */
	public static final String FORMATTYPE_FixedPosition = "F";
	/** CommaSeparated = C */
	public static final String FORMATTYPE_CommaSeparated = "C";
	/** TabSeparated = T */
	public static final String FORMATTYPE_TabSeparated = "T";
	/** XML = X */
	public static final String FORMATTYPE_XML = "X";
	/** SemicolonSeparated = S */
	public static final String FORMATTYPE_SemicolonSeparated = "S";
	/** Set Format.
		@param FormatType 
		Format of the data
	  */
	@Override
	public void setFormatType (java.lang.String FormatType)
	{

		set_Value (COLUMNNAME_FormatType, FormatType);
	}

	/** Get Format.
		@return Format of the data
	  */
	@Override
	public java.lang.String getFormatType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FormatType);
	}

	/** Set Multi Line.
		@param IsMultiLine Multi Line	  */
	@Override
	public void setIsMultiLine (boolean IsMultiLine)
	{
		set_Value (COLUMNNAME_IsMultiLine, Boolean.valueOf(IsMultiLine));
	}

	/** Get Multi Line.
		@return Multi Line	  */
	@Override
	public boolean isMultiLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}