// Generated Model - DO NOT CHANGE
package de.metas.marketing.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_User_MKTG_Channels
 *  @author metasfresh (generated) 
 */
public class X_AD_User_MKTG_Channels extends org.compiere.model.PO implements I_AD_User_MKTG_Channels, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1439654629L;

    /** Standard Constructor */
    public X_AD_User_MKTG_Channels (final Properties ctx, final int AD_User_MKTG_Channels_ID, @Nullable final String trxName)
    {
      super (ctx, AD_User_MKTG_Channels_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_User_MKTG_Channels (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_MKTG_Channels_ID (final int AD_User_MKTG_Channels_ID)
	{
		if (AD_User_MKTG_Channels_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_MKTG_Channels_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_MKTG_Channels_ID, AD_User_MKTG_Channels_ID);
	}

	@Override
	public int getAD_User_MKTG_Channels_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_MKTG_Channels_ID);
	}

	@Override
	public de.metas.marketing.base.model.I_MKTG_Channel getMKTG_Channel()
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_Channel_ID, de.metas.marketing.base.model.I_MKTG_Channel.class);
	}

	@Override
	public void setMKTG_Channel(final de.metas.marketing.base.model.I_MKTG_Channel MKTG_Channel)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_Channel_ID, de.metas.marketing.base.model.I_MKTG_Channel.class, MKTG_Channel);
	}

	@Override
	public void setMKTG_Channel_ID (final int MKTG_Channel_ID)
	{
		if (MKTG_Channel_ID < 1) 
			set_Value (COLUMNNAME_MKTG_Channel_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Channel_ID, MKTG_Channel_ID);
	}

	@Override
	public int getMKTG_Channel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_Channel_ID);
	}
}