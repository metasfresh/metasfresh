package de.metas.ordercandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OLCandProcessor
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OLCandProcessor 
{

	String Table_Name = "C_OLCandProcessor";

//	/** AD_Table_ID=540245 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Scheduler_ID (int AD_Scheduler_ID);

	/**
	 * Get Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Scheduler_ID();

	@Nullable org.compiere.model.I_AD_Scheduler getAD_Scheduler();

	void setAD_Scheduler(@Nullable org.compiere.model.I_AD_Scheduler AD_Scheduler);

	ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_AD_Scheduler> COLUMN_AD_Scheduler_ID = new ModelColumn<>(I_C_OLCandProcessor.class, "AD_Scheduler_ID", org.compiere.model.I_AD_Scheduler.class);
	String COLUMNNAME_AD_Scheduler_ID = "AD_Scheduler_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set C_OLCandAgg_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OLCandAgg_IncludedTab (@Nullable java.lang.String C_OLCandAgg_IncludedTab);

	/**
	 * Get C_OLCandAgg_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_OLCandAgg_IncludedTab();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_C_OLCandAgg_IncludedTab = new ModelColumn<>(I_C_OLCandProcessor.class, "C_OLCandAgg_IncludedTab", null);
	String COLUMNNAME_C_OLCandAgg_IncludedTab = "C_OLCandAgg_IncludedTab";

	/**
	 * Set Auftragskand. Verarb..
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID);

	/**
	 * Get Auftragskand. Verarb..
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCandProcessor_ID();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_C_OLCandProcessor_ID = new ModelColumn<>(I_C_OLCandProcessor.class, "C_OLCandProcessor_ID", null);
	String COLUMNNAME_C_OLCandProcessor_ID = "C_OLCandProcessor_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCandProcessor.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryRule();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_C_OLCandProcessor.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryViaRule();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_C_OLCandProcessor.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Description = new ModelColumn<>(I_C_OLCandProcessor.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Berechnung von Frachtkosten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFreightCostRule();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_C_OLCandProcessor.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_OLCandProcessor.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCandProcessor.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Merkmals-Satz zum Produkt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	@Nullable org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	void setM_AttributeSet(@Nullable org.compiere.model.I_M_AttributeSet M_AttributeSet);

	ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_C_OLCandProcessor.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_C_OLCandProcessor, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_C_OLCandProcessor.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Name = new ModelColumn<>(I_C_OLCandProcessor.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_OLCandProcessor.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCandProcessor, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCandProcessor.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
