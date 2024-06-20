// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Message_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Message_Trl extends org.compiere.model.PO implements I_AD_Message_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -923986697L;

    /** Standard Constructor */
    public X_AD_Message_Trl (final Properties ctx, final int AD_Message_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Message_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Message_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Message_ID (final int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Message_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Message_ID, AD_Message_ID);
	}

	@Override
	public int getAD_Message_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Message_ID);
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
	public void setMsgText (final java.lang.String MsgText)
	{
		set_Value (COLUMNNAME_MsgText, MsgText);
	}

	@Override
	public java.lang.String getMsgText() 
	{
		return get_ValueAsString(COLUMNNAME_MsgText);
	}

	@Override
	public void setMsgTip (final @Nullable java.lang.String MsgTip)
	{
		set_Value (COLUMNNAME_MsgTip, MsgTip);
	}

	@Override
	public java.lang.String getMsgTip() 
	{
		return get_ValueAsString(COLUMNNAME_MsgTip);
	}
}