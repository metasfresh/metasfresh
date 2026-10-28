// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PromotionCode
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_PromotionCode extends org.compiere.model.PO implements I_C_PromotionCode, org.compiere.model.I_Persistent
{
	private static final long serialVersionUID = 542586L;

	/** Standard Constructor */
	public X_C_PromotionCode (final Properties ctx, final int C_PromotionCode_ID, @Nullable final String trxName)
	{
		super (ctx, C_PromotionCode_ID, trxName);
	}

	/** Load Constructor */
	public X_C_PromotionCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_PromotionCode_ID (final int C_PromotionCode_ID)
	{
		if (C_PromotionCode_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PromotionCode_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PromotionCode_ID, C_PromotionCode_ID);
	}

	@Override
	public int getC_PromotionCode_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_PromotionCode_ID);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}
