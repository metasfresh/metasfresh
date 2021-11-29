package de.metas.inoutcandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Shipment_Constraint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Shipment_Constraint 
{

	String Table_Name = "M_Shipment_Constraint";

//	/** AD_Table_ID=540845 */
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
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	@Deprecated
	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_M_Shipment_Constraint.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Created = new ModelColumn<>(I_M_Shipment_Constraint.class, "Created", null);
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

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Shipment_Constraint.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDeliveryStop (boolean IsDeliveryStop);

	/**
	 * Get Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDeliveryStop();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsDeliveryStop = new ModelColumn<>(I_M_Shipment_Constraint.class, "IsDeliveryStop", null);
	String COLUMNNAME_IsDeliveryStop = "IsDeliveryStop";

	/**
	 * Set Gezahlt.
	 * Der Beleg ist bezahlt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsPaid (boolean IsPaid);

	/**
	 * Get Gezahlt.
	 * Der Beleg ist bezahlt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isPaid();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsPaid = new ModelColumn<>(I_M_Shipment_Constraint.class, "IsPaid", null);
	String COLUMNNAME_IsPaid = "IsPaid";

	/**
	 * Set Shipment constraint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID);

	/**
	 * Get Shipment constraint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipment_Constraint_ID();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_M_Shipment_Constraint_ID = new ModelColumn<>(I_M_Shipment_Constraint.class, "M_Shipment_Constraint_ID", null);
	String COLUMNNAME_M_Shipment_Constraint_ID = "M_Shipment_Constraint_ID";

	/**
	 * Set Source document.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceDoc_Record_ID (int SourceDoc_Record_ID);

	/**
	 * Get Source document.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSourceDoc_Record_ID();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_SourceDoc_Record_ID = new ModelColumn<>(I_M_Shipment_Constraint.class, "SourceDoc_Record_ID", null);
	String COLUMNNAME_SourceDoc_Record_ID = "SourceDoc_Record_ID";

	/**
	 * Set Source document (table).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceDoc_Table_ID (int SourceDoc_Table_ID);

	/**
	 * Get Source document (table).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSourceDoc_Table_ID();

	String COLUMNNAME_SourceDoc_Table_ID = "SourceDoc_Table_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Updated = new ModelColumn<>(I_M_Shipment_Constraint.class, "Updated", null);
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
