// Generated Model - DO NOT CHANGE
package de.metas.payment.sepa.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for SEPA_Export_Line_Ref
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SEPA_Export_Line_Ref extends org.compiere.model.PO implements I_SEPA_Export_Line_Ref, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -974662009L;

    /** Standard Constructor */
    public X_SEPA_Export_Line_Ref (final Properties ctx, final int SEPA_Export_Line_Ref_ID, @Nullable final String trxName)
    {
      super (ctx, SEPA_Export_Line_Ref_ID, trxName);
    }

    /** Load Constructor */
    public X_SEPA_Export_Line_Ref (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAmt (final BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	@Override
	public BigDecimal getAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSEPA_Export_ID (final int SEPA_Export_ID)
	{
		if (SEPA_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, SEPA_Export_ID);
	}

	@Override
	public int getSEPA_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SEPA_Export_ID);
	}

	@Override
	public void setSEPA_Export_Line_ID (final int SEPA_Export_Line_ID)
	{
		if (SEPA_Export_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_ID, SEPA_Export_Line_ID);
	}

	@Override
	public int getSEPA_Export_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SEPA_Export_Line_ID);
	}

	@Override
	public void setSEPA_Export_Line_Ref_ID (final int SEPA_Export_Line_Ref_ID)
	{
		if (SEPA_Export_Line_Ref_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_Ref_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_Line_Ref_ID, SEPA_Export_Line_Ref_ID);
	}

	@Override
	public int getSEPA_Export_Line_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SEPA_Export_Line_Ref_ID);
	}

	@Override
	public void setStructuredRemittanceInfo (final @Nullable String StructuredRemittanceInfo)
	{
		set_Value (COLUMNNAME_StructuredRemittanceInfo, StructuredRemittanceInfo);
	}

	@Override
	public String getStructuredRemittanceInfo() 
	{
		return get_ValueAsString(COLUMNNAME_StructuredRemittanceInfo);
	}
}