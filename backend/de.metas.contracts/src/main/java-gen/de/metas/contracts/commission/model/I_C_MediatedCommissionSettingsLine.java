package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_MediatedCommissionSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_MediatedCommissionSettingsLine 
{

	String Table_Name = "C_MediatedCommissionSettingsLine";

//	/** AD_Table_ID=541808 */
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
	 * Set Brokerage commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettings_ID (int C_MediatedCommissionSettings_ID);

	/**
	 * Get Brokerage commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettings_ID();

	de.metas.contracts.commission.model.I_C_MediatedCommissionSettings getC_MediatedCommissionSettings();

	void setC_MediatedCommissionSettings(de.metas.contracts.commission.model.I_C_MediatedCommissionSettings C_MediatedCommissionSettings);

	ModelColumn<I_C_MediatedCommissionSettingsLine, de.metas.contracts.commission.model.I_C_MediatedCommissionSettings> COLUMN_C_MediatedCommissionSettings_ID = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "C_MediatedCommissionSettings_ID", de.metas.contracts.commission.model.I_C_MediatedCommissionSettings.class);
	String COLUMNNAME_C_MediatedCommissionSettings_ID = "C_MediatedCommissionSettings_ID";

	/**
	 * Set Brokerage commission detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettingsLine_ID (int C_MediatedCommissionSettingsLine_ID);

	/**
	 * Get Brokerage commission detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettingsLine_ID();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_C_MediatedCommissionSettingsLine_ID = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "C_MediatedCommissionSettingsLine_ID", null);
	String COLUMNNAME_C_MediatedCommissionSettingsLine_ID = "C_MediatedCommissionSettingsLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_Created = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "Created", null);
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

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_PercentOfBasePoints = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "PercentOfBasePoints", null);
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

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "SeqNo", null);
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

	ModelColumn<I_C_MediatedCommissionSettingsLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_MediatedCommissionSettingsLine.class, "Updated", null);
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
