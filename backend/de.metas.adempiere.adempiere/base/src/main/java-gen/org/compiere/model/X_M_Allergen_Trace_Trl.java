// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Allergen_Trace_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Allergen_Trace_Trl extends org.compiere.model.PO implements I_M_Allergen_Trace_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -692372308L;

    /** Standard Constructor */
    public X_M_Allergen_Trace_Trl (final Properties ctx, final int M_Allergen_Trace_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, M_Allergen_Trace_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Allergen_Trace_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_Allergen_Trace getM_Allergen_Trace()
	{
		return get_ValueAsPO(COLUMNNAME_M_Allergen_Trace_ID, org.compiere.model.I_M_Allergen_Trace.class);
	}

	@Override
	public void setM_Allergen_Trace(final org.compiere.model.I_M_Allergen_Trace M_Allergen_Trace)
	{
		set_ValueFromPO(COLUMNNAME_M_Allergen_Trace_ID, org.compiere.model.I_M_Allergen_Trace.class, M_Allergen_Trace);
	}

	@Override
	public void setM_Allergen_Trace_ID (final int M_Allergen_Trace_ID)
	{
		if (M_Allergen_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_Trace_ID, M_Allergen_Trace_ID);
	}

	@Override
	public int getM_Allergen_Trace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Allergen_Trace_ID);
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