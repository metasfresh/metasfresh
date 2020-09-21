/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Inventory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Inventory extends org.compiere.model.PO implements I_I_Inventory, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1947633462L;

    /** Standard Constructor */
    public X_I_Inventory (Properties ctx, int I_Inventory_ID, String trxName)
    {
      super (ctx, I_Inventory_ID, trxName);
    }

    /** Load Constructor */
    public X_I_Inventory (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAttributeCode1 (java.lang.String AttributeCode1)
	{
		set_Value (COLUMNNAME_AttributeCode1, AttributeCode1);
	}

	@Override
	public java.lang.String getAttributeCode1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeCode1);
	}

	@Override
	public void setAttributeCode2 (java.lang.String AttributeCode2)
	{
		set_Value (COLUMNNAME_AttributeCode2, AttributeCode2);
	}

	@Override
	public java.lang.String getAttributeCode2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeCode2);
	}

	@Override
	public void setAttributeCode3 (java.lang.String AttributeCode3)
	{
		set_Value (COLUMNNAME_AttributeCode3, AttributeCode3);
	}

	@Override
	public java.lang.String getAttributeCode3() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeCode3);
	}

	@Override
	public void setAttributeCode4 (java.lang.String AttributeCode4)
	{
		set_Value (COLUMNNAME_AttributeCode4, AttributeCode4);
	}

	@Override
	public java.lang.String getAttributeCode4() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeCode4);
	}

	@Override
	public void setAttributeValueString1 (java.lang.String AttributeValueString1)
	{
		set_Value (COLUMNNAME_AttributeValueString1, AttributeValueString1);
	}

	@Override
	public java.lang.String getAttributeValueString1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValueString1);
	}

	@Override
	public void setAttributeValueString2 (java.lang.String AttributeValueString2)
	{
		set_Value (COLUMNNAME_AttributeValueString2, AttributeValueString2);
	}

	@Override
	public java.lang.String getAttributeValueString2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValueString2);
	}

	@Override
	public void setAttributeValueString3 (java.lang.String AttributeValueString3)
	{
		set_Value (COLUMNNAME_AttributeValueString3, AttributeValueString3);
	}

	@Override
	public java.lang.String getAttributeValueString3() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValueString3);
	}

	@Override
	public void setAttributeValueString4 (java.lang.String AttributeValueString4)
	{
		set_Value (COLUMNNAME_AttributeValueString4, AttributeValueString4);
	}

	@Override
	public java.lang.String getAttributeValueString4() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValueString4);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, Integer.valueOf(C_DataImport_Run_ID));
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
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
	public void setDateLastInventory (java.sql.Timestamp DateLastInventory)
	{
		set_Value (COLUMNNAME_DateLastInventory, DateLastInventory);
	}

	@Override
	public java.sql.Timestamp getDateLastInventory() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastInventory);
	}

	@Override
	public void setDateReceived (java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	@Override
	public java.sql.Timestamp getDateReceived() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateReceived);
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
	public void setExternalHeaderId (java.lang.String ExternalHeaderId)
	{
		set_Value (COLUMNNAME_ExternalHeaderId, ExternalHeaderId);
	}

	@Override
	public java.lang.String getExternalHeaderId() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalHeaderId);
	}

	@Override
	public void setExternalLineId (java.lang.String ExternalLineId)
	{
		set_Value (COLUMNNAME_ExternalLineId, ExternalLineId);
	}

	@Override
	public java.lang.String getExternalLineId() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalLineId);
	}

	@Override
	public void setHU_BestBeforeDate (java.sql.Timestamp HU_BestBeforeDate)
	{
		set_Value (COLUMNNAME_HU_BestBeforeDate, HU_BestBeforeDate);
	}

	@Override
	public java.sql.Timestamp getHU_BestBeforeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_HU_BestBeforeDate);
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

	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_Inventory_ID (int I_Inventory_ID)
	{
		if (I_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Inventory_ID, Integer.valueOf(I_Inventory_ID));
	}

	@Override
	public int getI_Inventory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_Inventory_ID);
	}

	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, Integer.valueOf(I_LineNo));
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setInventoryDate (java.sql.Timestamp InventoryDate)
	{
		set_Value (COLUMNNAME_InventoryDate, InventoryDate);
	}

	@Override
	public java.sql.Timestamp getInventoryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_InventoryDate);
	}

	@Override
	public void setIsLotBlocked (boolean IsLotBlocked)
	{
		set_Value (COLUMNNAME_IsLotBlocked, Boolean.valueOf(IsLotBlocked));
	}

	@Override
	public boolean isLotBlocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLotBlocked);
	}

	@Override
	public void setLocatorValue (java.lang.String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	@Override
	public java.lang.String getLocatorValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LocatorValue);
	}

	@Override
	public void setLot (java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	@Override
	public java.lang.String getLot() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
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
			set_Value (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_Value (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	@Override
	public int getM_Inventory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	@Override
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
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
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
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
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
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
	public void setSerNo (java.lang.String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	@Override
	public java.lang.String getSerNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNo);
	}

	@Override
	public void setSubProducer_BPartner_ID (int SubProducer_BPartner_ID)
	{
		if (SubProducer_BPartner_ID < 1) 
			set_Value (COLUMNNAME_SubProducer_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_SubProducer_BPartner_ID, Integer.valueOf(SubProducer_BPartner_ID));
	}

	@Override
	public int getSubProducer_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SubProducer_BPartner_ID);
	}

	/** 
	 * SubProducerBPartner_Value AD_Reference_ID=138
	 * Reference name: C_BPartner (Trx)
	 */
	public static final int SUBPRODUCERBPARTNER_VALUE_AD_Reference_ID=138;
	@Override
	public void setSubProducerBPartner_Value (java.lang.String SubProducerBPartner_Value)
	{

		set_Value (COLUMNNAME_SubProducerBPartner_Value, SubProducerBPartner_Value);
	}

	@Override
	public java.lang.String getSubProducerBPartner_Value() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SubProducerBPartner_Value);
	}

	@Override
	public void setTE (java.lang.String TE)
	{
		set_Value (COLUMNNAME_TE, TE);
	}

	@Override
	public java.lang.String getTE() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TE);
	}

	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	@Override
	public void setWarehouseLocatorIdentifier (java.lang.String WarehouseLocatorIdentifier)
	{
		set_Value (COLUMNNAME_WarehouseLocatorIdentifier, WarehouseLocatorIdentifier);
	}

	@Override
	public java.lang.String getWarehouseLocatorIdentifier() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseLocatorIdentifier);
	}

	@Override
	public void setWarehouseValue (java.lang.String WarehouseValue)
	{
		set_Value (COLUMNNAME_WarehouseValue, WarehouseValue);
	}

	@Override
	public java.lang.String getWarehouseValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseValue);
	}

	@Override
	public void setX (java.lang.String X)
	{
		set_Value (COLUMNNAME_X, X);
	}

	@Override
	public java.lang.String getX() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X);
	}

	@Override
	public void setX1 (java.lang.String X1)
	{
		set_Value (COLUMNNAME_X1, X1);
	}

	@Override
	public java.lang.String getX1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X1);
	}

	@Override
	public void setY (java.lang.String Y)
	{
		set_Value (COLUMNNAME_Y, Y);
	}

	@Override
	public java.lang.String getY() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Y);
	}

	@Override
	public void setZ (java.lang.String Z)
	{
		set_Value (COLUMNNAME_Z, Z);
	}

	@Override
	public java.lang.String getZ() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Z);
	}
}