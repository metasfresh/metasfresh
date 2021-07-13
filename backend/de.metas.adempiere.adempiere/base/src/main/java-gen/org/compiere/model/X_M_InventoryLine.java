/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InventoryLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_InventoryLine extends org.compiere.model.PO implements I_M_InventoryLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1021761914L;

    /** Standard Constructor */
    public X_M_InventoryLine (Properties ctx, int M_InventoryLine_ID, String trxName)
    {
      super (ctx, M_InventoryLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_InventoryLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAssignedTo (java.lang.String AssignedTo)
	{
		set_ValueNoCheck (COLUMNNAME_AssignedTo, AssignedTo);
	}

	@Override
	public java.lang.String getAssignedTo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AssignedTo);
	}

	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCostPrice (java.math.BigDecimal CostPrice)
	{
		set_Value (COLUMNNAME_CostPrice, CostPrice);
	}

	@Override
	public java.math.BigDecimal getCostPrice() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_ValueNoCheck (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** 
	 * HUAggregationType AD_Reference_ID=540976
	 * Reference name: HUAggregationType
	 */
	public static final int HUAGGREGATIONTYPE_AD_Reference_ID=540976;
	/** SINGLE_HU = S */
	public static final String HUAGGREGATIONTYPE_SINGLE_HU = "S";
	/** MULTIPLE_HUS = M */
	public static final String HUAGGREGATIONTYPE_MULTIPLE_HUS = "M";
	@Override
	public void setHUAggregationType (java.lang.String HUAggregationType)
	{

		set_Value (COLUMNNAME_HUAggregationType, HUAggregationType);
	}

	@Override
	public java.lang.String getHUAggregationType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUAggregationType);
	}

	/** 
	 * InventoryType AD_Reference_ID=292
	 * Reference name: M_Inventory Type
	 */
	public static final int INVENTORYTYPE_AD_Reference_ID=292;
	/** InventoryDifference = D */
	public static final String INVENTORYTYPE_InventoryDifference = "D";
	/** ChargeAccount = C */
	public static final String INVENTORYTYPE_ChargeAccount = "C";
	@Override
	public void setInventoryType (java.lang.String InventoryType)
	{

		set_Value (COLUMNNAME_InventoryType, InventoryType);
	}

	@Override
	public java.lang.String getInventoryType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InventoryType);
	}

	@Override
	public void setIsCounted (boolean IsCounted)
	{
		set_Value (COLUMNNAME_IsCounted, Boolean.valueOf(IsCounted));
	}

	@Override
	public boolean isCounted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCounted);
	}

	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public org.compiere.model.I_M_Inventory getM_Inventory()
	{
		return get_ValueAsPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	@Override
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	@Override
	public int getM_Inventory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	@Override
	public int getM_InventoryLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_ID);
	}

	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQtyBook (java.math.BigDecimal QtyBook)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBook, QtyBook);
	}

	@Override
	public java.math.BigDecimal getQtyBook() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBook);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCount (java.math.BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	@Override
	public java.math.BigDecimal getQtyCount() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCsv (java.math.BigDecimal QtyCsv)
	{
		set_Value (COLUMNNAME_QtyCsv, QtyCsv);
	}

	@Override
	public java.math.BigDecimal getQtyCsv() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCsv);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	@Override
	public java.math.BigDecimal getQtyInternalUse() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInternalUse);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getReversalLine()
	{
		return get_ValueAsPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setReversalLine(org.compiere.model.I_M_InventoryLine ReversalLine)
	{
		set_ValueFromPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_M_InventoryLine.class, ReversalLine);
	}

	@Override
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	@Override
	public int getReversalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReversalLine_ID);
	}

	@Override
	public void setUPC (java.lang.String UPC)
	{
		throw new IllegalArgumentException ("UPC is virtual column");	}

	@Override
	public java.lang.String getUPC() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		throw new IllegalArgumentException ("Value is virtual column");	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}