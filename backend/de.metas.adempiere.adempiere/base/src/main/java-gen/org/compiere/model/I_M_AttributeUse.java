package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_AttributeUse
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_AttributeUse 
{

	String Table_Name = "M_AttributeUse";

//	/** AD_Table_ID=563 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_Created = new ModelColumn<>(I_M_AttributeUse.class, "Created", null);
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

	ModelColumn<I_M_AttributeUse, Object> COLUMN_IsActive = new ModelColumn<>(I_M_AttributeUse.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Mandatory On Picking.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMandatoryOnPicking (@Nullable java.lang.String MandatoryOnPicking);

	/**
	 * Get Mandatory On Picking.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMandatoryOnPicking();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_MandatoryOnPicking = new ModelColumn<>(I_M_AttributeUse.class, "MandatoryOnPicking", null);
	String COLUMNNAME_MandatoryOnPicking = "MandatoryOnPicking";

	/**
	 * Set Mandatory On Receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMandatoryOnReceipt (@Nullable java.lang.String MandatoryOnReceipt);

	/**
	 * Get Mandatory On Receipt.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMandatoryOnReceipt();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_MandatoryOnReceipt = new ModelColumn<>(I_M_AttributeUse.class, "MandatoryOnReceipt", null);
	String COLUMNNAME_MandatoryOnReceipt = "MandatoryOnReceipt";

	/**
	 * Set Mandatory On Shipment.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMandatoryOnShipment (@Nullable java.lang.String MandatoryOnShipment);

	/**
	 * Get Mandatory On Shipment.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMandatoryOnShipment();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_MandatoryOnShipment = new ModelColumn<>(I_M_AttributeUse.class, "MandatoryOnShipment", null);
	String COLUMNNAME_MandatoryOnShipment = "MandatoryOnShipment";

	/**
	 * Set Merkmal.
	 * Product Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Product Attribute
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

	ModelColumn<I_M_AttributeUse, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_M_AttributeUse.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set M_AttributeUse.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeUse_ID (int M_AttributeUse_ID);

	/**
	 * Get M_AttributeUse.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeUse_ID();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_M_AttributeUse_ID = new ModelColumn<>(I_M_AttributeUse.class, "M_AttributeUse_ID", null);
	String COLUMNNAME_M_AttributeUse_ID = "M_AttributeUse_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_AttributeUse.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_AttributeUse, Object> COLUMN_Updated = new ModelColumn<>(I_M_AttributeUse.class, "Updated", null);
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
