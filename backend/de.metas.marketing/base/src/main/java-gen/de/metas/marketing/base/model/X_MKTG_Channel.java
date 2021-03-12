// Generated Model - DO NOT CHANGE
package de.metas.marketing.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MKTG_Channel
 *  @author metasfresh (generated) 
 */
public class X_MKTG_Channel extends org.compiere.model.PO implements I_MKTG_Channel, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1964952074L;

    /** Standard Constructor */
    public X_MKTG_Channel (final Properties ctx, final int MKTG_Channel_ID, @Nullable final String trxName)
    {
      super (ctx, MKTG_Channel_ID, trxName);
    }

    /** Load Constructor */
    public X_MKTG_Channel (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setMKTG_Channel_ID (final int MKTG_Channel_ID)
	{
		if (MKTG_Channel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Channel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Channel_ID, MKTG_Channel_ID);
	}

	@Override
	public int getMKTG_Channel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_Channel_ID);
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