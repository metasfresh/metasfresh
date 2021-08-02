// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_IndexInclude
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_IndexInclude extends org.compiere.model.PO implements I_ES_FTS_IndexInclude, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 654539803L;

    /** Standard Constructor */
    public X_ES_FTS_IndexInclude (final Properties ctx, final int ES_FTS_IndexInclude_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_IndexInclude_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_IndexInclude (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributeName (final java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
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
	public de.metas.elasticsearch.model.I_ES_FTS_Index getES_FTS_Index()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Index_ID, de.metas.elasticsearch.model.I_ES_FTS_Index.class);
	}

	@Override
	public void setES_FTS_Index(final de.metas.elasticsearch.model.I_ES_FTS_Index ES_FTS_Index)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Index_ID, de.metas.elasticsearch.model.I_ES_FTS_Index.class, ES_FTS_Index);
	}

	@Override
	public void setES_FTS_Index_ID (final int ES_FTS_Index_ID)
	{
		if (ES_FTS_Index_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_ID, ES_FTS_Index_ID);
	}

	@Override
	public int getES_FTS_Index_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Index_ID);
	}

	@Override
	public void setES_FTS_IndexInclude_ID (final int ES_FTS_IndexInclude_ID)
	{
		if (ES_FTS_IndexInclude_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_IndexInclude_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_IndexInclude_ID, ES_FTS_IndexInclude_ID);
	}

	@Override
	public int getES_FTS_IndexInclude_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_IndexInclude_ID);
	}

	@Override
	public org.compiere.model.I_AD_Column getInclude_LinkColumn()
	{
		return get_ValueAsPO(COLUMNNAME_Include_LinkColumn_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setInclude_LinkColumn(final org.compiere.model.I_AD_Column Include_LinkColumn)
	{
		set_ValueFromPO(COLUMNNAME_Include_LinkColumn_ID, org.compiere.model.I_AD_Column.class, Include_LinkColumn);
	}

	@Override
	public void setInclude_LinkColumn_ID (final int Include_LinkColumn_ID)
	{
		if (Include_LinkColumn_ID < 1) 
			set_Value (COLUMNNAME_Include_LinkColumn_ID, null);
		else 
			set_Value (COLUMNNAME_Include_LinkColumn_ID, Include_LinkColumn_ID);
	}

	@Override
	public int getInclude_LinkColumn_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Include_LinkColumn_ID);
	}

	@Override
	public void setInclude_Table_ID (final int Include_Table_ID)
	{
		if (Include_Table_ID < 1) 
			set_Value (COLUMNNAME_Include_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Include_Table_ID, Include_Table_ID);
	}

	@Override
	public int getInclude_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Include_Table_ID);
	}
}