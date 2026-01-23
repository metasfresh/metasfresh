// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DocType_PrintOptions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_DocType_PrintOptions extends org.compiere.model.PO implements I_C_DocType_PrintOptions, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 458533581L;

    /** Standard Constructor */
    public X_C_DocType_PrintOptions (final Properties ctx, final int C_DocType_PrintOptions_ID, @Nullable final String trxName)
    {
      super (ctx, C_DocType_PrintOptions_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DocType_PrintOptions (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_DocType_PrintOptions_ID (final int C_DocType_PrintOptions_ID)
	{
		if (C_DocType_PrintOptions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_PrintOptions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_PrintOptions_ID, C_DocType_PrintOptions_ID);
	}

	@Override
	public int getC_DocType_PrintOptions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_PrintOptions_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocumentFlavor AD_Reference_ID=541225
	 * Reference name: C_DocType_PrintOptions_DocumentFlavor
	 */
	public static final int DOCUMENTFLAVOR_AD_Reference_ID=541225;
	/** EMail = E */
	public static final String DOCUMENTFLAVOR_EMail = "E";
	/** Print = P */
	public static final String DOCUMENTFLAVOR_Print = "P";
	@Override
	public void setDocumentFlavor (final @Nullable java.lang.String DocumentFlavor)
	{
		set_Value (COLUMNNAME_DocumentFlavor, DocumentFlavor);
	}

	@Override
	public java.lang.String getDocumentFlavor() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentFlavor);
	}

	@Override
	public void setPRINTER_OPTS_IsPrintLogo (final boolean PRINTER_OPTS_IsPrintLogo)
	{
		set_Value (COLUMNNAME_PRINTER_OPTS_IsPrintLogo, PRINTER_OPTS_IsPrintLogo);
	}

	@Override
	public boolean isPRINTER_OPTS_IsPrintLogo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PRINTER_OPTS_IsPrintLogo);
	}

	@Override
	public void setPRINTER_OPTS_IsPrintPrices (final boolean PRINTER_OPTS_IsPrintPrices)
	{
		set_Value (COLUMNNAME_PRINTER_OPTS_IsPrintPrices, PRINTER_OPTS_IsPrintPrices);
	}

	@Override
	public boolean isPRINTER_OPTS_IsPrintPrices() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PRINTER_OPTS_IsPrintPrices);
	}

	@Override
	public void setPRINTER_OPTS_IsPrintTotals (final boolean PRINTER_OPTS_IsPrintTotals)
	{
		set_Value (COLUMNNAME_PRINTER_OPTS_IsPrintTotals, PRINTER_OPTS_IsPrintTotals);
	}

	@Override
	public boolean isPRINTER_OPTS_IsPrintTotals() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PRINTER_OPTS_IsPrintTotals);
	}
}