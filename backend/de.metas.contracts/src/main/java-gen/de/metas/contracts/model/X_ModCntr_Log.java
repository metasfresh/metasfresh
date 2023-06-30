// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Log extends org.compiere.model.PO implements I_ModCntr_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1784155537L;

    /** Standard Constructor */
    public X_ModCntr_Log (final Properties ctx, final int ModCntr_Log_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
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
	@Override
	public void setModCntr_Log_DocumentType (final java.lang.String ModCntr_Log_DocumentType)
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
			set_ValueNoCheck (COLUMNNAME_ModCntr_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Log_ID, ModCntr_Log_ID);
	}

	@Override
	public int getModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Log_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Type getModCntr_Type()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Type_ID, de.metas.contracts.model.I_ModCntr_Type.class);
	}

	@Override
	public void setModCntr_Type(final de.metas.contracts.model.I_ModCntr_Type ModCntr_Type)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Type_ID, de.metas.contracts.model.I_ModCntr_Type.class, ModCntr_Type);
	}

	@Override
	public void setModCntr_Type_ID (final int ModCntr_Type_ID)
	{
		if (ModCntr_Type_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Type_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Type_ID, ModCntr_Type_ID);
	}

	@Override
	public int getModCntr_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Type_ID);
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
}