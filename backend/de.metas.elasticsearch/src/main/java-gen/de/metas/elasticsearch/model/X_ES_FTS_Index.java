// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Index
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Index extends org.compiere.model.PO implements I_ES_FTS_Index, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1457153015L;

    /** Standard Constructor */
    public X_ES_FTS_Index (final Properties ctx, final int ES_FTS_Index_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Index_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Index (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.elasticsearch.model.I_ES_FTS_Template getES_FTS_Template()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Template_ID, de.metas.elasticsearch.model.I_ES_FTS_Template.class);
	}

	@Override
	public void setES_FTS_Template(final de.metas.elasticsearch.model.I_ES_FTS_Template ES_FTS_Template)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Template_ID, de.metas.elasticsearch.model.I_ES_FTS_Template.class, ES_FTS_Template);
	}

	@Override
	public void setES_FTS_Template_ID (final int ES_FTS_Template_ID)
	{
		if (ES_FTS_Template_ID < 1) 
			set_Value (COLUMNNAME_ES_FTS_Template_ID, null);
		else 
			set_Value (COLUMNNAME_ES_FTS_Template_ID, ES_FTS_Template_ID);
	}

	@Override
	public int getES_FTS_Template_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Template_ID);
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
}