// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Filter extends org.compiere.model.PO implements I_ES_FTS_Filter, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1201811022L;

    /** Standard Constructor */
    public X_ES_FTS_Filter (final Properties ctx, final int ES_FTS_Filter_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Filter_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Filter (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Config getES_FTS_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Config_ID, de.metas.elasticsearch.model.I_ES_FTS_Config.class);
	}

	@Override
	public void setES_FTS_Config(final de.metas.elasticsearch.model.I_ES_FTS_Config ES_FTS_Config)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Config_ID, de.metas.elasticsearch.model.I_ES_FTS_Config.class, ES_FTS_Config);
	}

	@Override
	public void setES_FTS_Config_ID (final int ES_FTS_Config_ID)
	{
		if (ES_FTS_Config_ID < 1) 
			set_Value (COLUMNNAME_ES_FTS_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ES_FTS_Config_ID, ES_FTS_Config_ID);
	}

	@Override
	public int getES_FTS_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_ID);
	}

	@Override
	public void setES_FTS_Filter_ID (final int ES_FTS_Filter_ID)
	{
		if (ES_FTS_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Filter_ID, ES_FTS_Filter_ID);
	}

	@Override
	public int getES_FTS_Filter_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Filter_ID);
	}
}