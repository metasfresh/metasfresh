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

<<<<<<< HEAD
	private static final long serialVersionUID = -307032135L;
=======
	private static final long serialVersionUID = 1480680640L;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	public void setAttributesKey (final @Nullable java.lang.String AttributesKey)
=======
	public void setAttributesKey (final @Nullable String AttributesKey)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getAttributesKey() 
=======
	public String getAttributesKey()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setMDCandidateQtyStock (final @Nullable BigDecimal MDCandidateQtyStock)
	{
		set_Value (COLUMNNAME_MDCandidateQtyStock, MDCandidateQtyStock);
	}

	@Override
	public BigDecimal getMDCandidateQtyStock() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MDCandidateQtyStock);
=======
	public void setMDCandidateQtyStock_AtDate (final @Nullable BigDecimal MDCandidateQtyStock_AtDate)
	{
		set_Value (COLUMNNAME_MDCandidateQtyStock_AtDate, MDCandidateQtyStock_AtDate);
	}

	@Override
	public BigDecimal getMDCandidateQtyStock_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MDCandidateQtyStock_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setPMM_QtyPromised_OnDate (final @Nullable BigDecimal PMM_QtyPromised_OnDate)
	{
		set_Value (COLUMNNAME_PMM_QtyPromised_OnDate, PMM_QtyPromised_OnDate);
	}

	@Override
	public BigDecimal getPMM_QtyPromised_OnDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PMM_QtyPromised_OnDate);
=======
	public void setPMM_QtyPromised_NextDay (final @Nullable BigDecimal PMM_QtyPromised_NextDay)
	{
		set_Value (COLUMNNAME_PMM_QtyPromised_NextDay, PMM_QtyPromised_NextDay);
	}

	@Override
	public BigDecimal getPMM_QtyPromised_NextDay() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PMM_QtyPromised_NextDay);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setProductName (final @Nullable java.lang.String ProductName)
=======
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
	public void setProductName (final @Nullable String ProductName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getProductName() 
=======
	public String getProductName()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
<<<<<<< HEAD
	public void setProductValue (final @Nullable java.lang.String ProductValue)
=======
	public void setProductValue (final @Nullable String ProductValue)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getProductValue() 
=======
	public String getProductValue()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
<<<<<<< HEAD
	public void setQtyDemand_DD_Order (final @Nullable BigDecimal QtyDemand_DD_Order)
	{
		set_Value (COLUMNNAME_QtyDemand_DD_Order, QtyDemand_DD_Order);
	}

	@Override
	public BigDecimal getQtyDemand_DD_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_DD_Order);
=======
	public void setQtyDemand_DD_Order_AtDate (final @Nullable BigDecimal QtyDemand_DD_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_DD_Order_AtDate, QtyDemand_DD_Order_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_DD_Order_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_DD_Order_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyDemand_PP_Order (final @Nullable BigDecimal QtyDemand_PP_Order)
	{
		set_Value (COLUMNNAME_QtyDemand_PP_Order, QtyDemand_PP_Order);
	}

	@Override
	public BigDecimal getQtyDemand_PP_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_PP_Order);
=======
	public void setQtyDemand_PP_Order_AtDate (final @Nullable BigDecimal QtyDemand_PP_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_PP_Order_AtDate, QtyDemand_PP_Order_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_PP_Order_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_PP_Order_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyDemand_SalesOrder (final @Nullable BigDecimal QtyDemand_SalesOrder)
	{
		set_Value (COLUMNNAME_QtyDemand_SalesOrder, QtyDemand_SalesOrder);
	}

	@Override
	public BigDecimal getQtyDemand_SalesOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_SalesOrder);
=======
	public void setQtyDemand_SalesOrder_AtDate (final @Nullable BigDecimal QtyDemand_SalesOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemand_SalesOrder_AtDate, QtyDemand_SalesOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyDemand_SalesOrder_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemand_SalesOrder_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyDemandSum (final @Nullable BigDecimal QtyDemandSum)
	{
		set_Value (COLUMNNAME_QtyDemandSum, QtyDemandSum);
	}

	@Override
	public BigDecimal getQtyDemandSum() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemandSum);
=======
	public void setQtyDemandSum_AtDate (final @Nullable BigDecimal QtyDemandSum_AtDate)
	{
		set_Value (COLUMNNAME_QtyDemandSum_AtDate, QtyDemandSum_AtDate);
	}

	@Override
	public BigDecimal getQtyDemandSum_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDemandSum_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyExpectedSurplus (final @Nullable BigDecimal QtyExpectedSurplus)
	{
		set_Value (COLUMNNAME_QtyExpectedSurplus, QtyExpectedSurplus);
	}

	@Override
	public BigDecimal getQtyExpectedSurplus() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedSurplus);
=======
	public void setQtyExpectedSurplus_AtDate (final @Nullable BigDecimal QtyExpectedSurplus_AtDate)
	{
		set_Value (COLUMNNAME_QtyExpectedSurplus_AtDate, QtyExpectedSurplus_AtDate);
	}

	@Override
	public BigDecimal getQtyExpectedSurplus_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedSurplus_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyInventoryCount (final @Nullable BigDecimal QtyInventoryCount)
	{
		set_Value (COLUMNNAME_QtyInventoryCount, QtyInventoryCount);
	}

	@Override
	public BigDecimal getQtyInventoryCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInventoryCount);
