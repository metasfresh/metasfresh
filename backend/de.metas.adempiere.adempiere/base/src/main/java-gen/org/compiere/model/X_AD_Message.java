// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Message
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Message extends org.compiere.model.PO implements I_AD_Message, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -888647770L;

    /** Standard Constructor */
    public X_AD_Message (final Properties ctx, final int AD_Message_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Message_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Message (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setErrorCode (final @Nullable java.lang.String ErrorCode)
	{
		set_Value (COLUMNNAME_ErrorCode, ErrorCode);
	}

	@Override
	public java.lang.String getErrorCode() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorCode);
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

	/** 
	 * MsgType AD_Reference_ID=103
	 * Reference name: AD_Message Type
	 */
	public static final int MSGTYPE_AD_Reference_ID=103;
	/** Fehler = E */
	public static final String MSGTYPE_Fehler = "E";
	/** Information = I */
	public static final String MSGTYPE_Information = "I";
	/** Menü = M */
	public static final String MSGTYPE_Menue = "M";
	@Override
	public void setMsgType (final java.lang.String MsgType)
	{
		set_Value (COLUMNNAME_MsgType, MsgType);
	}

	@Override
	public java.lang.String getMsgType() 
	{
		return get_ValueAsString(COLUMNNAME_MsgType);
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