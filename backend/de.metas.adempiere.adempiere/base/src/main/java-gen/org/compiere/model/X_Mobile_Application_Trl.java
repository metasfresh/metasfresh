// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Mobile_Application_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Mobile_Application_Trl extends org.compiere.model.PO implements I_Mobile_Application_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1942149417L;

    /** Standard Constructor */
    public X_Mobile_Application_Trl (final Properties ctx, final int Mobile_Application_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, Mobile_Application_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_Mobile_Application_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription_Customized (final @Nullable java.lang.String Description_Customized)
	{
		set_Value (COLUMNNAME_Description_Customized, Description_Customized);
	}

	@Override
	public java.lang.String getDescription_Customized() 
	{
		return get_ValueAsString(COLUMNNAME_Description_Customized);
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
	public void setIsUseCustomization (final boolean IsUseCustomization)
	{
		set_Value (COLUMNNAME_IsUseCustomization, IsUseCustomization);
	}

	@Override
	public boolean isUseCustomization() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseCustomization);
	}

	@Override
	public void setMobile_Application_ID (final int Mobile_Application_ID)
	{
		if (Mobile_Application_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, Mobile_Application_ID);
	}

	@Override
	public int getMobile_Application_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_ID);
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
	public void setName_Customized (final @Nullable java.lang.String Name_Customized)
	{
		set_Value (COLUMNNAME_Name_Customized, Name_Customized);
	}

	@Override
	public java.lang.String getName_Customized() 
	{
		return get_ValueAsString(COLUMNNAME_Name_Customized);
	}
}