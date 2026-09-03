// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CostClassification_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CostClassification_Trl extends org.compiere.model.PO implements I_C_CostClassification_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 309024709L;

    /** Standard Constructor */
    public X_C_CostClassification_Trl (final Properties ctx, final int C_CostClassification_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, C_CostClassification_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CostClassification_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final java.lang.String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setC_CostClassification_ID (final int C_CostClassification_ID)
	{
		if (C_CostClassification_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CostClassification_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CostClassification_ID, C_CostClassification_ID);
	}

	@Override
	public int getC_CostClassification_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CostClassification_ID);
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
	public void setIsTranslated (final boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, IsTranslated);
	}

	@Override
	public boolean isTranslated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslated);
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
}