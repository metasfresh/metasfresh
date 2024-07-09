// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Type extends org.compiere.model.PO implements I_ModCntr_Type, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 768966789L;

    /** Standard Constructor */
    public X_ModCntr_Type (final Properties ctx, final int ModCntr_Type_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Type_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Type (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setModCntr_Type_ID (final int ModCntr_Type_ID)
	{
		if (ModCntr_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Type_ID, ModCntr_Type_ID);
	}

	@Override
	public int getModCntr_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Type_ID);
	}

	/** 
	 * ModularContractHandlerType AD_Reference_ID=541838
	 * Reference name: Computing Methods
	 */
	public static final int MODULARCONTRACTHANDLERTYPE_AD_Reference_ID=541838;
	/** Interim_Contract = Interim */
	public static final String MODULARCONTRACTHANDLERTYPE_Interim_Contract = "Interim";
	/** InventoryLine_Modular_NotUsed = InventoryLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_InventoryLine_Modular_NotUsed = "InventoryLine_Modular";
	/** MaterialReceiptLine_Interim_NotUsed = MaterialReceiptLine_Interim */
	public static final String MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Interim_NotUsed = "MaterialReceiptLine_Interim";
	/** MaterialReceiptLine_Modular_NotUsed = MaterialReceiptLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Modular_NotUsed = "MaterialReceiptLine_Modular";
	/** PPCostCollector_Modular_NotUsed = PPCostCollector_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_PPCostCollector_Modular_NotUsed = "PPCostCollector_Modular";
	/** PurchaseInvoiceLine_Interim_NotUsed = PurchaseInvoiceLine_Interim */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseInvoiceLine_Interim_NotUsed = "PurchaseInvoiceLine_Interim";
	/** PurchaseModularContract_NotUsed = PurchaseModularContract */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseModularContract_NotUsed = "PurchaseModularContract";
	/** PurchaseOrderLine_Modular_NotUsed = PurchaseOrderLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseOrderLine_Modular_NotUsed = "PurchaseOrderLine_Modular";
	/** SOLineForPO_Modular_NotUsed = SOLineForPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SOLineForPO_Modular_NotUsed = "SOLineForPO_Modular";
	/** SalesContractProForma_Modular_NotUsed = SalesContractProForma_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesContractProForma_Modular_NotUsed = "SalesContractProForma_Modular";
	/** SalesInvoiceLine_Modular_NotUsed = SalesInvoiceLine_Modular_NotUsed */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesInvoiceLine_Modular_NotUsed = "SalesInvoiceLine_Modular_NotUsed";
	/** SalesModularContract_NotUsed = SalesModularContract */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesModularContract_NotUsed = "SalesModularContract";
	/** SalesOrderLine_Modular_NotUsed = SalesOrderLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLine_Modular_NotUsed = "SalesOrderLine_Modular";
	/** SalesOrderLineProForma_Modular_NotUsed = SalesOrderLineProForma_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLineProForma_Modular_NotUsed = "SalesOrderLineProForma_Modular";
	/** SalesOrderLineProFormaPO_Modular_NotUsed = SalesOrderLineProFormaPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLineProFormaPO_Modular_NotUsed = "SalesOrderLineProFormaPO_Modular";
	/** ShipmentLineForPO_Modular_NotUsed = ShipmentLineForPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShipmentLineForPO_Modular_NotUsed = "ShipmentLineForPO_Modular";
	/** ShipmentLineForSO_Modular_NotUsed = ShipmentLineForSO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShipmentLineForSO_Modular_NotUsed = "ShipmentLineForSO_Modular";
	/** ShippingNotificationForPurchase_Modular_NotUsed = ShippingNotificationForPurchase_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShippingNotificationForPurchase_Modular_NotUsed = "ShippingNotificationForPurchase_Modular";
	/** ShippingNotificationForSales_Modular_NotUsed = ShippingNotificationForSales_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShippingNotificationForSales_Modular_NotUsed = "ShippingNotificationForSales_Modular";
	/** ImportLog_NotUsed = ImportLog */
	public static final String MODULARCONTRACTHANDLERTYPE_ImportLog_NotUsed = "ImportLog";
	/** Receipt = Receipt */
	public static final String MODULARCONTRACTHANDLERTYPE_Receipt = "Receipt";
	/** SalesOnRawProduct = SalesOnRawProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOnRawProduct = "SalesOnRawProduct";
	/** SalesOnProcessedProduct = SalesOnProcessedProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOnProcessedProduct = "SalesOnProcessedProduct";
	/** CoProduct = CoProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_CoProduct = "CoProduct";
	/** AddValueOnRawProduct = AddValueOnRawProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_AddValueOnRawProduct = "AddValueOnRawProduct";
	/** AddValueOnProcessedProduct = AddValueOnProcessedProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_AddValueOnProcessedProduct = "AddValueOnProcessedProduct";
	/** SubtractValueOnRawProduct = SubtractValueOnRawProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_SubtractValueOnRawProduct = "SubtractValueOnRawProduct";
	/** ReductionCalibration = ReductionCalibration */
	public static final String MODULARCONTRACTHANDLERTYPE_ReductionCalibration = "ReductionCalibration";
	/** StorageCost = StorageCost */
	public static final String MODULARCONTRACTHANDLERTYPE_StorageCost = "StorageCost";
	/** AverageAddedValueOnShippedQuantity = AverageAddedValueOnShippedQuantity */
	public static final String MODULARCONTRACTHANDLERTYPE_AverageAddedValueOnShippedQuantity = "AverageAddedValueOnShippedQuantity";
	/** AddValueOnInterim = AddValueOnInterim */
	public static final String MODULARCONTRACTHANDLERTYPE_AddValueOnInterim = "AddValueOnInterim";
	/** SubtractValueOnInterim = SubtractValueOnInterim */
	public static final String MODULARCONTRACTHANDLERTYPE_SubtractValueOnInterim = "SubtractValueOnInterim";
	/** InformativeLogs = InformativeLogs */
	public static final String MODULARCONTRACTHANDLERTYPE_InformativeLogs = "InformativeLogs";
	/** DefinitiveInvoiceRawProduct = DefinitiveInvoiceRawProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceRawProduct = "DefinitiveInvoiceRawProduct";
	/** DefinitiveInvoiceProcessedProduct = DefinitiveInvoiceProcessedProduct */
	public static final String MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceProcessedProduct = "DefinitiveInvoiceProcessedProduct";
    @Override
	public void setModularContractHandlerType (final java.lang.String ModularContractHandlerType)
	{
		set_Value (COLUMNNAME_ModularContractHandlerType, ModularContractHandlerType);
	}

	@Override
	public java.lang.String getModularContractHandlerType() 
	{
		return get_ValueAsString(COLUMNNAME_ModularContractHandlerType);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}


	/**
	 * ColumnName  AD_Reference_ID=541871
	 * Reference name: ColumnName
	 */
	public static final int ColumnName_AD_Reference_ID=541871;
	/** UserElementNumber1 = UserElementNumber1 */
	public static final String ColumnName_UserElementNumber1 = "UserElementNumber1";
	/** UserElementNumber2 = UserElementNumber2 */
	public static final String ColumnName_UserElementNumber2 = "UserElementNumber2";

	@Override
	public void setColumnName(final java.lang.String ColumnName)
	{
		set_Value(COLUMNNAME_ColumnName, ColumnName);
	}

	@Override
	public final java.lang.String getColumnName()
	{
		return get_ValueAsString(COLUMNNAME_ColumnName);
	}
}