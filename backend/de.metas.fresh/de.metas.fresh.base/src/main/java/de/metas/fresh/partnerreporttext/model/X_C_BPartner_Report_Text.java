// Generated Model - DO NOT CHANGE
package de.metas.fresh.partnerreporttext.model;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_BPartner_Report_Text
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Report_Text extends org.compiere.model.PO implements I_C_BPartner_Report_Text, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 998540969L;

	/**
	 * Standard Constructor
	 */
	public X_C_BPartner_Report_Text(final Properties ctx, final int C_BPartner_Report_Text_ID, @Nullable final String trxName)
	{
		super(ctx, C_BPartner_Report_Text_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_C_BPartner_Report_Text(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAdditionalText(final @Nullable String AdditionalText)
	{
		set_Value(COLUMNNAME_AdditionalText, AdditionalText);
	}

	@Override
	public @NotNull String getAdditionalText()
	{
		return get_ValueAsString(COLUMNNAME_AdditionalText);
	}

	@Override
	public I_C_BPartner_DocType getC_BPartner_DocType()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_DocType_ID, I_C_BPartner_DocType.class);
	}

	@Override
	public void setC_BPartner_DocType(final I_C_BPartner_DocType C_BPartner_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_DocType_ID, I_C_BPartner_DocType.class, C_BPartner_DocType);
	}

	@Override
	public void setC_BPartner_DocType_ID(final int C_BPartner_DocType_ID)
	{
		if (C_BPartner_DocType_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_DocType_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_DocType_ID, C_BPartner_DocType_ID);
	}

	@Override
	public int getC_BPartner_DocType_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_DocType_ID);
	}

	@Override
	public void setC_BPartner_ID(final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Report_Text_ID(final int C_BPartner_Report_Text_ID)
	{
		if (C_BPartner_Report_Text_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_BPartner_Report_Text_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_BPartner_Report_Text_ID, C_BPartner_Report_Text_ID);
	}

	@Override
	public int getC_BPartner_Report_Text_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Report_Text_ID);
	}

	@Override
	public void setValue(final String Value)
	{
		set_Value(COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}
