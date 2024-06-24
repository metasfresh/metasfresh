// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HazardSymbol_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HazardSymbol_Trl extends org.compiere.model.PO implements I_M_HazardSymbol_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1659347890L;

    /** Standard Constructor */
    public X_M_HazardSymbol_Trl (final Properties ctx, final int M_HazardSymbol_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, M_HazardSymbol_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HazardSymbol_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_HazardSymbol getM_HazardSymbol()
	{
		return get_ValueAsPO(COLUMNNAME_M_HazardSymbol_ID, org.compiere.model.I_M_HazardSymbol.class);
	}

	@Override
	public void setM_HazardSymbol(final org.compiere.model.I_M_HazardSymbol M_HazardSymbol)
	{
		set_ValueFromPO(COLUMNNAME_M_HazardSymbol_ID, org.compiere.model.I_M_HazardSymbol.class, M_HazardSymbol);
	}

	@Override
	public void setM_HazardSymbol_ID (final int M_HazardSymbol_ID)
	{
		if (M_HazardSymbol_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HazardSymbol_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HazardSymbol_ID, M_HazardSymbol_ID);
	}

	@Override
	public int getM_HazardSymbol_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HazardSymbol_ID);
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