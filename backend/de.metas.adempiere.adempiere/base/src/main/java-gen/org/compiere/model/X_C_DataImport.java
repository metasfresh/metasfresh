// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DataImport
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_DataImport extends org.compiere.model.PO implements I_C_DataImport, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1991628502L;

    /** Standard Constructor */
    public X_C_DataImport (final Properties ctx, final int C_DataImport_ID, @Nullable final String trxName)
    {
      super (ctx, C_DataImport_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DataImport (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class);
	}

	@Override
	public void setAD_ImpFormat(final org.compiere.model.I_AD_ImpFormat AD_ImpFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class, AD_ImpFormat);
	}

	@Override
	public void setAD_ImpFormat_ID (final int AD_ImpFormat_ID)
	{
		if (AD_ImpFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_ImpFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ImpFormat_ID, AD_ImpFormat_ID);
	}

	@Override
	public int getAD_ImpFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ImpFormat_ID);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	/** 
	 * DataImport_ConfigType AD_Reference_ID=541535
	 * Reference name: C_DataImport_ConfigType
	 */
	public static final int DATAIMPORT_CONFIGTYPE_AD_Reference_ID=541535;
	/** Standard = S */
	public static final String DATAIMPORT_CONFIGTYPE_Standard = "S";
	/** Bank Statement Import = BSI */
	public static final String DATAIMPORT_CONFIGTYPE_BankStatementImport = "BSI";
	@Override
	public void setDataImport_ConfigType (final java.lang.String DataImport_ConfigType)
	{
		set_Value (COLUMNNAME_DataImport_ConfigType, DataImport_ConfigType);
	}

	@Override
	public java.lang.String getDataImport_ConfigType() 
	{
		return get_ValueAsString(COLUMNNAME_DataImport_ConfigType);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}
}