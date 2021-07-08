// Generated Model - DO NOT CHANGE
package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Repair_Task
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Repair_Task extends org.compiere.model.PO implements I_C_Project_Repair_Task, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1478686627L;

    /** Standard Constructor */
    public X_C_Project_Repair_Task (final Properties ctx, final int C_Project_Repair_Task_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Repair_Task_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Repair_Task (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_Repair_Task_ID (final int C_Project_Repair_Task_ID)
	{
		if (C_Project_Repair_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Task_ID, C_Project_Repair_Task_ID);
	}

	@Override
	public int getC_Project_Repair_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Repair_Task_ID);
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
	public org.compiere.model.I_M_InOut getCustomerReturn_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_CustomerReturn_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setCustomerReturn_InOut(final org.compiere.model.I_M_InOut CustomerReturn_InOut)
	{
		set_ValueFromPO(COLUMNNAME_CustomerReturn_InOut_ID, org.compiere.model.I_M_InOut.class, CustomerReturn_InOut);
	}

	@Override
	public void setCustomerReturn_InOut_ID (final int CustomerReturn_InOut_ID)
	{
		if (CustomerReturn_InOut_ID < 1) 
			set_Value (COLUMNNAME_CustomerReturn_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_CustomerReturn_InOut_ID, CustomerReturn_InOut_ID);
	}

	@Override
	public int getCustomerReturn_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CustomerReturn_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getCustomerReturn_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_CustomerReturn_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setCustomerReturn_InOutLine(final org.compiere.model.I_M_InOutLine CustomerReturn_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_CustomerReturn_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, CustomerReturn_InOutLine);
	}

	@Override
	public void setCustomerReturn_InOutLine_ID (final int CustomerReturn_InOutLine_ID)
	{
		if (CustomerReturn_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_CustomerReturn_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_CustomerReturn_InOutLine_ID, CustomerReturn_InOutLine_ID);
	}

	@Override
	public int getCustomerReturn_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CustomerReturn_InOutLine_ID);
	}

	@Override
	public void setIsRepairOrderDone (final boolean IsRepairOrderDone)
	{
		set_Value (COLUMNNAME_IsRepairOrderDone, IsRepairOrderDone);
	}

	@Override
	public boolean isRepairOrderDone() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRepairOrderDone);
	}

	@Override
	public void setIsWarrantyCase (final boolean IsWarrantyCase)
	{
		set_Value (COLUMNNAME_IsWarrantyCase, IsWarrantyCase);
	}

	@Override
	public boolean isWarrantyCase() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWarrantyCase);
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
	public void setQtyConsumed (final @Nullable BigDecimal QtyConsumed)
	{
		set_Value (COLUMNNAME_QtyConsumed, QtyConsumed);
	}

	@Override
	public BigDecimal getQtyConsumed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyConsumed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyRequired (final @Nullable BigDecimal QtyRequired)
	{
		set_Value (COLUMNNAME_QtyRequired, QtyRequired);
	}

	@Override
	public BigDecimal getQtyRequired() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyRequired);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.eevolution.model.I_PP_Order getRepair_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Repair_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setRepair_Order(final org.eevolution.model.I_PP_Order Repair_Order)
	{
		set_ValueFromPO(COLUMNNAME_Repair_Order_ID, org.eevolution.model.I_PP_Order.class, Repair_Order);
	}

	@Override
	public void setRepair_Order_ID (final int Repair_Order_ID)
	{
		if (Repair_Order_ID < 1) 
			set_Value (COLUMNNAME_Repair_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Repair_Order_ID, Repair_Order_ID);
	}

	@Override
	public int getRepair_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Repair_Order_ID);
	}

	@Override
	public void setRepair_VHU_ID (final int Repair_VHU_ID)
	{
		if (Repair_VHU_ID < 1) 
			set_Value (COLUMNNAME_Repair_VHU_ID, null);
		else 
			set_Value (COLUMNNAME_Repair_VHU_ID, Repair_VHU_ID);
	}

	@Override
	public int getRepair_VHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Repair_VHU_ID);
	}

	/** 
	 * Status AD_Reference_ID=541245
	 * Reference name: C_Project_Repair_Task_Status
	 */
	public static final int STATUS_AD_Reference_ID=541245;
	/** Not Started = NS */
	public static final String STATUS_NotStarted = "NS";
	/** In Progress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	/** 
	 * Type AD_Reference_ID=541243
	 * Reference name: C_Project_Repair_Task_Type
	 */
	public static final int TYPE_AD_Reference_ID=541243;
	/** ServiceRepairOrder = W */
	public static final String TYPE_ServiceRepairOrder = "W";
	/** SpareParts = P */
	public static final String TYPE_SpareParts = "P";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}