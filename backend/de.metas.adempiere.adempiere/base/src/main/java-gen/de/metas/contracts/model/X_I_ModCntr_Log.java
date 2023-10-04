// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for I_ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_ModCntr_Log extends org.compiere.model.PO implements I_I_ModCntr_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -3567340L;

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
	public void setAmount (final BigDecimal Amount)
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
	public void setCollectionPointValue (final java.lang.String CollectionPointValue)
	{
		set_Value (COLUMNNAME_CollectionPointValue, CollectionPointValue);
	}

	@Override
	public java.lang.String getCollectionPointValue() 
	{
		return get_ValueAsString(COLUMNNAME_CollectionPointValue);
	}

	@Override
	public void setContractModuleName (final java.lang.String ContractModuleName)
	{
		set_Value (COLUMNNAME_ContractModuleName, ContractModuleName);
	}

	@Override
	public java.lang.String getContractModuleName() 
	{
		return get_ValueAsString(COLUMNNAME_ContractModuleName);
	}

	@Override
	public void setDateTrx (final @Nullable java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
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
	public void setProductValue (final java.lang.String ProductValue)
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