=======
	public void setQtyInventoryCount_AtDate (final @Nullable BigDecimal QtyInventoryCount_AtDate)
	{
		set_Value (COLUMNNAME_QtyInventoryCount_AtDate, QtyInventoryCount_AtDate);
	}

	@Override
	public BigDecimal getQtyInventoryCount_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInventoryCount_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
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
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
	public void setQtyOrdered_PurchaseOrder_AtDate (final @Nullable BigDecimal QtyOrdered_PurchaseOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate, QtyOrdered_PurchaseOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyOrdered_PurchaseOrder_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered_SalesOrder_AtDate (final @Nullable BigDecimal QtyOrdered_SalesOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtyOrdered_SalesOrder_AtDate, QtyOrdered_SalesOrder_AtDate);
	}

	@Override
	public BigDecimal getQtyOrdered_SalesOrder_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_SalesOrder_AtDate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setQtyStockCurrent (final @Nullable BigDecimal QtyStockCurrent)
	{
		set_Value (COLUMNNAME_QtyStockCurrent, QtyStockCurrent);
	}

	@Override
	public BigDecimal getQtyStockCurrent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockCurrent);
=======
	public void setQtyStockCurrent_AtDate (final @Nullable BigDecimal QtyStockCurrent_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockCurrent_AtDate, QtyStockCurrent_AtDate);
	}

	@Override
	public BigDecimal getQtyStockCurrent_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockCurrent_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyStockEstimateCount (final @Nullable BigDecimal QtyStockEstimateCount)
	{
		set_Value (COLUMNNAME_QtyStockEstimateCount, QtyStockEstimateCount);
	}

	@Override
	public BigDecimal getQtyStockEstimateCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockEstimateCount);
=======
	public void setQtyStockEstimateCount_AtDate (final @Nullable BigDecimal QtyStockEstimateCount_AtDate)
	{
		set_Value (COLUMNNAME_QtyStockEstimateCount_AtDate, QtyStockEstimateCount_AtDate);
	}

	@Override
	public BigDecimal getQtyStockEstimateCount_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyStockEstimateCount_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
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
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtySupply_PP_Order (final @Nullable BigDecimal QtySupply_PP_Order)
	{
		set_Value (COLUMNNAME_QtySupply_PP_Order, QtySupply_PP_Order);
	}

	@Override
	public BigDecimal getQtySupply_PP_Order() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PP_Order);
=======
	public void setQtySupply_PP_Order_AtDate (final @Nullable BigDecimal QtySupply_PP_Order_AtDate)
	{
		set_Value (COLUMNNAME_QtySupply_PP_Order_AtDate, QtySupply_PP_Order_AtDate);
	}

	@Override
	public BigDecimal getQtySupply_PP_Order_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PP_Order_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtySupply_PurchaseOrder (final @Nullable BigDecimal QtySupply_PurchaseOrder)
	{
		set_Value (COLUMNNAME_QtySupply_PurchaseOrder, QtySupply_PurchaseOrder);
	}

	@Override
	public BigDecimal getQtySupply_PurchaseOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PurchaseOrder);
=======
	public void setQtySupply_PurchaseOrder_AtDate (final @Nullable BigDecimal QtySupply_PurchaseOrder_AtDate)
	{
		set_Value (COLUMNNAME_QtySupply_PurchaseOrder_AtDate, QtySupply_PurchaseOrder_AtDate);
	}

	@Override
	public BigDecimal getQtySupply_PurchaseOrder_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupply_PurchaseOrder_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtySupplyRequired (final @Nullable BigDecimal QtySupplyRequired)
	{
		set_Value (COLUMNNAME_QtySupplyRequired, QtySupplyRequired);
	}

	@Override
	public BigDecimal getQtySupplyRequired() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyRequired);
=======
	public void setQtySupplyRequired_AtDate (final @Nullable BigDecimal QtySupplyRequired_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplyRequired_AtDate, QtySupplyRequired_AtDate);
	}

	@Override
	public BigDecimal getQtySupplyRequired_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyRequired_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtySupplySum (final @Nullable BigDecimal QtySupplySum)
	{
		set_Value (COLUMNNAME_QtySupplySum, QtySupplySum);
	}

	@Override
	public BigDecimal getQtySupplySum() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplySum);
=======
	public void setQtySupplySum_AtDate (final @Nullable BigDecimal QtySupplySum_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplySum_AtDate, QtySupplySum_AtDate);
	}

	@Override
	public BigDecimal getQtySupplySum_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplySum_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtySupplyToSchedule (final @Nullable BigDecimal QtySupplyToSchedule)
	{
		set_Value (COLUMNNAME_QtySupplyToSchedule, QtySupplyToSchedule);
	}

	@Override
	public BigDecimal getQtySupplyToSchedule() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyToSchedule);
=======
	public void setQtySupplyToSchedule_AtDate (final @Nullable BigDecimal QtySupplyToSchedule_AtDate)
	{
		set_Value (COLUMNNAME_QtySupplyToSchedule_AtDate, QtySupplyToSchedule_AtDate);
	}

	@Override
	public BigDecimal getQtySupplyToSchedule_AtDate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtySupplyToSchedule_AtDate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}
}