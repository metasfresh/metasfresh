// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ES_FTS_Filter_JoinColumn
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Filter_JoinColumn extends org.compiere.model.PO implements I_ES_FTS_Filter_JoinColumn, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1814285969L;

    /** Standard Constructor */
    public X_ES_FTS_Filter_JoinColumn (final Properties ctx, final int ES_FTS_Filter_JoinColumn_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Filter_JoinColumn_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Filter_JoinColumn (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Config_Field getES_FTS_Config_Field()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Config_Field_ID, de.metas.elasticsearch.model.I_ES_FTS_Config_Field.class);
	}

	@Override
	public void setES_FTS_Config_Field(final de.metas.elasticsearch.model.I_ES_FTS_Config_Field ES_FTS_Config_Field)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Config_Field_ID, de.metas.elasticsearch.model.I_ES_FTS_Config_Field.class, ES_FTS_Config_Field);
	}

	@Override
	public void setES_FTS_Config_Field_ID (final int ES_FTS_Config_Field_ID)
	{
		if (ES_FTS_Config_Field_ID < 1) 
			set_Value (COLUMNNAME_ES_FTS_Config_Field_ID, null);
		else 
			set_Value (COLUMNNAME_ES_FTS_Config_Field_ID, ES_FTS_Config_Field_ID);
	}

	@Override
	public int getES_FTS_Config_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_Field_ID);
	}

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Filter getES_FTS_Filter()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Filter_ID, de.metas.elasticsearch.model.I_ES_FTS_Filter.class);
	}

	@Override
	public void setES_FTS_Filter(final de.metas.elasticsearch.model.I_ES_FTS_Filter ES_FTS_Filter)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Filter_ID, de.metas.elasticsearch.model.I_ES_FTS_Filter.class, ES_FTS_Filter);
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

	@Override
	public void setES_FTS_Filter_JoinColumn_ID (final int ES_FTS_Filter_JoinColumn_ID)
	{
		if (ES_FTS_Filter_JoinColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Filter_JoinColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Filter_JoinColumn_ID, ES_FTS_Filter_JoinColumn_ID);
	}

	@Override
	public int getES_FTS_Filter_JoinColumn_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Filter_JoinColumn_ID);
	}

	@Override
	public void setIsNullable (final boolean IsNullable)
	{
		set_Value (COLUMNNAME_IsNullable, IsNullable);
	}

	@Override
	public boolean isNullable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNullable);
	}
}