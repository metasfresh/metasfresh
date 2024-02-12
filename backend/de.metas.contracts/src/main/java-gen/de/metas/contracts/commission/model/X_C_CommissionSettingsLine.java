// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CommissionSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CommissionSettingsLine extends org.compiere.model.PO implements I_C_CommissionSettingsLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -251674515L;

    /** Standard Constructor */
    public X_C_CommissionSettingsLine (final Properties ctx, final int C_CommissionSettingsLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_CommissionSettingsLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CommissionSettingsLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public void setC_CommissionSettingsLine_ID (final int C_CommissionSettingsLine_ID)
	{
		if (C_CommissionSettingsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, C_CommissionSettingsLine_ID);
	}

	@Override
	public int getC_CommissionSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CommissionSettingsLine_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings getC_HierarchyCommissionSettings()
	{
		return get_ValueAsPO(COLUMNNAME_C_HierarchyCommissionSettings_ID, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class);
	}

	@Override
	public void setC_HierarchyCommissionSettings(final de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings C_HierarchyCommissionSettings)
	{
		set_ValueFromPO(COLUMNNAME_C_HierarchyCommissionSettings_ID, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class, C_HierarchyCommissionSettings);
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
	public org.compiere.model.I_C_BP_Group getCustomer_Group()
	{
		return get_ValueAsPO(COLUMNNAME_Customer_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setCustomer_Group(final org.compiere.model.I_C_BP_Group Customer_Group)
	{
		set_ValueFromPO(COLUMNNAME_Customer_Group_ID, org.compiere.model.I_C_BP_Group.class, Customer_Group);
	}

	@Override
	public void setCustomer_Group_ID (final int Customer_Group_ID)
	{
		if (Customer_Group_ID < 1) 
			set_Value (COLUMNNAME_Customer_Group_ID, null);
		else 
			set_Value (COLUMNNAME_Customer_Group_ID, Customer_Group_ID);
	}

	@Override
	public int getCustomer_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Customer_Group_ID);
	}

	@Override
	public void setIsExcludeBPGroup (final boolean IsExcludeBPGroup)
	{
		set_Value (COLUMNNAME_IsExcludeBPGroup, IsExcludeBPGroup);
	}

	@Override
	public boolean isExcludeBPGroup() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeBPGroup);
	}

	@Override
	public void setIsExcludeProductCategory (final boolean IsExcludeProductCategory)
	{
		set_Value (COLUMNNAME_IsExcludeProductCategory, IsExcludeProductCategory);
	}

	@Override
	public boolean isExcludeProductCategory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeProductCategory);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setPercentOfBasePoints (final BigDecimal PercentOfBasePoints)
	{
		set_Value (COLUMNNAME_PercentOfBasePoints, PercentOfBasePoints);
	}

	@Override
	public BigDecimal getPercentOfBasePoints() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentOfBasePoints);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}