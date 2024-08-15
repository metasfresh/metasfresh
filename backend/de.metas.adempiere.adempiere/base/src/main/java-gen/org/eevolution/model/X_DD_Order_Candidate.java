// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_Order_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_Order_Candidate extends org.compiere.model.PO implements I_DD_Order_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2080912459L;

    /** Standard Constructor */
    public X_DD_Order_Candidate (final Properties ctx, final int DD_Order_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, DD_Order_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_Order_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(final org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	@Override
	public void setC_OrderLineSO_ID (final int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, C_OrderLineSO_ID);
	}

	@Override
	public int getC_OrderLineSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLineSO_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateOrdered (final java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
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
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, DD_NetworkDistribution_ID);
	}

	@Override
	public int getDD_NetworkDistribution_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistribution_ID);
	}

	@Override
	public org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class);
	}

	@Override
	public void setDD_NetworkDistributionLine(final org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class, DD_NetworkDistributionLine);
	}

	@Override
	public void setDD_NetworkDistributionLine_ID (final int DD_NetworkDistributionLine_ID)
	{
		if (DD_NetworkDistributionLine_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, DD_NetworkDistributionLine_ID);
	}

	@Override
	public int getDD_NetworkDistributionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistributionLine_ID);
	}

	@Override
	public void setDD_Order_Candidate_ID (final int DD_Order_Candidate_ID)
	{
		if (DD_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_Candidate_ID, DD_Order_Candidate_ID);
	}

	@Override
	public int getDD_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_Candidate_ID);
	}

	@Override
	public void setDemandDate (final java.sql.Timestamp DemandDate)
	{
		set_Value (COLUMNNAME_DemandDate, DemandDate);
	}

	@Override
	public java.sql.Timestamp getDemandDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DemandDate);
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getForward_PP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_Forward_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setForward_PP_Order_BOMLine(final org.eevolution.model.I_PP_Order_BOMLine Forward_PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_Forward_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, Forward_PP_Order_BOMLine);
	}

	@Override
	public void setForward_PP_Order_BOMLine_ID (final int Forward_PP_Order_BOMLine_ID)
	{
		if (Forward_PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_Forward_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_Forward_PP_Order_BOMLine_ID, Forward_PP_Order_BOMLine_ID);
	}

	@Override
	public int getForward_PP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Forward_PP_Order_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Candidate getForward_PP_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_Forward_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class);
	}

	@Override
	public void setForward_PP_Order_Candidate(final org.eevolution.model.I_PP_Order_Candidate Forward_PP_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_Forward_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class, Forward_PP_Order_Candidate);
	}

	@Override
	public void setForward_PP_Order_Candidate_ID (final int Forward_PP_Order_Candidate_ID)
	{
		if (Forward_PP_Order_Candidate_ID < 1) 
			set_Value (COLUMNNAME_Forward_PP_Order_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_Forward_PP_Order_Candidate_ID, Forward_PP_Order_Candidate_ID);
	}

	@Override
	public int getForward_PP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Forward_PP_Order_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getForward_PP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Forward_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setForward_PP_Order(final org.eevolution.model.I_PP_Order Forward_PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_Forward_PP_Order_ID, org.eevolution.model.I_PP_Order.class, Forward_PP_Order);
	}

	@Override
	public void setForward_PP_Order_ID (final int Forward_PP_Order_ID)
	{
		if (Forward_PP_Order_ID < 1) 
			set_Value (COLUMNNAME_Forward_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Forward_PP_Order_ID, Forward_PP_Order_ID);
	}

	@Override
	public int getForward_PP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Forward_PP_Order_ID);
	}

	@Override
	public org.eevolution.model.I_PP_OrderLine_Candidate getForward_PP_OrderLine_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_Forward_PP_OrderLine_Candidate_ID, org.eevolution.model.I_PP_OrderLine_Candidate.class);
	}

	@Override
	public void setForward_PP_OrderLine_Candidate(final org.eevolution.model.I_PP_OrderLine_Candidate Forward_PP_OrderLine_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_Forward_PP_OrderLine_Candidate_ID, org.eevolution.model.I_PP_OrderLine_Candidate.class, Forward_PP_OrderLine_Candidate);
	}

	@Override
	public void setForward_PP_OrderLine_Candidate_ID (final int Forward_PP_OrderLine_Candidate_ID)
	{
		if (Forward_PP_OrderLine_Candidate_ID < 1) 
			set_Value (COLUMNNAME_Forward_PP_OrderLine_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_Forward_PP_OrderLine_Candidate_ID, Forward_PP_OrderLine_Candidate_ID);
	}

	@Override
	public int getForward_PP_OrderLine_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Forward_PP_OrderLine_Candidate_ID);
	}

	@Override
	public void setIsSimulated (final boolean IsSimulated)
	{
		set_Value (COLUMNNAME_IsSimulated, IsSimulated);
	}

	@Override
	public boolean isSimulated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulated);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public void setM_LocatorFrom_ID (final int M_LocatorFrom_ID)
	{
		if (M_LocatorFrom_ID < 1) 
			set_Value (COLUMNNAME_M_LocatorFrom_ID, null);
		else 
			set_Value (COLUMNNAME_M_LocatorFrom_ID, M_LocatorFrom_ID);
	}

	@Override
	public int getM_LocatorFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LocatorFrom_ID);
	}

	@Override
	public void setM_LocatorTo_ID (final int M_LocatorTo_ID)
	{
		if (M_LocatorTo_ID < 1) 
			set_Value (COLUMNNAME_M_LocatorTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_LocatorTo_ID, M_LocatorTo_ID);
	}

	@Override
	public int getM_LocatorTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LocatorTo_ID);
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
	public void setM_Warehouse_From_ID (final int M_Warehouse_From_ID)
	{
		if (M_Warehouse_From_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_From_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_From_ID, M_Warehouse_From_ID);
	}

	@Override
	public int getM_Warehouse_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_From_ID);
	}

	@Override
	public void setM_WarehouseTo_ID (final int M_WarehouseTo_ID)
	{
		if (M_WarehouseTo_ID < 1) 
			set_Value (COLUMNNAME_M_WarehouseTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehouseTo_ID, M_WarehouseTo_ID);
	}

	@Override
	public int getM_WarehouseTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehouseTo_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant_From()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_From_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant_From(final org.compiere.model.I_S_Resource PP_Plant_From)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_From_ID, org.compiere.model.I_S_Resource.class, PP_Plant_From);
	}

	@Override
	public void setPP_Plant_From_ID (final int PP_Plant_From_ID)
	{
		if (PP_Plant_From_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_From_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_From_ID, PP_Plant_From_ID);
	}

	@Override
	public int getPP_Plant_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_From_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant_To()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_To_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant_To(final org.compiere.model.I_S_Resource PP_Plant_To)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_To_ID, org.compiere.model.I_S_Resource.class, PP_Plant_To);
	}

	@Override
	public void setPP_Plant_To_ID (final int PP_Plant_To_ID)
	{
		if (PP_Plant_To_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_To_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_To_ID, PP_Plant_To_ID);
	}

	@Override
	public int getPP_Plant_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_To_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQtyEntered (final BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEnteredTU (final @Nullable BigDecimal QtyEnteredTU)
	{
		set_Value (COLUMNNAME_QtyEnteredTU, QtyEnteredTU);
	}

	@Override
	public BigDecimal getQtyEnteredTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyProcessed (final BigDecimal QtyProcessed)
	{
		set_Value (COLUMNNAME_QtyProcessed, QtyProcessed);
	}

	@Override
	public BigDecimal getQtyProcessed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyProcessed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToProcess (final BigDecimal QtyToProcess)
	{
		set_Value (COLUMNNAME_QtyToProcess, QtyToProcess);
	}

	@Override
	public BigDecimal getQtyToProcess() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToProcess);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSupplyDate (final java.sql.Timestamp SupplyDate)
	{
		set_Value (COLUMNNAME_SupplyDate, SupplyDate);
	}

	@Override
	public java.sql.Timestamp getSupplyDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_SupplyDate);
	}
}