package de.metas.servicerepair.repository.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ServiceRepair_Old_Shipped_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ServiceRepair_Old_Shipped_HU 
{

	String Table_Name = "ServiceRepair_Old_Shipped_HU";

//	/** AD_Table_ID=541604 */
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
	 * Set Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerName (@Nullable java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerName();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_BPartnerName = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerValue (@Nullable java.lang.String BPartnerValue);

	/**
	 * Get Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerValue();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_BPartnerValue = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "BPartnerValue", null);
	String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Customer_ID();

	String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_Created = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "Created", null);
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

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_IsActive = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProductNo (java.lang.String ProductNo);

	/**
	 * Get Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductNo();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_ProductNo = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "ProductNo", null);
	String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReference (@Nullable java.lang.String Reference);

	/**
	 * Get Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReference();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_Reference = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "Reference", null);
	String COLUMNNAME_Reference = "Reference";

	/**
	 * Set SerialNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSerialNo (@Nullable java.lang.String SerialNo);

	/**
	 * Get SerialNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSerialNo();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_SerialNo = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "SerialNo", null);
	String COLUMNNAME_SerialNo = "SerialNo";

	/**
	 * Set Old Shipped HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setServiceRepair_Old_Shipped_HU_ID (int ServiceRepair_Old_Shipped_HU_ID);

	/**
	 * Get Old Shipped HU.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getServiceRepair_Old_Shipped_HU_ID();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_ServiceRepair_Old_Shipped_HU_ID = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "ServiceRepair_Old_Shipped_HU_ID", null);
	String COLUMNNAME_ServiceRepair_Old_Shipped_HU_ID = "ServiceRepair_Old_Shipped_HU_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_Updated = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "Updated", null);
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

	/**
	 * Set Beginn der Garantiezeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarrantyStartDate (@Nullable java.sql.Timestamp WarrantyStartDate);

	/**
	 * Get Beginn der Garantiezeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getWarrantyStartDate();

	ModelColumn<I_ServiceRepair_Old_Shipped_HU, Object> COLUMN_WarrantyStartDate = new ModelColumn<>(I_ServiceRepair_Old_Shipped_HU.class, "WarrantyStartDate", null);
	String COLUMNNAME_WarrantyStartDate = "WarrantyStartDate";
}
