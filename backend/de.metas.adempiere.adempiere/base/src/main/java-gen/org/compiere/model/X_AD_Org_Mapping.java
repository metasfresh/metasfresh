// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Org_Mapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Org_Mapping extends org.compiere.model.PO implements I_AD_Org_Mapping, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 830925188L;

    /** Standard Constructor */
    public X_AD_Org_Mapping (final Properties ctx, final int AD_Org_Mapping_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Org_Mapping_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Org_Mapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Org_Mapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
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
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}