package de.metas.purchasecandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PurchaseCandidate_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PurchaseCandidate_Alloc 
{

	String Table_Name = "C_PurchaseCandidate_Alloc";

//	/** AD_Table_ID=540930 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLinePO_ID (int C_OrderLinePO_ID);

	/**
	 * Get Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLinePO_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLinePO();

	void setC_OrderLinePO(@Nullable org.compiere.model.I_C_OrderLine C_OrderLinePO);

	ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLinePO_ID = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "C_OrderLinePO_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLinePO_ID = "C_OrderLinePO_ID";

	/**
	 * Set Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderPO_ID (int C_OrderPO_ID);

	/**
	 * Get Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderPO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderPO();

	void setC_OrderPO(@Nullable org.compiere.model.I_C_Order C_OrderPO);

	ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

	/**
	 * Set C_PurchaseCandidate_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PurchaseCandidate_Alloc_ID (int C_PurchaseCandidate_Alloc_ID);

	/**
	 * Get C_PurchaseCandidate_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PurchaseCandidate_Alloc_ID();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_C_PurchaseCandidate_Alloc_ID = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "C_PurchaseCandidate_Alloc_ID", null);
	String COLUMNNAME_C_PurchaseCandidate_Alloc_ID = "C_PurchaseCandidate_Alloc_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PurchaseCandidate_ID();

	de.metas.purchasecandidate.model.I_C_PurchaseCandidate getC_PurchaseCandidate();

	void setC_PurchaseCandidate(de.metas.purchasecandidate.model.I_C_PurchaseCandidate C_PurchaseCandidate);

	ModelColumn<I_C_PurchaseCandidate_Alloc, de.metas.purchasecandidate.model.I_C_PurchaseCandidate> COLUMN_C_PurchaseCandidate_ID = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "C_PurchaseCandidate_ID", de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class);
	String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "Created", null);
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
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised (@Nullable java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_DatePromised = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

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

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Bestell-Kennung.
	 * Kennung zur Eindeutigen Identifikation der Bestellung beim Lieferanten
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemotePurchaseOrderId (@Nullable java.lang.String RemotePurchaseOrderId);

	/**
	 * Get Bestell-Kennung.
	 * Kennung zur Eindeutigen Identifikation der Bestellung beim Lieferanten
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemotePurchaseOrderId();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_RemotePurchaseOrderId = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "RemotePurchaseOrderId", null);
	String COLUMNNAME_RemotePurchaseOrderId = "RemotePurchaseOrderId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_C_PurchaseCandidate_Alloc.class, "Updated", null);
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
