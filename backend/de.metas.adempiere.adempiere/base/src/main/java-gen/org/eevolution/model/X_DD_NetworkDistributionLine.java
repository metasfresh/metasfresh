// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_NetworkDistributionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_NetworkDistributionLine extends org.compiere.model.PO implements I_DD_NetworkDistributionLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1970823789L;

    /** Standard Constructor */
    public X_DD_NetworkDistributionLine (final Properties ctx, final int DD_NetworkDistributionLine_ID, @Nullable final String trxName)
    {
      super (ctx, DD_NetworkDistributionLine_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_NetworkDistributionLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDD_AllowPush (final boolean DD_AllowPush)
	{
		set_Value (COLUMNNAME_DD_AllowPush, DD_AllowPush);
	}

	@Override
	public boolean isDD_AllowPush() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DD_AllowPush);
	}

	@Override
	public org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution()
	{
		return get_ValueAsPO(COLUMNNAME_DD_NetworkDistribution_ID, org.eevolution.model.I_DD_NetworkDistribution.class);
	}

	@Override
	public void setDD_NetworkDistribution(final org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution)
	{
		set_ValueFromPO(COLUMNNAME_DD_NetworkDistribution_ID, org.eevolution.model.I_DD_NetworkDistribution.class, DD_NetworkDistribution);
	}

	@Override
	public void setDD_NetworkDistribution_ID (final int DD_NetworkDistribution_ID)
	{
		if (DD_NetworkDistribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistribution_ID, DD_NetworkDistribution_ID);
	}

	@Override
	public int getDD_NetworkDistribution_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistribution_ID);
	}

	@Override
	public void setDD_NetworkDistributionLine_ID (final int DD_NetworkDistributionLine_ID)
	{
		if (DD_NetworkDistributionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistributionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistributionLine_ID, DD_NetworkDistributionLine_ID);
	}

	@Override
	public int getDD_NetworkDistributionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistributionLine_ID);
	}

	@Override
	public void setIsKeepTargetPlant (final boolean IsKeepTargetPlant)
	{
		set_Value (COLUMNNAME_IsKeepTargetPlant, IsKeepTargetPlant);
	}

	@Override
	public boolean isKeepTargetPlant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsKeepTargetPlant);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setM_WarehouseSource_ID (final int M_WarehouseSource_ID)
	{
		if (M_WarehouseSource_ID < 1) 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, M_WarehouseSource_ID);
	}

	@Override
	public int getM_WarehouseSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehouseSource_ID);
	}

	@Override
	public void setPercent (final BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	@Override
	public BigDecimal getPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Percent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriorityNo (final int PriorityNo)
	{
		set_Value (COLUMNNAME_PriorityNo, PriorityNo);
	}

	@Override
	public int getPriorityNo() 
	{
		return get_ValueAsInt(COLUMNNAME_PriorityNo);
	}

	@Override
	public void setTransfertTime (final @Nullable BigDecimal TransfertTime)
	{
		set_Value (COLUMNNAME_TransfertTime, TransfertTime);
	}

	@Override
	public BigDecimal getTransfertTime() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TransfertTime);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}