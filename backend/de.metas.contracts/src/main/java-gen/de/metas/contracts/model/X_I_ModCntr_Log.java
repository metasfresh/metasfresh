// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_ModCntr_Log extends org.compiere.model.PO implements I_I_ModCntr_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 75051L;

    /** Standard Constructor */
    public X_I_ModCntr_Log (final Properties ctx, final int I_ModCntr_Log_ID, @Nullable final String trxName)
    {
      super (ctx, I_ModCntr_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_I_ModCntr_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAmount (final @Nullable BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_BPartner_Value (final @Nullable java.lang.String Bill_BPartner_Value)
	{
		set_Value (COLUMNNAME_Bill_BPartner_Value, Bill_BPartner_Value);
	}

	@Override
	public java.lang.String getBill_BPartner_Value() 
	{
		return get_ValueAsString(COLUMNNAME_Bill_BPartner_Value);
	}

	@Override
	public void setBPartnerValue (final @Nullable java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setCalendarName (final java.lang.String CalendarName)
	{
		set_Value (COLUMNNAME_CalendarName, CalendarName);
	}

	@Override
	public java.lang.String getCalendarName() 
	{
		return get_ValueAsString(COLUMNNAME_CalendarName);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(final org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (final int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, C_Calendar_ID);
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
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
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(final de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setCollectionPoint_BPartner_ID (final int CollectionPoint_BPartner_ID)
	{
		if (CollectionPoint_BPartner_ID < 1) 
			set_Value (COLUMNNAME_CollectionPoint_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_CollectionPoint_BPartner_ID, CollectionPoint_BPartner_ID);
	}

	@Override
	public int getCollectionPoint_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CollectionPoint_BPartner_ID);
	}

	@Override
	public void setCollectionPointValue (final @Nullable java.lang.String CollectionPointValue)
	{
		set_Value (COLUMNNAME_CollectionPointValue, CollectionPointValue);
	}

	@Override
	public java.lang.String getCollectionPointValue() 
	{
		return get_ValueAsString(COLUMNNAME_CollectionPointValue);
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
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
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
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFiscalYear (final java.lang.String FiscalYear)
	{
		set_Value (COLUMNNAME_FiscalYear, FiscalYear);
	}

	@Override
	public java.lang.String getFiscalYear() 
	{
		return get_ValueAsString(COLUMNNAME_FiscalYear);
	}

	@Override
	public org.compiere.model.I_C_Year getHarvesting_Year()
	{
		return get_ValueAsPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setHarvesting_Year(final org.compiere.model.I_C_Year Harvesting_Year)
	{
		set_ValueFromPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class, Harvesting_Year);
	}

	@Override
	public void setHarvesting_Year_ID (final int Harvesting_Year_ID)
	{
		if (Harvesting_Year_ID < 1) 
			set_Value (COLUMNNAME_Harvesting_Year_ID, null);
		else 
			set_Value (COLUMNNAME_Harvesting_Year_ID, Harvesting_Year_ID);
	}

	@Override
	public int getHarvesting_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Harvesting_Year_ID);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	@Override
	public void setI_IsImported (final java.lang.String I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public java.lang.String getI_IsImported() 
	{
		return get_ValueAsString(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setI_ModCntr_Log_ID (final int I_ModCntr_Log_ID)
	{
		if (I_ModCntr_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_ModCntr_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_ModCntr_Log_ID, I_ModCntr_Log_ID);
	}

	@Override
	public int getI_ModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_ModCntr_Log_ID);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	}

	@Override
	public void setModCntr_InvoicingGroup(final de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class, ModCntr_InvoicingGroup);
	}

	@Override
	public void setModCntr_InvoicingGroup_ID (final int ModCntr_InvoicingGroup_ID)
	{
		if (ModCntr_InvoicingGroup_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_InvoicingGroup_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_InvoicingGroup_ID, ModCntr_InvoicingGroup_ID);
	}

	@Override
	public int getModCntr_InvoicingGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_InvoicingGroup_ID);
	}

	@Override
	public void setModCntr_InvoicingGroupName (final @Nullable java.lang.String ModCntr_InvoicingGroupName)
	{
		set_Value (COLUMNNAME_ModCntr_InvoicingGroupName, ModCntr_InvoicingGroupName);
	}

	@Override
	public java.lang.String getModCntr_InvoicingGroupName() 
	{
		return get_ValueAsString(COLUMNNAME_ModCntr_InvoicingGroupName);
	}

	/** 
	 * ModCntr_Log_DocumentType AD_Reference_ID=541770
	 * Reference name: ModCntr_Log_DocumentType
	 */
	public static final int MODCNTR_LOG_DOCUMENTTYPE_AD_Reference_ID=541770;
	/** Purchase Order = PurchaseOrder */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_PurchaseOrder = "PurchaseOrder";
	/** Supply Agreement = SupplyAgreement */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_SupplyAgreement = "SupplyAgreement";
	/** Material Receipt = MaterialReceipt */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_MaterialReceipt = "MaterialReceipt";
	/** Production = Production */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_Production = "Production";
	/** Contract Prefinancing = ContractPrefinancing */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ContractPrefinancing = "ContractPrefinancing";
	/** Interim Invoice = InterimInvoice */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_InterimInvoice = "InterimInvoice";
	/** Contract Setting = ContractSetting */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ContractSetting = "ContractSetting";
	/** Sales Order = SalesOrder */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_SalesOrder = "SalesOrder";
	/** Shipment Disposition = ShipmentDisposition */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ShipmentDisposition = "ShipmentDisposition";
	/** Shipment = Shipment */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_Shipment = "Shipment";
	/** Final Settlement = FinalSettlement */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_FinalSettlement = "FinalSettlement";
	/** Definitive Final Settlement = DefinitiveFinalSettlement */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_DefinitiveFinalSettlement = "DefinitiveFinalSettlement";
	/** Inventory = Inventory */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_Inventory = "Inventory";
	/** Sales invoice = SalesInvoice */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_SalesInvoice = "SalesInvoice";
	/** SalesModularContract = SalesModularContract */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_SalesModularContract = "SalesModularContract";
	/** PurchaseModularContract = PurchaseModularContract */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_PurchaseModularContract = "PurchaseModularContract";
	/** ProFormaSO = ProFormaSO */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ProFormaSO = "ProFormaSO";
	/** ProFormaSOModularContract = ProFormaSOModularContract */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ProFormaSOModularContract = "ProFormaSOModularContract";
	/** Lieferavis = ShippingNotification */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_Lieferavis = "ShippingNotification";
	/** Import Log = ImportLog */
	public static final String MODCNTR_LOG_DOCUMENTTYPE_ImportLog = "ImportLog";
	@Override
	public void setModCntr_Log_DocumentType (final @Nullable java.lang.String ModCntr_Log_DocumentType)
	{
		set_Value (COLUMNNAME_ModCntr_Log_DocumentType, ModCntr_Log_DocumentType);
	}

	@Override
	public java.lang.String getModCntr_Log_DocumentType() 
	{
		return get_ValueAsString(COLUMNNAME_ModCntr_Log_DocumentType);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Log getModCntr_Log()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Log_ID, de.metas.contracts.model.I_ModCntr_Log.class);
	}

	@Override
	public void setModCntr_Log(final de.metas.contracts.model.I_ModCntr_Log ModCntr_Log)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Log_ID, de.metas.contracts.model.I_ModCntr_Log.class, ModCntr_Log);
	}

	@Override
	public void setModCntr_Log_ID (final int ModCntr_Log_ID)
	{
		if (ModCntr_Log_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Log_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Log_ID, ModCntr_Log_ID);
	}

	@Override
	public int getModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Log_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Module getModCntr_Module()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class);
	}

	@Override
	public void setModCntr_Module(final de.metas.contracts.model.I_ModCntr_Module ModCntr_Module)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class, ModCntr_Module);
	}

	@Override
	public void setModCntr_Module_ID (final int ModCntr_Module_ID)
	{
		if (ModCntr_Module_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Module_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Module_ID, ModCntr_Module_ID);
	}

	@Override
	public int getModCntr_Module_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Module_ID);
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
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceUOM (final @Nullable java.lang.String PriceUOM)
	{
		set_Value (COLUMNNAME_PriceUOM, PriceUOM);
	}

	@Override
	public java.lang.String getPriceUOM() 
	{
		return get_ValueAsString(COLUMNNAME_PriceUOM);
	}

	@Override
	public void setPrice_UOM_ID (final int Price_UOM_ID)
	{
		if (Price_UOM_ID < 1) 
			set_Value (COLUMNNAME_Price_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Price_UOM_ID, Price_UOM_ID);
	}

	@Override
	public int getPrice_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Price_UOM_ID);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProducer_BPartner_ID (final int Producer_BPartner_ID)
	{
		if (Producer_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Producer_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Producer_BPartner_ID, Producer_BPartner_ID);
	}

	@Override
	public int getProducer_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Producer_BPartner_ID);
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
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setUOMSymbol (final @Nullable java.lang.String UOMSymbol)
	{
		set_Value (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	@Override
	public java.lang.String getUOMSymbol() 
	{
		return get_ValueAsString(COLUMNNAME_UOMSymbol);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		set_Value (COLUMNNAME_WarehouseName, WarehouseName);
	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}
}