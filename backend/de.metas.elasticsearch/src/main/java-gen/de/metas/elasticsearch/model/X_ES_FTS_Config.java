// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Config extends org.compiere.model.PO implements I_ES_FTS_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -889446780L;

    /** Standard Constructor */
    public X_ES_FTS_Config (final Properties ctx, final int ES_FTS_Config_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setES_CreateIndexCommand (final java.lang.String ES_CreateIndexCommand)
	{
		set_Value (COLUMNNAME_ES_CreateIndexCommand, ES_CreateIndexCommand);
	}

	@Override
	public java.lang.String getES_CreateIndexCommand() 
	{
		return get_ValueAsString(COLUMNNAME_ES_CreateIndexCommand);
	}

	@Override
	public void setES_DocumentToIndexTemplate (final java.lang.String ES_DocumentToIndexTemplate)
	{
		set_Value (COLUMNNAME_ES_DocumentToIndexTemplate, ES_DocumentToIndexTemplate);
	}

	@Override
	public java.lang.String getES_DocumentToIndexTemplate() 
	{
		return get_ValueAsString(COLUMNNAME_ES_DocumentToIndexTemplate);
	}

	@Override
	public void setES_FTS_Config_ID (final int ES_FTS_Config_ID)
	{
		if (ES_FTS_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_ID, ES_FTS_Config_ID);
	}

	@Override
	public int getES_FTS_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_ID);
	}

	@Override
	public void setES_Index (final java.lang.String ES_Index)
	{
		set_Value (COLUMNNAME_ES_Index, ES_Index);
	}

	@Override
	public java.lang.String getES_Index() 
	{
		return get_ValueAsString(COLUMNNAME_ES_Index);
	}

	@Override
	public void setES_QueryCommand (final java.lang.String ES_QueryCommand)
	{
		set_Value (COLUMNNAME_ES_QueryCommand, ES_QueryCommand);
	}

	@Override
	public java.lang.String getES_QueryCommand() 
	{
		return get_ValueAsString(COLUMNNAME_ES_QueryCommand);
	}
}