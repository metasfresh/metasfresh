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

	private static final long serialVersionUID = -1989481417L;

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
	public void setAttributesKey (final @Nullable String AttributesKey)
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
	public String getAttributesKey()
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
	public void setMDCandidateQtyStock_AtDate (final @Nullable BigDecimal MDCandidateQtyStock_AtDate)
	{
		set_Value (COLUMNNAME_MDCandidateQtyStock_AtDate, MDCandidateQtyStock_AtDate);
	}

	@Override
	public BigDecimal getMDCandidateQtyStock_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MDCandidateQtyStock_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPMM_QtyPromised_OnDate_AtDate (final @Nullable BigDecimal PMM_QtyPromised_OnDate_AtDate)
	{
		set_Value (COLUMNNAME_PMM_QtyPromised_OnDate_AtDate, PMM_QtyPromised_OnDate_AtDate);
	}

	@Override
	public BigDecimal getPMM_QtyPromised_OnDate_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PMM_QtyPromised_OnDate_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPMM_QtyPromised_NextDay(final @Nullable BigDecimal PMM_QtyPromised_NextDay)
	{
		set_Value(COLUMNNAME_PMM_QtyPromised_NextDay, PMM_QtyPromised_NextDay);
	}

	@Override
	public BigDecimal getPMM_QtyPromised_NextDay()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PMM_QtyPromised_NextDay);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductName (final @Nullable String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public String getProductName()
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductValue (final @Nullable String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public String getProductValue()
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyDemand_DD_Order_AtDate (final @Nullable BigDecimal QtyDemand_DD_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_DD_Order_AtDate, QtyDemand_DD_Order_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_DD_Order_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_DD_Order_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemand_PP_Order_AtDate (final @Nullable BigDecimal QtyDemand_PP_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_PP_Order_AtDate, QtyDemand_PP_Order_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_PP_Order_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_PP_Order_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemand_SalesOrder_AtDate (final @Nullable BigDecimal QtyDemand_SalesOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_SalesOrder_AtDate, QtyDemand_SalesOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_SalesOrder_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_SalesOrder_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDemandSum_AtDate (final @Nullable BigDecimal QtyDemandSum_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemandSum_AtDate, QtyDemandSum_AtDate);
	}

	@Override
	public BigDecimal getQtyDemandSum_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemandSum_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyExpectedSurplus_AtDate (final @Nullable BigDecimal QtyExpectedSurplus_AtDate)
	{
		set_Value (COLUMNNAME_QtyExpectedSurplus_AtDate, QtyExpectedSurplus_AtDate);
	}

	@Override
	public BigDecimal getQtyExpectedSurplus_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedSurplus_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInventoryCount_AtDate (final @Nullable BigDecimal QtyInventoryCount_AtDate)
	{
		set_Value (COLUMNNAME_QtyInventoryCount_AtDate, QtyInventoryCount_AtDate);
	}

	@Override
	public BigDecimal getQtyInventoryCount_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInventoryCount_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInventoryTime_AtDate (final @Nullable java.sql.Timestamp QtyInventoryTime_AtDate)
	{
		set_Value (COLUMNNAME_QtyInventoryTime_AtDate, QtyInventoryTime_AtDate);
	}

	@Override
	public java.sql.Timestamp getQtyInventoryTime_AtDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_QtyInventoryTime_AtDate);
	}

	@Override
	public void setQtyMaterialentnahme_AtDate (final @Nullable BigDecimal QtyMaterialentnahme_AtDate)
	{
		set_Value (COLUMNNAME_QtyMaterialentnahme_AtDate, QtyMaterialentnahme_AtDate);
	}

	@Override
	public BigDecimal getQtyMaterialentnahme_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyMaterialentnahme_AtDate);
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
	public void setQtyOrdered_PurchaseOrder_AtDate(final @Nullable BigDecimal QtyOrdered_PurchaseOrder_AtDate)
	{
		set_Value(COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate, QtyOrdered_PurchaseOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyOrdered_PurchaseOrder_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered_SalesOrder_AtDate(final @Nullable BigDecimal QtyOrdered_SalesOrder_AtDate)
	{
		set_Value(COLUMNNAME_QtyOrdered_SalesOrder_AtDate, QtyOrdered_SalesOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyOrdered_SalesOrder_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_SalesOrder_AtDate);
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
	public void setQtyStockCurrent_AtDate (final @Nullable BigDecimal QtyStockCurrent_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockCurrent_AtDate, QtyStockCurrent_AtDate);
	}

	@Override
	public BigDecimal getQtyStockCurrent_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockCurrent_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockEstimateCount_AtDate (final @Nullable BigDecimal QtyStockEstimateCount_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockEstimateCount_AtDate, QtyStockEstimateCount_AtDate);
	}

	@Override
	public BigDecimal getQtyStockEstimateCount_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockEstimateCount_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyStockEstimateSeqNo_AtDate (final int QtyStockEstimateSeqNo_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockEstimateSeqNo_AtDate, QtyStockEstimateSeqNo_AtDate);
	}

	@Override
	public int getQtyStockEstimateSeqNo_AtDate()
	{
		return get_ValueAsInt(COLUMNNAME_QtyStockEstimateSeqNo_AtDate);
	}

	@Override
	public void setQtyStockEstimateTime_AtDate (final @Nullable java.sql.Timestamp QtyStockEstimateTime_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockEstimateTime_AtDate, QtyStockEstimateTime_AtDate);
	}

	@Override
	public java.sql.Timestamp getQtyStockEstimateTime_AtDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_QtyStockEstimateTime_AtDate);
	}

	@Override
	public void setQtySupply_DD_Order_AtDate (final @Nullable BigDecimal QtySupply_DD_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtySupply_DD_Order_AtDate, QtySupply_DD_Order_AtDate);
	}

	@Override
	public BigDecimal getQtySupply_DD_Order_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_DD_Order_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupply_PP_Order_AtDate (final @Nullable BigDecimal QtySupply_PP_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtySupply_PP_Order_AtDate, QtySupply_PP_Order_AtDate);
	}

	@Override
	public BigDecimal getQtySupply_PP_Order_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PP_Order_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupply_PurchaseOrder_AtDate (final @Nullable BigDecimal QtySupply_PurchaseOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtySupply_PurchaseOrder_AtDate, QtySupply_PurchaseOrder_AtDate);
	}

	@Override
	public BigDecimal getQtySupply_PurchaseOrder_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PurchaseOrder_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplyRequired_AtDate (final @Nullable BigDecimal QtySupplyRequired_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplyRequired_AtDate, QtySupplyRequired_AtDate);
	}

	@Override
	public BigDecimal getQtySupplyRequired_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyRequired_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplySum_AtDate (final @Nullable BigDecimal QtySupplySum_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplySum_AtDate, QtySupplySum_AtDate);
	}

	@Override
	public BigDecimal getQtySupplySum_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplySum_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtySupplyToSchedule_AtDate (final @Nullable BigDecimal QtySupplyToSchedule_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplyToSchedule_AtDate, QtySupplyToSchedule_AtDate);
	}

	@Override
	public BigDecimal getQtySupplyToSchedule_AtDate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyToSchedule_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}