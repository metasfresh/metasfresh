// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Org
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Org extends org.compiere.model.PO implements I_AD_Org, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1255990917L;

    /** Standard Constructor */
    public X_AD_Org (final Properties ctx, final int AD_Org_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Org_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Org (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class);
	}

	@Override
	public void setAD_ReplicationStrategy(final org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class, AD_ReplicationStrategy);
	}

	@Override
	public void setAD_ReplicationStrategy_ID (final int AD_ReplicationStrategy_ID)
	{
		if (AD_ReplicationStrategy_ID < 1) 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, AD_ReplicationStrategy_ID);
	}

	@Override
	public int getAD_ReplicationStrategy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ReplicationStrategy_ID);
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
	public void setIsEUOneStopShop (final boolean IsEUOneStopShop)
	{
		set_Value (COLUMNNAME_IsEUOneStopShop, IsEUOneStopShop);
	}

	@Override
	public boolean isEUOneStopShop() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEUOneStopShop);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
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

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}