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

	private static final long serialVersionUID = -426858056L;

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
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription() 
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
	 * Reference name: Modular Contract Type Handler
	 */
	public static final int MODULARCONTRACTHANDLERTYPE_AD_Reference_ID=541838;
	/** Interim_Contract = Interim_Contract */
	public static final String MODULARCONTRACTHANDLERTYPE_Interim_Contract = "Interim_Contract";
	/** InventoryLine_Modular = InventoryLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_InventoryLine_Modular = "InventoryLine_Modular";
	/** MaterialReceiptLine_Interim = MaterialReceiptLine_Interim */
	public static final String MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Interim = "MaterialReceiptLine_Interim";
	/** MaterialReceiptLine_Modular = MaterialReceiptLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Modular = "MaterialReceiptLine_Modular";
	/** PPCostCollector_Modular = PPCostCollector_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_PPCostCollector_Modular = "PPCostCollector_Modular";
	/** PurchaseInvoiceLine_Interim = PurchaseInvoiceLine_Interim */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseInvoiceLine_Interim = "PurchaseInvoiceLine_Interim";
	/** PurchaseModularContract = PurchaseModularContract */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseModularContract = "PurchaseModularContract";
	/** PurchaseOrderLine_Modular = PurchaseOrderLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_PurchaseOrderLine_Modular = "PurchaseOrderLine_Modular";
	/** SOLineForPO_Modular = SOLineForPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SOLineForPO_Modular = "SOLineForPO_Modular";
	/** SalesContractProForma_Modular = SalesContractProForma_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesContractProForma_Modular = "SalesContractProForma_Modular";
	/** SalesInvoiceLine_Modular = SalesInvoiceLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesInvoiceLine_Modular = "SalesInvoiceLine_Modular";
	/** SalesModularContract = SalesModularContract */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesModularContract = "SalesModularContract";
	/** SalesOrderLine_Modular = SalesOrderLine_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLine_Modular = "SalesOrderLine_Modular";
	/** SalesOrderLineProForma_Modular = SalesOrderLineProForma_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLineProForma_Modular = "SalesOrderLineProForma_Modular";
	/** SalesOrderLineProFormaPO_Modular = SalesOrderLineProFormaPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_SalesOrderLineProFormaPO_Modular = "SalesOrderLineProFormaPO_Modular";
	/** ShipmentLineForPO_Modular = ShipmentLineForPO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShipmentLineForPO_Modular = "ShipmentLineForPO_Modular";
	/** ShipmentLineForSO_Modular = ShipmentLineForSO_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShipmentLineForSO_Modular = "ShipmentLineForSO_Modular";
	/** ShippingNotificationForPurchase_Modular = ShippingNotificationForPurchase_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShippingNotificationForPurchase_Modular = "ShippingNotificationForPurchase_Modular";
	/** ShippingNotificationForSales_Modular = ShippingNotificationForSales_Modular */
	public static final String MODULARCONTRACTHANDLERTYPE_ShippingNotificationForSales_Modular = "ShippingNotificationForSales_Modular";
	@Override
	public void setModularContractHandlerType (final @Nullable String ModularContractHandlerType)
	{
		set_Value (COLUMNNAME_ModularContractHandlerType, ModularContractHandlerType);
	}

	@Override
	public String getModularContractHandlerType() 
	{
		return get_ValueAsString(COLUMNNAME_ModularContractHandlerType);
	}

	@Override
	public void setName (final String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}