/** Generated Model - DO NOT CHANGE */
package de.metas.datev.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DATEV_ExportFormatColumn
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DATEV_ExportFormatColumn extends org.compiere.model.PO implements I_DATEV_ExportFormatColumn, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2064030L;

    /** Standard Constructor */
    public X_DATEV_ExportFormatColumn (Properties ctx, int DATEV_ExportFormatColumn_ID, String trxName)
    {
      super (ctx, DATEV_ExportFormatColumn_ID, trxName);
      /** if (DATEV_ExportFormatColumn_ID == 0)
        {
			setAD_Column_ID (0);
			setDATEV_ExportFormat_ID (0);
			setDATEV_ExportFormatColumn_ID (0);
			setName (null);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_DATEV_ExportFormatColumn (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
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
		Spalte in der Tabelle
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
		@return Spalte in der Tabelle
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
	public de.metas.datev.model.I_DATEV_ExportFormat getDATEV_ExportFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DATEV_ExportFormat_ID, de.metas.datev.model.I_DATEV_ExportFormat.class);
	}

	@Override
	public void setDATEV_ExportFormat(de.metas.datev.model.I_DATEV_ExportFormat DATEV_ExportFormat)
	{
		set_ValueFromPO(COLUMNNAME_DATEV_ExportFormat_ID, de.metas.datev.model.I_DATEV_ExportFormat.class, DATEV_ExportFormat);
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

	/** Set DATEV Export Format Column.
		@param DATEV_ExportFormatColumn_ID DATEV Export Format Column	  */
	@Override
	public void setDATEV_ExportFormatColumn_ID (int DATEV_ExportFormatColumn_ID)
	{
		if (DATEV_ExportFormatColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportFormatColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportFormatColumn_ID, Integer.valueOf(DATEV_ExportFormatColumn_ID));
	}

	/** Get DATEV Export Format Column.
		@return DATEV Export Format Column	  */
	@Override
	public int getDATEV_ExportFormatColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DATEV_ExportFormatColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	@Override
	public void setFormatPattern (java.lang.String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	@Override
	public java.lang.String getFormatPattern () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FormatPattern);
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}