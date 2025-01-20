// Generated Model - DO NOT CHANGE
package de.metas.datev.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DATEV_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DATEV_Export extends org.compiere.model.PO implements I_DATEV_Export, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -302052408L;

    /** Standard Constructor */
    public X_DATEV_Export (final Properties ctx, final int DATEV_Export_ID, @Nullable final String trxName)
    {
      super (ctx, DATEV_Export_ID, trxName);
    }

    /** Load Constructor */
    public X_DATEV_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDateAcctFrom (final @Nullable java.sql.Timestamp DateAcctFrom)
	{
		set_Value (COLUMNNAME_DateAcctFrom, DateAcctFrom);
	}

	@Override
	public java.sql.Timestamp getDateAcctFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcctFrom);
	}

	@Override
	public void setDateAcctTo (final java.sql.Timestamp DateAcctTo)
	{
		set_Value (COLUMNNAME_DateAcctTo, DateAcctTo);
	}

	@Override
	public java.sql.Timestamp getDateAcctTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcctTo);
	}

	@Override
	public void setDATEV_Export_ID (final int DATEV_Export_ID)
	{
		if (DATEV_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, DATEV_Export_ID);
	}

	@Override
	public int getDATEV_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_Export_ID);
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

	@Override
	public void setIsExcludeAlreadyExported (final boolean IsExcludeAlreadyExported)
	{
		set_Value (COLUMNNAME_IsExcludeAlreadyExported, IsExcludeAlreadyExported);
	}

	@Override
	public boolean isExcludeAlreadyExported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeAlreadyExported);
	}

	@Override
	public void setIsNegateInboundAmounts (final boolean IsNegateInboundAmounts)
	{
		set_Value (COLUMNNAME_IsNegateInboundAmounts, IsNegateInboundAmounts);
	}

	@Override
	public boolean isNegateInboundAmounts() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNegateInboundAmounts);
	}

	@Override
	public void setIsSwitchCreditMemo (final boolean IsSwitchCreditMemo)
	{
		set_Value (COLUMNNAME_IsSwitchCreditMemo, IsSwitchCreditMemo);
	}

	@Override
	public boolean isSwitchCreditMemo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSwitchCreditMemo);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}