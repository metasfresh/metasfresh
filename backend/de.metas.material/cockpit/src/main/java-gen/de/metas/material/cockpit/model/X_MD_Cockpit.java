// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Cockpit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Cockpit extends org.compiere.model.PO implements I_MD_Cockpit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -307032135L;

    /** Standard Constructor */
    public X_MD_Cockpit (final Properties ctx, final int MD_Cockpit_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Cockpit_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Cockpit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributesKey (final @Nullable java.lang.String AttributesKey)
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
	public java.lang.String getAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_AttributesKey);
	}

	@Override
	public void setDateGeneral (final java.sql.Timestamp DateGeneral)
	{
		set_Value (COLUMNNAME_DateGeneral, DateGeneral);
	}

	@Override
	public java.sql.Timestamp getDateGeneral() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateGeneral);
	}

	@Override
	public void setMDCandidateQtyStock (final @Nullable BigDecimal MDCandidateQtyStock)
	{
		set_Value (COLUMNNAME_MDCandidateQtyStock, MDCandidateQtyStock);
	}

	@Override
	public BigDecimal getMDCandidateQtyStock() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MDCandidateQtyStock);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMD_Cockpit_ID (final int MD_Cockpit_ID)
	{
		if (MD_Cockpit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_ID, MD_Cockpit_ID);
	}

	@Override
	public int getMD_Cockpit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Cockpit_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
	public void setPMM_QtyPromised_OnDate (final @Nullable BigDecimal PMM_QtyPromised_OnDate)
	{
		set_Value (COLUMNNAME_PMM_QtyPromised_OnDate, PMM_QtyPromised_OnDate);
	}

	@Override
	public BigDecimal getPMM_QtyPromised_OnDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PMM_QtyPromised_OnDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyDemand_DD_Order (final @Nullable BigDecimal QtyDemand_DD_Order)
	{
		set_Value (COLUMNNAME_QtyDemand_DD_Order, QtyDemand_DD_Order);
	}

	@Override
	public BigDecimal getQtyDemand_DD_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_DD_Order);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemand_PP_Order (final @Nullable BigDecimal QtyDemand_PP_Order)
	{
		set_Value (COLUMNNAME_QtyDemand_PP_Order, QtyDemand_PP_Order);
	}

	@Override
	public BigDecimal getQtyDemand_PP_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_PP_Order);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemand_SalesOrder (final @Nullable BigDecimal QtyDemand_SalesOrder)
	{
		set_Value (COLUMNNAME_QtyDemand_SalesOrder, QtyDemand_SalesOrder);
	}

	@Override
	public BigDecimal getQtyDemand_SalesOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_SalesOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemandSum (final @Nullable BigDecimal QtyDemandSum)
	{
		set_Value (COLUMNNAME_QtyDemandSum, QtyDemandSum);
	}

	@Override
	public BigDecimal getQtyDemandSum() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemandSum);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyExpectedSurplus (final @Nullable BigDecimal QtyExpectedSurplus)
	{
		set_Value (COLUMNNAME_QtyExpectedSurplus, QtyExpectedSurplus);
	}

	@Override
	public BigDecimal getQtyExpectedSurplus() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedSurplus);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInventoryCount (final @Nullable BigDecimal QtyInventoryCount)
	{
		set_Value (COLUMNNAME_QtyInventoryCount, QtyInventoryCount);
	}

	@Override
	public BigDecimal getQtyInventoryCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInventoryCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInventoryTime (final @Nullable java.sql.Timestamp QtyInventoryTime)
	{
		set_Value (COLUMNNAME_QtyInventoryTime, QtyInventoryTime);
	}

	@Override
	public java.sql.Timestamp getQtyInventoryTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_QtyInventoryTime);
	}

	@Override
	public void setQtyMaterialentnahme (final @Nullable BigDecimal QtyMaterialentnahme)
	{
		set_Value (COLUMNNAME_QtyMaterialentnahme, QtyMaterialentnahme);
	}

	@Override
	public BigDecimal getQtyMaterialentnahme() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMaterialentnahme);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOnHandCount (final @Nullable BigDecimal QtyOnHandCount)
	{
		set_Value (COLUMNNAME_QtyOnHandCount, QtyOnHandCount);
	}

	@Override
	public BigDecimal getQtyOnHandCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHandCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockChange (final @Nullable BigDecimal QtyStockChange)
	{
		set_Value (COLUMNNAME_QtyStockChange, QtyStockChange);
	}

	@Override
	public BigDecimal getQtyStockChange() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockChange);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockCurrent (final @Nullable BigDecimal QtyStockCurrent)
	{
		set_Value (COLUMNNAME_QtyStockCurrent, QtyStockCurrent);
	}

	@Override
	public BigDecimal getQtyStockCurrent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockCurrent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockEstimateCount (final @Nullable BigDecimal QtyStockEstimateCount)
	{
		set_Value (COLUMNNAME_QtyStockEstimateCount, QtyStockEstimateCount);
	}

	@Override
	public BigDecimal getQtyStockEstimateCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockEstimateCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockEstimateSeqNo (final int QtyStockEstimateSeqNo)
	{
		set_Value (COLUMNNAME_QtyStockEstimateSeqNo, QtyStockEstimateSeqNo);
	}

	@Override
	public int getQtyStockEstimateSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_QtyStockEstimateSeqNo);
	}

	@Override
	public void setQtyStockEstimateTime (final @Nullable java.sql.Timestamp QtyStockEstimateTime)
	{
		set_Value (COLUMNNAME_QtyStockEstimateTime, QtyStockEstimateTime);
	}

	@Override
	public java.sql.Timestamp getQtyStockEstimateTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_QtyStockEstimateTime);
	}

	@Override
	public void setQtySupply_DD_Order (final @Nullable BigDecimal QtySupply_DD_Order)
	{
		set_Value (COLUMNNAME_QtySupply_DD_Order, QtySupply_DD_Order);
	}

	@Override
	public BigDecimal getQtySupply_DD_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_DD_Order);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupply_PP_Order (final @Nullable BigDecimal QtySupply_PP_Order)
	{
		set_Value (COLUMNNAME_QtySupply_PP_Order, QtySupply_PP_Order);
	}

	@Override
	public BigDecimal getQtySupply_PP_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PP_Order);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupply_PurchaseOrder (final @Nullable BigDecimal QtySupply_PurchaseOrder)
	{
		set_Value (COLUMNNAME_QtySupply_PurchaseOrder, QtySupply_PurchaseOrder);
	}

	@Override
	public BigDecimal getQtySupply_PurchaseOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PurchaseOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplyRequired (final @Nullable BigDecimal QtySupplyRequired)
	{
		set_Value (COLUMNNAME_QtySupplyRequired, QtySupplyRequired);
	}

	@Override
	public BigDecimal getQtySupplyRequired() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyRequired);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplySum (final @Nullable BigDecimal QtySupplySum)
	{
		set_Value (COLUMNNAME_QtySupplySum, QtySupplySum);
	}

	@Override
	public BigDecimal getQtySupplySum() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplySum);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplyToSchedule (final @Nullable BigDecimal QtySupplyToSchedule)
	{
		set_Value (COLUMNNAME_QtySupplyToSchedule, QtySupplyToSchedule);
	}

	@Override
	public BigDecimal getQtySupplyToSchedule() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyToSchedule);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}