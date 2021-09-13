// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Config_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Config_Field extends org.compiere.model.PO implements I_ES_FTS_Config_Field, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 947220712L;

    /** Standard Constructor */
    public X_ES_FTS_Config_Field (final Properties ctx, final int ES_FTS_Config_Field_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Config_Field_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Config_Field (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setES_FieldName (final java.lang.String ES_FieldName)
	{
		set_Value (COLUMNNAME_ES_FieldName, ES_FieldName);
	}

	@Override
	public java.lang.String getES_FieldName() 
	{
		return get_ValueAsString(COLUMNNAME_ES_FieldName);
	}

	@Override
	public void setES_FTS_Config_Field_ID (final int ES_FTS_Config_Field_ID)
	{
		if (ES_FTS_Config_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_Field_ID, ES_FTS_Config_Field_ID);
	}

	@Override
	public int getES_FTS_Config_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_Field_ID);
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
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Config_ID, ES_FTS_Config_ID);
	}

	@Override
	public int getES_FTS_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_ID);
	}
}