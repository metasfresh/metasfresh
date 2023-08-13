// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_HierarchyCommissionSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_HierarchyCommissionSettings extends org.compiere.model.PO implements I_C_HierarchyCommissionSettings, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1007640463L;

    /** Standard Constructor */
    public X_C_HierarchyCommissionSettings (final Properties ctx, final int C_HierarchyCommissionSettings_ID, @Nullable final String trxName)
    {
      super (ctx, C_HierarchyCommissionSettings_ID, trxName);
    }

    /** Load Constructor */
    public X_C_HierarchyCommissionSettings (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_HierarchyCommissionSettings_ID (final int C_HierarchyCommissionSettings_ID)
	{
		if (C_HierarchyCommissionSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_HierarchyCommissionSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_HierarchyCommissionSettings_ID, C_HierarchyCommissionSettings_ID);
	}

	@Override
	public int getC_HierarchyCommissionSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_HierarchyCommissionSettings_ID);
	}

	@Override
	public void setCommission_Product_ID (final int Commission_Product_ID)
	{
		if (Commission_Product_ID < 1) 
			set_Value (COLUMNNAME_Commission_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Commission_Product_ID, Commission_Product_ID);
	}

	@Override
	public int getCommission_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Commission_Product_ID);
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
	public void setIsCreateShareForOwnRevenue (final boolean IsCreateShareForOwnRevenue)
	{
		set_Value (COLUMNNAME_IsCreateShareForOwnRevenue, IsCreateShareForOwnRevenue);
	}

	@Override
	public boolean isCreateShareForOwnRevenue() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateShareForOwnRevenue);
	}

	@Override
	public void setIsSubtractLowerLevelCommissionFromBase (final boolean IsSubtractLowerLevelCommissionFromBase)
	{
		set_Value (COLUMNNAME_IsSubtractLowerLevelCommissionFromBase, IsSubtractLowerLevelCommissionFromBase);
	}

	@Override
	public boolean isSubtractLowerLevelCommissionFromBase() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubtractLowerLevelCommissionFromBase);
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
	public void setPointsPrecision (final int PointsPrecision)
	{
		set_Value (COLUMNNAME_PointsPrecision, PointsPrecision);
	}

	@Override
	public int getPointsPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_PointsPrecision);
	}
}