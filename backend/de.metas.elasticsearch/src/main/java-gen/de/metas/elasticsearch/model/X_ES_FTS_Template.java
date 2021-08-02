// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Template
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Template extends org.compiere.model.PO implements I_ES_FTS_Template, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -477600663L;

    /** Standard Constructor */
    public X_ES_FTS_Template (final Properties ctx, final int ES_FTS_Template_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Template_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Template (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setES_FTS_Settings (final @Nullable java.lang.String ES_FTS_Settings)
	{
		set_Value (COLUMNNAME_ES_FTS_Settings, ES_FTS_Settings);
	}

	@Override
	public java.lang.String getES_FTS_Settings() 
	{
		return get_ValueAsString(COLUMNNAME_ES_FTS_Settings);
	}

	@Override
	public void setES_FTS_Template_ID (final int ES_FTS_Template_ID)
	{
		if (ES_FTS_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Template_ID, ES_FTS_Template_ID);
	}

	@Override
	public int getES_FTS_Template_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Template_ID);
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