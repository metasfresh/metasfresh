package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CommissionSettingsLine
 *  @author metasfresh (generated) 
 */
public interface I_C_CommissionSettingsLine 
{

	String Table_Name = "C_CommissionSettingsLine";

//	/** AD_Table_ID=541429 */
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
	 * Set Settings detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID);

	/**
	 * Get Settings detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CommissionSettingsLine_ID();

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_C_CommissionSettingsLine_ID = new ModelColumn<>(I_C_CommissionSettingsLine.class, "C_CommissionSettingsLine_ID", null);
	String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Hierarchy commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_HierarchyCommissionSettings_ID (int C_HierarchyCommissionSettings_ID);

	/**
	 * Get Hierarchy commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_HierarchyCommissionSettings_ID();

	de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings getC_HierarchyCommissionSettings();

	void setC_HierarchyCommissionSettings(de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings C_HierarchyCommissionSettings);

	ModelColumn<I_C_CommissionSettingsLine, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings> COLUMN_C_HierarchyCommissionSettings_ID = new ModelColumn<>(I_C_CommissionSettingsLine.class, "C_HierarchyCommissionSettings_ID", de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class);
	String COLUMNNAME_C_HierarchyCommissionSettings_ID = "C_HierarchyCommissionSettings_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_Created = new ModelColumn<>(I_C_CommissionSettingsLine.class, "Created", null);
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
	 * Set Customer Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomer_Group_ID (int Customer_Group_ID);

	/**
	 * Get Customer Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCustomer_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getCustomer_Group();

	void setCustomer_Group(@Nullable org.compiere.model.I_C_BP_Group Customer_Group);

	ModelColumn<I_C_CommissionSettingsLine, org.compiere.model.I_C_BP_Group> COLUMN_Customer_Group_ID = new ModelColumn<>(I_C_CommissionSettingsLine.class, "Customer_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_Customer_Group_ID = "Customer_Group_ID";

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

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CommissionSettingsLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Exclude customer or group.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeBPGroup (boolean IsExcludeBPGroup);

	/**
	 * Get Exclude customer or group.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeBPGroup();

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_IsExcludeBPGroup = new ModelColumn<>(I_C_CommissionSettingsLine.class, "IsExcludeBPGroup", null);
	String COLUMNNAME_IsExcludeBPGroup = "IsExcludeBPGroup";

	/**
	 * Set Exclude product category.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeProductCategory (boolean IsExcludeProductCategory);

	/**
	 * Get Exclude product category.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeProductCategory();

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_IsExcludeProductCategory = new ModelColumn<>(I_C_CommissionSettingsLine.class, "IsExcludeProductCategory", null);
	String COLUMNNAME_IsExcludeProductCategory = "IsExcludeProductCategory";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set % of base points.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercentOfBasePoints (BigDecimal PercentOfBasePoints);

	/**
	 * Get % of base points.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentOfBasePoints();

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_PercentOfBasePoints = new ModelColumn<>(I_C_CommissionSettingsLine.class, "PercentOfBasePoints", null);
	String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

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

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_CommissionSettingsLine.class, "SeqNo", null);
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

	ModelColumn<I_C_CommissionSettingsLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_CommissionSettingsLine.class, "Updated", null);
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
