// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Product_Planning
 *  @author metasfresh (generated) 
 */
public class X_PP_Product_Planning extends org.compiere.model.PO implements I_PP_Product_Planning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1032027986L;

    /** Standard Constructor */
    public X_PP_Product_Planning (final Properties ctx, final int PP_Product_Planning_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Product_Planning_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Product_Planning (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
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
	public void setDeliveryTime_Promised (final BigDecimal DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, DeliveryTime_Promised);
	}

	@Override
	public BigDecimal getDeliveryTime_Promised() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DeliveryTime_Promised);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsAttributeDependant (final boolean IsAttributeDependant)
	{
		set_Value (COLUMNNAME_IsAttributeDependant, IsAttributeDependant);
	}

	@Override
	public boolean isAttributeDependant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAttributeDependant);
	}

	@Override
	public void setIsCreatePlan (final boolean IsCreatePlan)
	{
		set_Value (COLUMNNAME_IsCreatePlan, IsCreatePlan);
	}

	@Override
	public boolean isCreatePlan() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreatePlan);
	}

	@Override
	public void setIsDocComplete (final boolean IsDocComplete)
	{
		set_Value (COLUMNNAME_IsDocComplete, IsDocComplete);
	}

	@Override
	public boolean isDocComplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocComplete);
	}

	/** 
	 * IsManufactured AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISMANUFACTURED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISMANUFACTURED_Yes = "Y";
	/** No = N */
	public static final String ISMANUFACTURED_No = "N";
	@Override
	public void setIsManufactured (final java.lang.String IsManufactured)
	{
		set_Value (COLUMNNAME_IsManufactured, IsManufactured);
	}

	@Override
	public java.lang.String getIsManufactured() 
	{
		return get_ValueAsString(COLUMNNAME_IsManufactured);
	}

	@Override
	public void setIsMPS (final boolean IsMPS)
	{
		set_Value (COLUMNNAME_IsMPS, IsMPS);
	}

	@Override
	public boolean isMPS() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMPS);
	}

	@Override
	public void setIsPickDirectlyIfFeasible (final boolean IsPickDirectlyIfFeasible)
	{
		set_Value (COLUMNNAME_IsPickDirectlyIfFeasible, IsPickDirectlyIfFeasible);
	}

	@Override
	public boolean isPickDirectlyIfFeasible() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickDirectlyIfFeasible);
	}

	@Override
	public void setIsPickingOrder (final boolean IsPickingOrder)
	{
		set_Value (COLUMNNAME_IsPickingOrder, IsPickingOrder);
	}

	@Override
	public boolean isPickingOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingOrder);
	}

	/** 
	 * IsPurchased AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISPURCHASED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISPURCHASED_Yes = "Y";
	/** No = N */
	public static final String ISPURCHASED_No = "N";
	@Override
	public void setIsPurchased (final java.lang.String IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, IsPurchased);
	}

	@Override
	public java.lang.String getIsPurchased() 
	{
		return get_ValueAsString(COLUMNNAME_IsPurchased);
	}

	/** 
	 * IsTraded AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISTRADED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISTRADED_Yes = "Y";
	/** No = N */
	public static final String ISTRADED_No = "N";
	@Override
	public void setIsTraded (final java.lang.String IsTraded)
	{
		set_Value (COLUMNNAME_IsTraded, IsTraded);
	}

	@Override
	public java.lang.String getIsTraded() 
	{
		return get_ValueAsString(COLUMNNAME_IsTraded);
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
	public void setMaxManufacturedQtyPerOrder (final BigDecimal MaxManufacturedQtyPerOrder)
	{
		set_Value (COLUMNNAME_MaxManufacturedQtyPerOrder, MaxManufacturedQtyPerOrder);
	}

	@Override
	public BigDecimal getMaxManufacturedQtyPerOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MaxManufacturedQtyPerOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMaxManufacturedQtyPerOrder_UOM_ID (final int MaxManufacturedQtyPerOrder_UOM_ID)
	{
		if (MaxManufacturedQtyPerOrder_UOM_ID < 1) 
			set_Value (COLUMNNAME_MaxManufacturedQtyPerOrder_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_MaxManufacturedQtyPerOrder_UOM_ID, MaxManufacturedQtyPerOrder_UOM_ID);
	}

	@Override
	public int getMaxManufacturedQtyPerOrder_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxManufacturedQtyPerOrder_UOM_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Product_PlanningSchema_ID (final int M_Product_PlanningSchema_ID)
	{
		if (M_Product_PlanningSchema_ID < 1) 
			set_Value (COLUMNNAME_M_Product_PlanningSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_PlanningSchema_ID, M_Product_PlanningSchema_ID);
	}

	@Override
	public int getM_Product_PlanningSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_PlanningSchema_ID);
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

	/** 
	 * OnMaterialReceiptWithDestWarehouse AD_Reference_ID=540835
	 * Reference name: OnMaterialReceiptWithDestWarehouse_List
	 */
	public static final int ONMATERIALRECEIPTWITHDESTWAREHOUSE_AD_Reference_ID=540835;
	/** CreateMovement = M */
	public static final String ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement = "M";
	/** Create Distribution Order = D */
	public static final String ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder = "D";
	@Override
	public void setOnMaterialReceiptWithDestWarehouse (final java.lang.String OnMaterialReceiptWithDestWarehouse)
	{
		set_Value (COLUMNNAME_OnMaterialReceiptWithDestWarehouse, OnMaterialReceiptWithDestWarehouse);
	}

	@Override
	public java.lang.String getOnMaterialReceiptWithDestWarehouse() 
	{
		return get_ValueAsString(COLUMNNAME_OnMaterialReceiptWithDestWarehouse);
	}

	@Override
	public void setPlanner_ID (final int Planner_ID)
	{
		if (Planner_ID < 1) 
			set_Value (COLUMNNAME_Planner_ID, null);
		else 
			set_Value (COLUMNNAME_Planner_ID, Planner_ID);
	}

	@Override
	public int getPlanner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Planner_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(final org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	@Override
	public void setPP_Product_BOM_ID (final int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
	}

	@Override
	public int getPP_Product_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOM_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
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

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(final org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setStorageAttributesKey (final java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public java.lang.String getStorageAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_StorageAttributesKey);
	}

	@Override
	public void setTransfertTime (final BigDecimal TransfertTime)
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
	public void setWorkingTime (final BigDecimal WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	@Override
	public BigDecimal getWorkingTime() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WorkingTime);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setYield (final int Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public int getYield() 
	{
		return get_ValueAsInt(COLUMNNAME_Yield);
	}
}