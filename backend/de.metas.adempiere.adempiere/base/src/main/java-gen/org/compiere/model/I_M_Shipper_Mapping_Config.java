package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Shipper_Mapping_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Shipper_Mapping_Config 
{

	String Table_Name = "M_Shipper_Mapping_Config";

//	/** AD_Table_ID=542548 */
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
	 * Set Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_ID (int Carrier_Product_ID);

	/**
	 * Get Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_ID();

	ModelColumn<I_M_Shipper_Mapping_Config, org.compiere.model.I_Carrier_Product> COLUMN_Carrier_Product_ID = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "Carrier_Product_ID", org.compiere.model.I_Carrier_Product.class);
	String COLUMNNAME_Carrier_Product_ID = "Carrier_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_Created = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "Created", null);
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

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_Description = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attribute Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMappingAttributeKey (@Nullable java.lang.String MappingAttributeKey);

	/**
	 * Get Attribute Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMappingAttributeKey();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingAttributeKey = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingAttributeKey", null);
	String COLUMNNAME_MappingAttributeKey = "MappingAttributeKey";

	/**
	 * Set Attribute Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMappingAttributeType (java.lang.String MappingAttributeType);

	/**
	 * Get Attribute Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMappingAttributeType();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingAttributeType = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingAttributeType", null);
	String COLUMNNAME_MappingAttributeType = "MappingAttributeType";

	/**
	 * Set Attribute Value.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMappingAttributeValue (java.lang.String MappingAttributeValue);

	/**
	 * Get Attribute Value.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMappingAttributeValue();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingAttributeValue = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingAttributeValue", null);
	String COLUMNNAME_MappingAttributeValue = "MappingAttributeValue";

	/**
	 * Set Mapping Group Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMappingGroupKey (@Nullable java.lang.String MappingGroupKey);

	/**
	 * Get Mapping Group Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMappingGroupKey();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingGroupKey = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingGroupKey", null);
	String COLUMNNAME_MappingGroupKey = "MappingGroupKey";

	/**
	 * Set Mapping Rule.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMappingRule (@Nullable java.lang.String MappingRule);

	/**
	 * Get Mapping Rule.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMappingRule();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingRule = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingRule", null);
	String COLUMNNAME_MappingRule = "MappingRule";

	/**
	 * Set Mapping Rule Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMappingRuleValue (@Nullable java.lang.String MappingRuleValue);

	/**
	 * Get Mapping Rule Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMappingRuleValue();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_MappingRuleValue = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "MappingRuleValue", null);
	String COLUMNNAME_MappingRuleValue = "MappingRuleValue";

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

	ModelColumn<I_M_Shipper_Mapping_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Mapping Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_Mapping_Config_ID (int M_Shipper_Mapping_Config_ID);

	/**
	 * Get Mapping Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_Mapping_Config_ID();

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_M_Shipper_Mapping_Config_ID = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "M_Shipper_Mapping_Config_ID", null);
	String COLUMNNAME_M_Shipper_Mapping_Config_ID = "M_Shipper_Mapping_Config_ID";

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

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "SeqNo", null);
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

	ModelColumn<I_M_Shipper_Mapping_Config, Object> COLUMN_Updated = new ModelColumn<>(I_M_Shipper_Mapping_Config.class, "Updated", null);
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
